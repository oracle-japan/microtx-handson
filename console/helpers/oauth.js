const fetch = require('node-fetch');

let neededScopes = [
    'urn:opc:idm:__myscopes__'
];

// the getAT function goes and gets an AT from IDCS
function getAT(username, password) {
    const uri = new URL("/oauth2/v1/token", process.env.IDCS_URL);
    return fetch(uri, {
        method: 'POST',
        headers: {
            'Content-type': 'application/x-www-form-urlencoded',
            'Authorization': 'Basic ' + Buffer.from(process.env.IDCS_CLIENT_ID + ":" + process.env.IDCS_CLIENT_SECRET).toString('base64'),
            'Accept': 'application/json'
        },
        body: 'grant_type=password&username=' + username + '&password=' + password + '&scope=' + encodeURIComponent(neededScopes.join(' '))
    }).then(response => {
        if(response.status != 200){
            throw new Error("Unable to verify username and password.")
        }
        return response.json();
    }).then((data) => data.access_token
    )};
exports.getAT = getAT;


async function postTrip(accessToken, flightNumber, hotelName) {
    const uri = new URL("?flightNumber=" + flightNumber + "&hotelName=" + hotelName, process.env.TRIP_SERVICE_URL);
    const response = await fetch(uri, {
        method: 'POST',
        headers: {
            'Content-type': 'application/json',
            'Authorization': 'Bearer ' + accessToken
        }
    });
    console.log(response.status)
    if (response.status != 200) {
        return reject("Error, Unable to create trip.");
    }
    if (response && response.status == 200) {
        let oracleTmmTxToken;
        if (response.headers && response.headers["oracle-tmm-tx-token"]) {
            oracleTmmTxToken = response.headers["oracle-tmm-tx-token"];
        }
        logger.log("oracleTmmTxToken: \n" + oracleTmmTxToken);
        return { 'oracleTmmTxToken': oracleTmmTxToken, 'body': body }
    } else if (body) {
        return { 'oracleTmmTxToken': undefined, 'body': body };
    } else {
        return reject("Error, Unable to create trip.");
    }
};
exports.postTrip = postTrip;


function getTrip(accessToken, bookingId) {
    const uri = new URL("/" + encodeURIComponent(bookingId), process.env.TRIP_SERVICE_URL);
    return fetch(uri, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json',
            'Authorization': 'Bearer ' + accessToken,
            'Accept': 'application/json'
        }
    }).then(response => {
        if(response.status != 200){
            throw new Error("Unable to get trip.")
        }
        return response.json();
    }).then((data))};
exports.getTrip = getTrip;


function confirmTrip(accessToken, bookingId, oracleTmmTxToken) {
    const uri = new URL("/" + encodeURIComponent(bookingId), process.env.TRIP_SERVICE_URL);
    let headers = {
        'Content-type': 'application/json',
        'Long-Running-Action': bookingId,
        'Authorization': 'Bearer ' + accessToken,
        'Accept': 'application/json'
    };
    if (oracleTmmTxToken) {
        headers = {
            'Content-type': 'application/json',
            'Long-Running-Action': bookingId,
            'Authorization': 'Bearer ' + accessToken,
            'Oracle-Tmm-Tx-Token': oracleTmmTxToken,
            'Accept': 'application/json'
        };
    }
    return fetch(uri, {
        method: 'PUT',
        headers: headers
    }).then(response => {
        if(response.status != 200){
            throw new Error("Unable to confirm trip.")
        }
        return response.json();
    }).then((data))};
exports.confirmTrip = confirmTrip;


function cancelTrip(accessToken, bookingId, oracleTmmTxToken) {
    const uri = new URL("/" + encodeURIComponent(bookingId), process.env.TRIP_SERVICE_URL);
    let headers = {
        'Content-type': 'application/json',
        'Long-Running-Action': bookingId,
        'Authorization': 'Bearer ' + accessToken,
        'Accept': 'application/json'
    };
    if (oracleTmmTxToken) {
        headers = {
            'Content-type': 'application/json',
            'Long-Running-Action': bookingId,
            'Authorization': 'Bearer ' + accessToken,
            'Oracle-Tmm-Tx-Token': oracleTmmTxToken,
            'Accept': 'application/json'
        };
    }
    return fetch(uri, {
        method: 'DELETE',
        headers: headers
    }).then(response => {
        if(response.status != 200){
            throw new Error("Unable to cancel trip.")
        }
        return response.json();
    }).then((data))};
exports.cancelTrip = cancelTrip;

function getAllTrip(accessToken) {
    const uri = process.env.TRIP_SERVICE_URL;
    return fetch(uri, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json',
            'Authorization': 'Bearer ' + accessToken,
            'Accept': 'application/json'
        }
    }).then(response => {
        if(response.status != 200){
            throw new Error("Unable to get trips.")
        }
        return response.json();
    }).then((data))};
exports.getAllTrip = getAllTrip;


function deleteAllTrip(accessToken) {
    const uri = process.env.TRIP_SERVICE_URL;
    return fetch(uri, {
        method: 'DELETE',
        headers: {
            'Content-type': 'application/json',
            'Authorization': 'Bearer ' + accessToken,
            'Accept': 'application/json'
        }
    }).then(response => {
        if(response.status != 200){
            throw new Error("Unable to delete trips.")
        }
        return response.json();
    }).then((data))};
exports.deleteAllTrip = deleteAllTrip;