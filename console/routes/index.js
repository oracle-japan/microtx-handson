var express = require('express');
var router = express.Router();
var logger = require('../helpers/logging');
var oauth = require('../helpers/oauth.js');





router.get('/', function (req, res, next) {
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
    oauth.getAT(username, password).then(function (accessToken) {
        req.session.accessToken = accessToken;
        res.render("trip");
    }).catch(function (error) {
        logger.log("--- error : " + error);
        console.log(error);
        res.status(500).send(error.message);
    });
});


router.get('/signout', (req, res) => {
    req.session.destroy(function (err) {
        if (err) {
            res.json({ ret_code: 2, ret_msg: 'signout failed' });
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
        oauth.postTrip(accessToken, flightNumber, hotelName).then(function (bookingData) {
            res.render("detailp", { status: bookingData.status, detailsInfo: bookingData.details, bookingId: bookingData.id });
        }).catch(function (error) {
            logger.log("--- error : " + error);
            res.status(500).send(error.message);
        });
    } else {
        // Not logged in
        res.render("signin");
    }
});


router.post('/confirmtrip', (req, res) => {

    let bookingId = req.body.bookingId;
    let accessToken = req.session.accessToken;
    console.log(bookingId);

    oauth.confirmTrip(accessToken, bookingId).then(function (tempRes) {
        var timer = setInterval(function getTrip() {
            oauth.getTrip(accessToken, bookingId).then(function (bookingData) {
                if (bookingData.status != "PROVISIONAL") {
                    res.render("detail", { status: bookingData.status, detailsInfo: bookingData.details, bookingId: bookingData.id });
                    clearInterval(timer);
                }
            }).catch(function (error) {
                logger.log("--- error : " + error);
                res.status(500).send(error.message);
            });
            return getTrip;
        }(), 1000);
    }).catch(function (error) {
        logger.log("--- error : " + error);
        res.status(500).send(error.message);
    });

});


router.post('/canceltrip', (req, res) => {

    let bookingId = req.body.bookingId;
    let accessToken = req.session.accessToken;
    oauth.cancelTrip(accessToken, bookingId).then(function (tempRes) {
        var timer2 = setInterval(function getTrip() {
            oauth.getTrip(accessToken, bookingId).then(function (bookingData) {
                if (bookingData.status != "PROVISIONAL") {
                    res.render("detail", { status: bookingData.status, detailsInfo: bookingData.details, bookingId: bookingData.id });
                    clearInterval(timer2);
                }
            }).catch(function (error) {
                logger.log("--- error : " + error);
                res.status(500).send(error.message);
            });
            return getTrip;
        }(), 1000);
    }).catch(function (error) {
        logger.log("--- error : " + error);
        res.status(500).send(error.message);
    });

});

router.get('/gettrips', (req, res) => {

    let accessToken = req.session.accessToken;
    if (accessToken) {
        oauth.getAllTrip(accessToken).then(function (bookingData) {

            res.render("list", { recordsArr: bookingData });

        }).catch(function (error) {
            logger.log("--- error : " + error);
            res.status(500).send(error.message);
        });
    } else {
        // Not logged in
        res.render("signin");
    }


});


router.get('/deletetrips', (req, res) => {

    let accessToken = req.session.accessToken;
    if (accessToken) {
        oauth.deleteAllTrip(accessToken).then(function (bookingData) {
            res.redirect("/demo-console/gettrips");
        }).catch(function (error) {
            logger.log("--- error : " + error);
            res.status(500).send(error.message);
        });
    } else {
        // Not logged in
        res.render("signin");
    }


});


module.exports = router;
