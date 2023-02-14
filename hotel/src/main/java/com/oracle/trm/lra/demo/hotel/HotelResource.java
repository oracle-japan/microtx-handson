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
package com.oracle.trm.lra.demo.hotel;


import org.eclipse.microprofile.lra.annotation.AfterLRA;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ParticipantStatus;
import org.eclipse.microprofile.lra.annotation.Status;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

import static org.eclipse.microprofile.lra.annotation.ws.rs.LRA.LRA_HTTP_CONTEXT_HEADER;
import static org.eclipse.microprofile.lra.annotation.ws.rs.LRA.LRA_HTTP_ENDED_CONTEXT_HEADER;
import static org.eclipse.microprofile.lra.annotation.ws.rs.LRA.LRA_HTTP_PARENT_CONTEXT_HEADER;


@Path("/hotelService/api")
@ApplicationScoped
public class HotelResource {

    private static final Logger log = Logger.getLogger(HotelResource.class.getSimpleName());

    @Inject
    private HotelService hotelService;

    @POST
    @Path("/hotel")
    @Produces(MediaType.APPLICATION_JSON)
    @LRA(value = LRA.Type.MANDATORY, end = false)
    public Response bookRoom(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) String lraId, @QueryParam("hotelName") @DefaultValue("Default") String hotelName) throws BadRequestException {
        Hotel booking = hotelService.book(lraId, hotelName);
        log.info("Hotel Booking created: " + booking);
        return Response.ok(booking).build();
    }

    @PUT
    @Path("/complete")
    @Produces(MediaType.TEXT_PLAIN)
    @Complete
    public Response completeWork(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) String lraId) throws NotFoundException {
        log.info("HotelServiceResource complete() called for LRA : " + lraId);
        // Business logic to complete the work related to this LRA
        Hotel booking = hotelService.get(lraId);
        if(booking == null){
            return Response.status(Response.Status.NOT_FOUND).entity("Hotel booking not found").build();
        }
        if (booking.getStatus() == Hotel.BookingStatus.PROVISIONAL) {
            booking.setStatus(Hotel.BookingStatus.CONFIRMED);
            hotelService.updateHotel(booking);
            return Response.ok(ParticipantStatus.Completed.name()).build();
        }
        booking.setStatus(Hotel.BookingStatus.FAILED);
        hotelService.updateHotel(booking);
        return Response.ok(ParticipantStatus.FailedToComplete.name()).build();
    }

    @PUT
    @Path("/compensate")
    @Produces(MediaType.TEXT_PLAIN)
    @Compensate
    public Response compensateWork(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) String lraId) throws NotFoundException {
        log.info("HotelServiceResource compensate() called for LRA : " + lraId);
        // Business logic to compensate the work related to this LRA
        Hotel booking = hotelService.get(lraId);
        if(booking == null){
            return Response.status(Response.Status.NOT_FOUND).entity("Hotel booking not found").build();
        }
        if (booking.getStatus() == Hotel.BookingStatus.PROVISIONAL) {
            booking.setStatus(Hotel.BookingStatus.CANCELLED);
            hotelService.updateHotel(booking);
            return Response.ok(ParticipantStatus.Compensated.name()).build();
        }
        booking.setStatus(Hotel.BookingStatus.FAILED);
        hotelService.updateHotel(booking);
        return Response.ok(ParticipantStatus.FailedToCompensate.name()).build();
    }

    @GET
    @Path("/status")
    @Produces(MediaType.TEXT_PLAIN)
    @Status
    public Response status(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) String lraId, @HeaderParam(LRA_HTTP_PARENT_CONTEXT_HEADER) String parentLRA) {
        log.info("HotelServiceResource status() called for LRA : " + lraId);
        if (parentLRA != null) { // is the context nested
            // code which is sensitive to executing with a nested context goes here
        }
        Hotel.BookingStatus status = hotelService.get(lraId).getStatus();
        // Business logic (provided by the business developer)
        if(status == Hotel.BookingStatus.CONFIRMED) {
            return Response.ok(ParticipantStatus.Completed).build();
        }
        return Response.ok(ParticipantStatus.Compensated).build();
    }

    @PUT
    @Path("/after")
    @AfterLRA
    @Consumes(MediaType.TEXT_PLAIN)
    public Response afterLRA(@HeaderParam(LRA_HTTP_ENDED_CONTEXT_HEADER) String lraId, String status) throws NotFoundException {
        log.info("After LRA Called : " + lraId);
        log.info("Final LRA Status : " + status);
        // Clean up of resources held by this LRA
        return Response.ok().build();
    }


    @DELETE
    @Path("/hotel")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteAll() {
        log.info("Delete All Hotel bookings");
        hotelService.deleteAll();
        return Response.ok().build();
    }


    @GET
    @Path("/hotel/{bookingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooking(@PathParam("bookingId") String bookingId) {
        log.info("Get Hotel Booking : " + bookingId);
        return Response.ok(hotelService.get(bookingId)).build();
    }

    @GET
    @Path("/hotel")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        log.info("Get All Hotel bookings");
        return Response.ok(hotelService.getAll()).build();
    }

    @PUT
    @Path("/maxbookings")
    @Produces(MediaType.TEXT_PLAIN)
    public Response setMaxBookingCount(@QueryParam("count") @DefaultValue("3") Integer maxBookingCount) {
        HotelService.MAX_BOOKING = maxBookingCount;
        log.info("Set Max Hotel Booking Count: " + maxBookingCount);
        return Response.ok(maxBookingCount).build();
    }

}
