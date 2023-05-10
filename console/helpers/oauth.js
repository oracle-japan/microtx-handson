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
        if (response.status != 200) {
            throw new Error("Unable to verify username and password.")
        }
        return response.json();
    }).then((data) => data.access_token
    )
};
exports.getAT = getAT;


function postTrip(accessToken, flightNumber, hotelName) {
    const uri = new URL("?flightNumber=" + flightNumber + "&hotelName=" + hotelName, process.env.TRIP_SERVICE_URL);
    return fetch(uri, {
        method: 'POST',
        headers: {
            'Content-type': 'application/json',
            'Authorization': 'Bearer ' + accessToken
        }
    }).then(response => {
        return response.json();
    }).then(responseData => {
        return responseData;
    })
};
exports.postTrip = postTrip;


function getTrip(accessToken, bookingId) {
    const uri = process.env.TRIP_SERVICE_URL + "/" + encodeURIComponent(bookingId);
    return fetch(uri, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json',
            'Authorization': 'Bearer ' + accessToken,
            'Accept': 'application/json'
        }
    }).then(response => {
        if (response.status != 200) {
            throw new Error("Unable to get trip.")
        }
        return response.json();
    }).then((data) => {
        console.log(JSON.stringify(data));
        return data;
    });
}
exports.getTrip = getTrip;


function confirmTrip(accessToken, bookingId) {
    const uri = process.env.TRIP_SERVICE_URL + "/" + encodeURIComponent(bookingId);
    return fetch(uri, {
        method: 'PUT',
        headers: {
            'Content-type': 'application/json',
            'Long-Running-Action': bookingId,
            'Authorization': 'Bearer ' + accessToken,
            'Accept': 'application/json'
        }
    }).then(response => {
        if (response.status != 200) {
            throw new Error("Unable to confirm trip.")
        }
        return response;
    }).then((data) => {
        return data;
    });
}
exports.confirmTrip = confirmTrip;


function cancelTrip(accessToken, bookingId) {
    const uri = process.env.TRIP_SERVICE_URL + "/" + encodeURIComponent(bookingId);
    return fetch(uri, {
        method: 'DELETE',
        headers: {
            'Content-type': 'application/json',
            'Long-Running-Action': bookingId,
            'Authorization': 'Bearer ' + accessToken,
            'Accept': 'application/json'
        }
    }).then(response => {
        if (response.status != 200) {
            throw new Error("Unable to cancel trip.")
        }
        return response;
    }).then((data) => {
        return data;
    });
}
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
        if (response.status != 200) {
            throw new Error("Unable to get trips.")
        }
        return response.json();
    }).then((data) => {
        return data;
    });
}
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
        if (response.status != 200) {
            throw new Error("Unable to delete trips.")
        }
        return response;
    }).then((data) => {
        return data;
    });
}
exports.deleteAllTrip = deleteAllTrip;