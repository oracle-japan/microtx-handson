var express = require('express');
var router = express.Router();
var logger = require('../helpers/logging');
var oauth = require('../helpers/oauth.js');


/* GET home page. */

// router.get('/', function (req, res, next) {

//     res.setHeader('Content-Type', 'text/html');
//     res.write('<script language="JavaScript">\n');
//     res.write('window.location = "signin.html";\n');
//     res.write('</script>\n\n');
//     res.end();

// });

router.get('/', function(req, res,next) {
	// If the user is loggedin
	if (req.session.accessToken) {
        res.render("trip");
	} else {
		// Not logged in
		res.render("signin");
	}

});



router.post('/login', (req, res) => {
    // Insert Login Code Here
    let username = req.body.username;
    let password = req.body.password;
    oauth.getAT(username,password).then(function(accessToken) {
        req.session.accessToken = accessToken;
        res.render("trip");
    }).catch(function(error) {
        logger.log("--- error : " + error);
        res.status(500).send(error);
    });
});


router.get('/signout', (req, res) => {
    req.session.destroy(function(err) {
        if(err){
          res.json({ret_code: 2, ret_msg: 'signout failed'});
          return;
        }
         
        res.clearCookie("tmm-console");
        res.redirect('/demo-console');
    });
});




router.post('/posttrip', (req, res) => {
    let flightNumber = req.body.flightNumber;
    let hotelName = req.body.hotelName;
    let accessToken = req.session.accessToken;
    if (accessToken) {
        oauth.postTrip(accessToken,flightNumber,hotelName).then(function(bookingData) {
            // console.log(bookingData);
            if(bookingData.oracleTmmTxToken){
                req.session.oracleTmmTxToken = bookingData.oracleTmmTxToken;
            }
            let resData = JSON.parse(bookingData.body);
            res.render("detailp", { status: resData.status,  detailsInfo:  resData.details, bookingId : resData.id});

        }).catch(function(error) {
            logger.log("--- error : " + error);
            res.status(500).send(error);
        });
	} else {
		// Not logged in
		res.render("signin");
	}
});


router.post('/confirmtrip', (req, res) => {

    let bookingId = req.body.bookingId;
    let accessToken = req.session.accessToken;
    let oracleTmmTxToken = req.session.oracleTmmTxToken;
    console.log(bookingId);

    oauth.confirmTrip(accessToken,bookingId,oracleTmmTxToken).then(function(tempRes) {
            var timer = setInterval(function(){
                oauth.getTrip(accessToken,bookingId).then(function(bookingData) {
                    let resData = JSON.parse(bookingData);
                    if(resData.status != "PROVISIONAL"){
                        res.render("detail", { status: resData.status, detailsInfo:  resData.details,bookingId : resData.id});
                        clearInterval(timer);
                    }
                }).catch(function(error) {
                    logger.log("--- error : " + error);
                    res.status(500).send(error);
                });
            },1000);
        }).catch(function(error) {
            logger.log("--- error : " + error);
            res.status(500).send(error);
        });

});


router.post('/canceltrip', (req, res) => {

    let bookingId = req.body.bookingId;
    let accessToken = req.session.accessToken;
    let oracleTmmTxToken = req.session.oracleTmmTxToken;
    oauth.cancelTrip(accessToken,bookingId,oracleTmmTxToken).then(function(tempRes) {
        var timer2 = setInterval(function(){
            oauth.getTrip(accessToken,bookingId).then(function(bookingData) {
                let resData = JSON.parse(bookingData);
                if(resData.status != "PROVISIONAL"){
                    res.render("detail", { status: resData.status, detailsInfo:  resData.details,bookingId : resData.id});
                    clearInterval(timer2);
                }
            }).catch(function(error) {
                logger.log("--- error : " + error);
                res.status(500).send(error);
            });
        },1000);
    }).catch(function(error) {
        logger.log("--- error : " + error);
        res.status(500).send(error);
    });

});

router.get('/gettrips', (req, res) => {

    let accessToken = req.session.accessToken;
    if (accessToken) {
        oauth.getAllTrip(accessToken).then(function(bookingData) {
    
            let resData = JSON.parse(bookingData);
            res.render("list", { recordsArr:  resData});
          
        }).catch(function(error) {
            logger.log("--- error : " + error);
            res.status(500).send(error);
        });
	} else {
		// Not logged in
		res.render("signin");
	}
   

});


router.get('/deletetrips', (req, res) => {

    let accessToken = req.session.accessToken;
    if (accessToken) {
        oauth.deleteAllTrip(accessToken).then(function(bookingData) {
            res.redirect("/demo-console/gettrips");
        }).catch(function(error) {
            logger.log("--- error : " + error);
            res.status(500).send(error);
        });
	} else {
		// Not logged in
		res.render("signin");
	}
   

});


module.exports = router;
