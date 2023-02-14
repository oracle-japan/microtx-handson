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






import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


@ApplicationScoped
public class HotelService {

    @PersistenceContext(unitName = "test")
    private EntityManager entityManager;

    private static final Logger log = Logger.getLogger(HotelService.class.getSimpleName());

//    private Map<String, Booking> bookings = new HashMap<>();
    public static int MAX_BOOKING = 3;

    /**
     * Save the hotel booking in memory (HashMap)
     * @param bookingId Hotel booking Identity
     * @param hotelName Hotel name to be booked
     * @return hotel booking details
     */
//    public Booking book(String bookingId, String hotel) {
//        Booking booking = new Booking(bookingId, hotel, "Hotel", null);
//        if(bookings.size() >= MAX_BOOKING){
//            booking.setStatus(Booking.BookingStatus.FAILED);
//        }
//        Booking earlierBooking = bookings.putIfAbsent(booking.getId(), booking);
//        return earlierBooking == null ? booking : earlierBooking;
//    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Hotel book(String bookingId, String hotelName) throws BadRequestException {

        Hotel hotel=  entityManager.find(Hotel.class, bookingId);
        if (hotel == null) {

            hotel = new Hotel();

            hotel.setId(bookingId);
            hotel.setName(hotelName);
            hotel.setStatus(Hotel.BookingStatus.PROVISIONAL);
            hotel.setType("Hotel");
            try {
                hotel.setEncodedId(URLEncoder.encode(bookingId, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.info(e.getLocalizedMessage());
            }
            int count = ((Number)entityManager.createNamedQuery("getHotelBookingCount").getSingleResult()).intValue();
            if(count >= MAX_BOOKING){
                hotel.setStatus(Hotel.BookingStatus.FAILED);
            }
            try {
                entityManager.persist(hotel);
            } catch (Exception e) {
                throw new BadRequestException(Response.status(400).entity("Unable to create hotel booking record with ID " + bookingId).build());
            }
        }

        return hotel;
    }



    /**
     * Get hotel booking details
     * @param bookingId Hotel booking Identity
     * @return hotel booking details
     */
//    public Booking get(String bookingId) throws NotFoundException {
//        if (!bookings.containsKey(bookingId)) {
//            throw new NotFoundException(Response.status(404).entity("Invalid Booking id: " + bookingId).build());
//        }
//        return bookings.get(bookingId);
//    }



    public Hotel get(String bookingId) throws NotFoundException{
        Hotel hotel = entityManager.find(Hotel.class, bookingId);
        if (hotel == null) {
            throw new NotFoundException(Response.status(404).entity("Invalid Booking id: " + bookingId).build());
        }
        return hotel;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Hotel updateHotel(Hotel hotel){
        return entityManager.merge(hotel);
    }


    /**
     * Get all hotel bookings
     * @return all hotel bookings
     */
    public List<Hotel> getAll() {
        return entityManager.createNamedQuery("getHotels", Hotel.class).getResultList();
    }

    /**
     * delete all hotel bookings
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteAll() {
         entityManager.createNamedQuery("deleteHotels").executeUpdate();
    }
}
