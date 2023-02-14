var request = require('request');
var logger = require('./logging');

let neededScopes = [
  'urn:opc:idm:__myscopes__'
];



// the getAT function goes and gets an AT from IDCS
function getAT(username,password) {
  return new Promise(function(resolve, reject) {
    request({
        method: 'POST',
        uri: process.env.IDCS_URL + "/oauth2/v1/token",
        headers: {
          'Content-type': 'application/x-www-form-urlencoded',
          'Authorization': 'Basic ' + Buffer.from(process.env.IDCS_CLIENT_ID + ":" + process.env.IDCS_CLIENT_SECRET).toString('base64'),
          'Accept': 'application/json'
        },

        body: 'grant_type=password&username='+ username + '&password='+ password + '&scope=' + encodeURIComponent(neededScopes.join(' '))
      },
      function(error, response, body) {
        if (error)
            return reject(error);
      
        logger.log('getAccessTokenResult:  \n' + body);

        if ((response) &&
            (200 == response.statusCode)) {
          var bodydata = JSON.parse(body);
          let token = bodydata.access_token;
          resolve(token);
        } else {
            return reject("Unable to verify username and password.");
        }
      });
  });
};

exports.getAT = getAT;


function postTrip(accessToken, flightNumber, hotelName) {
    return new Promise(function(resolve, reject) {
        
        request({
                method: 'POST',
                uri: process.env.TRIP_SERVICE_URL + "?flightNumber=" + flightNumber + "&hotelName=" + hotelName ,
                headers: {
                    'Content-type': 'application/json',
                    'Authorization': 'Bearer ' + accessToken
                }
            },
            function(error, response, body) {
                if (error) {
                    return reject(error);
                } else if (response && response.statusCode == 200) {
                    logger.log("postTrip: \n" + body);
                    let oracleTmmTxToken ;
                    if(response.headers && response.headers["oracle-tmm-tx-token"]){
                        oracleTmmTxToken = response.headers["oracle-tmm-tx-token"];
                    }
                    logger.log("oracleTmmTxToken: \n" + oracleTmmTxToken);

                    return resolve({'oracleTmmTxToken':oracleTmmTxToken,'body':body});
                } else if(body){
                    return resolve({'oracleTmmTxToken':undefined,'body':body});
                } else {
                    return reject("Error, Unable to create trip.");
                }
            });
    });
};


exports.postTrip = postTrip;


function getTrip(accessToken,bookingId) {
  return new Promise(function(resolve, reject) {
      
      request({
              method: 'GET',
              uri: process.env.TRIP_SERVICE_URL + "/" + encodeURIComponent(bookingId),
              headers: {
                  'Content-type': 'application/json',
                  'Authorization': 'Bearer ' + accessToken,
                  'Accept': 'application/json'
              }
          },
          function(error, response, body) {
              if (error) {
                  return reject(error);
              } else if (response && response.statusCode == 200) {
                  logger.log("getTrip: \n" + body);
                  return resolve(body);
              } else {
                  return reject("Unable to get trip.");
              }
          });
  });
};


exports.getTrip = getTrip;


function confirmTrip(accessToken,bookingId,oracleTmmTxToken) {
  return new Promise(function(resolve, reject) {
      let headers = {
        'Content-type': 'application/json',
        'Long-Running-Action' : bookingId,
        'Authorization': 'Bearer ' + accessToken,
        'Accept': 'application/json'
      };
      if(oracleTmmTxToken){
            headers = {
                'Content-type': 'application/json',
                'Long-Running-Action' : bookingId,
                'Authorization': 'Bearer ' + accessToken,
                'Oracle-Tmm-Tx-Token': oracleTmmTxToken,
                'Accept': 'application/json'
            };
      }
      request({
              method: 'PUT',
              uri: process.env.TRIP_SERVICE_URL + "/" + encodeURIComponent(bookingId),
              headers: headers
          },
          function(error, response, body) {
              if (error) {
                  return reject(error);
              } else if (response && response.statusCode == 200) {
                  logger.log("confirmTrip: \n" );
                  return resolve(body);
              } else {
                  return reject("Unable to confirm trip.");
              }
          });
  });
};


exports.confirmTrip = confirmTrip;


function cancelTrip(accessToken,bookingId,oracleTmmTxToken) {
  return new Promise(function(resolve, reject) {
      let headers = {
        'Content-type': 'application/json',
        'Long-Running-Action' : bookingId,
        'Authorization': 'Bearer ' + accessToken,
        'Accept': 'application/json'
      };
      if(oracleTmmTxToken){
            headers = {
                'Content-type': 'application/json',
                'Long-Running-Action' : bookingId,
                'Authorization': 'Bearer ' + accessToken,
                'Oracle-Tmm-Tx-Token': oracleTmmTxToken,
                'Accept': 'application/json'
            };
      }
      
      request({
              method: 'DELETE',
              uri: process.env.TRIP_SERVICE_URL + "/" + encodeURIComponent(bookingId),
              headers: headers
          },
          function(error, response, body) {
              if (error) {
                  return reject(error);
              } else if (response && response.statusCode == 200) {
                  logger.log("cancelTrip: \n" + body);
                  return resolve(body);
              } else {
                return reject("Unable to cancel trip.");
              }
          });
  });
};


exports.cancelTrip = cancelTrip;



function getAllTrip(accessToken) {
    return new Promise(function(resolve, reject) {
        
        request({
                method: 'GET',
                uri: process.env.TRIP_SERVICE_URL,
                headers: {
                    'Content-type': 'application/json',
                    'Authorization': 'Bearer ' + accessToken,
                    'Accept': 'application/json'
                }
            },
            function(error, response, body) {
                if (error) {
                    return reject(error);
                } else if (response && response.statusCode == 200) {
                    logger.log("getAllTrip: \n" + body);
                    return resolve(body);
                } else {
                    return reject("Unable to get trip.");
                }
            });
    });
  };
  
  
  exports.getAllTrip = getAllTrip;


  function deleteAllTrip(accessToken) {
    return new Promise(function(resolve, reject) {
        
        request({
                method: 'DELETE',
                uri: process.env.TRIP_SERVICE_URL,
                headers: {
                    'Content-type': 'application/json',
                    'Authorization': 'Bearer ' + accessToken,
                    'Accept': 'application/json'
                }
            },
            function(error, response, body) {
                if (error) {
                    return reject(error);
                } else if (response && response.statusCode == 200) {
                    logger.log("deleteAllTrip: \n" + body);
                    return resolve(body);
                } else {
                    return reject("Unable to delete trips.");
                }
            });
    });
  };


  exports.deleteAllTrip = deleteAllTrip;