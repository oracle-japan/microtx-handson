/*

 Oracle Transaction Manager for Microservices
 
 Copyright © 2022, Oracle and/or its affiliates. All rights reserved.

 This software and related documentation are provided under a license agreement containing restrictions on use and disclosure and are protected by intellectual property laws. Except as expressly permitted in your license agreement or allowed by law, you may not use, copy, reproduce, translate, broadcast, modify, license, transmit, distribute, exhibit, perform, publish, or display any part, in any form, or by any means. Reverse engineering, disassembly, or decompilation of this software, unless required by law for interoperability, is prohibited.

 The information contained herein is subject to change without notice and is not warranted to be error-free. If you find any errors, please report them to us in writing.

 If this is software or related documentation that is delivered to the U.S. Government or anyone licensing it on behalf of the U.S. Government, then the following notice is applicable:

 U.S. GOVERNMENT END USERS: Oracle programs, including any operating system, integrated software, any programs installed on the hardware, and/or documentation, delivered to U.S. Government end users are "commercial computer software" pursuant to the applicable Federal Acquisition Regulation and agency-specific supplemental regulations. As such, use, duplication, disclosure, modification, and adaptation of the programs, including any operating system, integrated software, any programs installed on the hardware, and/or documentation, shall be subject to license terms and license restrictions applicable to the programs. No other rights are granted to the U.S. Government.

 This software or hardware is developed for general use in a variety of information management applications. It is not developed or intended for use in any inherently dangerous applications, including applications that may create a risk of personal injury. If you use this software or hardware in dangerous applications, then you shall be responsible to take all appropriate fail-safe, backup, redundancy, and other measures to ensure its safe use. Oracle Corporation and its affiliates disclaim any liability for any damages caused by use of this software or hardware in dangerous applications.
 Oracle and Java are registered trademarks of Oracle and/or its affiliates. Other names may be trademarks of their respective owners.
 Intel and Intel Xeon are trademarks or registered trademarks of Intel Corporation. All SPARC trademarks are used under license and are trademarks or registered trademarks of SPARC International, Inc. AMD, Opteron, the AMD logo, and the AMD Opteron logo are trademarks or registered trademarks of Advanced Micro Devices. UNIX is a registered trademark of The Open Group.

 This software or hardware and documentation may provide access to or information about content, products, and services from third parties. Oracle Corporation and its affiliates are not responsible for and expressly disclaim all warranties of any kind with respect to third-party content, products, and services unless otherwise set forth in an applicable agreement between you and Oracle. Oracle Corporation and its affiliates will not be responsible for any loss, costs, or damages incurred due to your access to or use of third-party content, products, or services, except as set forth in an applicable agreement between you and Oracle.

*/
import { Request, Response, Router } from 'express';
import { getLRAId, LRA, LRAConfig, LRAType, ParticipantStatus } from "tmmlib-node/lra/lra";
import { TrmConfig } from 'tmmlib-node/util/trmutils';
import { Booking, BookingStatus, setMaxConfirmedBooking } from "../booking";
import { MAX_CONFIRMED_BOOKING ,initdb  } from '../booking';
import { BookingService } from '../booking';

// Init Router
const flightSvcRouter = Router();

// Transaction coordinator configuration initialization 
TrmConfig.init('./tmm.properties');
initdb();

// Get the LRA coordinator URL
const lraCoordinateUrl = process.env.ORACLE_TMM_TCS_URL || "http://localhost:9000/api/v1/lra-coordinator"
//Step to setup and initiate LRA participation
const lra: LRA = new LRA("/flight", LRAType.MANDATORY);
lra.end = false;
lra.timeLimitInMilliSeconds = 100000;
new LRAConfig(lraCoordinateUrl, flightSvcRouter, "/flightService/api",
    lra, "/complete", "/compensate", "/status", "/after", "", "", "");

flightSvcRouter.post('/flight', async (req, resp) => {
    console.log("create flight");
    var booking = await checkAndBookFlight(req, resp); //business logic
    if (!booking) {
        resp.status(500).send("Flight booking failed");
    }
    resp.type('application/json').send(JSON.stringify(booking));
});

flightSvcRouter.get('/flight/:bookingId', async (req, resp) => {
    const bookingId = req.params.bookingId;
    console.log(`Get Booking details called for: ${bookingId}`);
    let booking = await BookingService.selectById(bookingId);
    if (!booking) {
        resp.status(404).send("Flight Booking not found");
    } else {
        resp.type('application/json').send(JSON.stringify(booking));
    }
});

flightSvcRouter.put('/complete', async (req, resp) => {
    var lraId = getLRAId(req, resp);
    if (!lraId) {
        resp.status(400).send("No LRA associated");
        return;
    }
    console.log(`FlightServiceResource complete() called for LRA : [${lraId}]`);
    let booking = await BookingService.selectById(lraId);
    if (!booking) {
        return resp.status(404).send("Flight Booking not found");
    } 
    if (booking.status == BookingStatus.PROVISIONAL) {
        booking.status = BookingStatus.CONFIRMED;
        await BookingService.updateStatus(booking);
        return resp.type('application/json').send(ParticipantStatus.Completed);
    }
    booking.status = BookingStatus.FAILED;
    await BookingService.updateStatus(booking);
    return resp.type('application/json').send(ParticipantStatus.FailedToComplete);    
    
});

flightSvcRouter.put('/compensate', async (req, resp) => {
    var lraId = getLRAId(req, resp);
    if (!lraId) {
        resp.status(400).send("No LRA associated");
        return;
    }
    console.log(`FlightServiceResource compensate() called for LRA : [${lraId}]`);
    let booking = await BookingService.selectById(lraId);
    if (booking == null) {
        return resp.status(404).send("Flight Booking not found");
    } 
    if (booking.status == BookingStatus.PROVISIONAL) {
        booking.status = BookingStatus.CANCELLED;
        await BookingService.updateStatus(booking);
        return resp.type('application/json').send(ParticipantStatus.Compensated);
    }
    booking.status = BookingStatus.FAILED;
    await BookingService.updateStatus(booking);
    return resp.type('application/json').send(ParticipantStatus.FailedToComplete);  
});

flightSvcRouter.get('/status', async (req, resp) => {
    var lraId = getLRAId(req, resp);
    if (!lraId) {
        return resp.status(400).send("No LRA associated");
    }
    console.log(`FlightServiceResource status() called for LRA : [${lraId}]`);
    let booking = await BookingService.selectById(lraId);
    if (booking == null) {
        return resp.status(404).send("Flight Booking not found");
    } 
    if(booking.status == BookingStatus.CONFIRMED){
        return resp.type('text/plain').send(ParticipantStatus.Completed);
    }
    return resp.type('text/plain').send(ParticipantStatus.Compensated);
});

flightSvcRouter.put('/after', (req, resp) => {
    // Clean up resource held by this LRA
    // Finalise the status of the LRA
    var lraId = getLRAId(req, resp);
    console.log(`FlightServiceResource after() called for LRA : [${lraId}]`);
    const status = req.body;
    console.log(`LRA [${lraId}] final status: ${status}`);
    resp.status(200).send();
});

flightSvcRouter.delete('/flight/:bookingId', async (req, resp) => {
    let bookingId = req.params.bookingId;
    console.log(`Delete Booking details called for: ${bookingId}`);
    let booking = await BookingService.selectById(bookingId);
    if (!booking) {
        resp.status(404).send("Flight Booking not found");
    } else {
        await BookingService.deleteById(bookingId);
        resp.type('application/json').send(JSON.stringify(booking));
    }
});

flightSvcRouter.delete('/flight', async (req, resp) => {
    console.log(`Delete All flight Bookings called`);
    await BookingService.deleteAll();
    resp.status(200).send();
});

flightSvcRouter.get('/flight', async (req, resp) => {
    console.log("Get All Flight bookings called");
    resp.type('application/json').send(JSON.stringify(await BookingService.selectAllAsObject()));
});

flightSvcRouter.put('/maxbookings', (req, resp) => {
    let num = req.query.count;
    console.log(`Set Max Booking Count : ${num}`);
    if (typeof num == 'string') {
        setMaxConfirmedBooking(parseInt(num, 10));
    }
    resp.status(200).type('text/plain').send(MAX_CONFIRMED_BOOKING.toString());
});

async function checkAndBookFlight(req: Request, resp: Response) {
    var lraId = getLRAId(req, resp);
    if (!lraId) {
        return null;
    }
    let booking
    let flightNum = req.query.flightNumber?.toString();
    if (flightNum) {
        booking = new Booking(lraId, flightNum, "Flight");
        let confirmedCount = await BookingService.getCount();
        if ( confirmedCount >= MAX_CONFIRMED_BOOKING) {
            booking.status = BookingStatus.FAILED;
            console.log(`Flight Booking failed: ${JSON.stringify(booking)}`);
        } else {
            console.log(`Flight Booking created: ${JSON.stringify(booking)}`);
        }

        if(!(await BookingService.insert(booking))){
            return null;
        }
    }
    return booking;
}

/******************************************************************************
 *                                     Export
 ******************************************************************************/


export default flightSvcRouter;


