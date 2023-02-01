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

import oracledb from 'oracledb';
import { dbConfig }  from '../dbconfig';


let usr = process.env.DBUSER || dbConfig.dbuser || 'admin';
let pwd = process.env.DBPASSWORD || dbConfig.dbpassword || '';
let ins = process.env.CONNECT_STRING || dbConfig.connect_string || 'localhost/xepdb1';

let dbConfiged = {
    user: usr,
    password: pwd,
    connectString: ins
    };

export enum BookingStatus {
    CONFIRMED = "CONFIRMED",
    CANCELLED = "CANCELLED",
    PROVISIONAL = "PROVISIONAL",
    CONFIRMING = "CONFIRMING",
    CANCEL_REQUESTED = "CANCEL_REQUESTED",
    FAILED = "FAILED"
}

// export let bookingMap: Map<string, Booking> = new Map();
export let MAX_CONFIRMED_BOOKING: number = 2; //default
export class Booking {
    public id: string;
    public name: string;
    public status: BookingStatus = BookingStatus.PROVISIONAL; //default
    public type: string;
    public details: Booking [] = [];
    public encodedId: string

    constructor(id : string, name : string, type : string) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.encodedId = encodeURIComponent(id);
    }
}

// export function getConfirmedBookingsCount(bookingMap: Map<string, Booking>): number {
//     let confirmedCount: number = 0;
//     bookingMap.forEach(function(booking, id) {
//         if (booking.status == BookingStatus.CONFIRMED) {
//             confirmedCount++;
//         }
//     });
//     return confirmedCount;
// }

export function setMaxConfirmedBooking(num: number){
    MAX_CONFIRMED_BOOKING = num;
}


export async function initdb() {

    let connection;

    try {

        console.log(dbConfiged);
        // console.log(oracledb);

        // create default connection pool
        await oracledb.createPool(dbConfiged);

        connection = await oracledb.getConnection(/*use connection pool*//*dbConfiged*/);
        // console.log(connection);
        
        await connection.execute(`begin
            execute immediate 'drop table flight';
            exception when others then if sqlcode <> -942 then raise; end if;
        end;`);

        await connection.execute(`create table flight (
            id varchar2(255 char) not null, 
            encodedid varchar2(255 char), 
            name varchar2(255 char) not null, 
            status varchar2(255 char) not null, 
            type varchar2(255 char) not null,
            primary key (id))`);

    } catch (err) {
        console.error(err);
    } finally {
        if (connection) {
            try {
                await connection.close();
            } catch (err) {
                console.error(err);
            }
        }
    }

}


export class BookingService {

        static async  selectById(bookingId: string) : Promise<Booking | null> {

            let connection;
            try {
                connection = await oracledb.getConnection(/*use connection pool*//*dbConfiged*/);
                let result = await connection.execute<Booking>(`SELECT id as "id", encodedid as "encodedId" ,name as "name",status as "status",type as "type" FROM flight where id = '${bookingId}'`,{},{outFormat: oracledb.OUT_FORMAT_OBJECT});
                console.log("Result is:", result);
                return result.rows && Array.isArray(result.rows) ? result.rows[0] as Booking : null;
            } catch (err) {
                throw (err);
            } finally {
                if (connection) {
                    try {
                        await connection.close(); // Put the connection back in the pool
                    } catch (err) {
                        console.error (err);
                    }
                }
            }
            
        }


        static async  deleteById(bookingId: string) : Promise<void> {

            let connection;
            try {
                connection = await oracledb.getConnection(/*use connection pool*//*dbConfiged*/);
                let result = await connection.execute<Booking>(`DELETE FROM flight where id = '${bookingId}'`);
                await connection.commit();
                console.log("deleteById Result is:", result);
            } catch (err) {
                console.error (err);
            } finally {
                if (connection) {
                    try {
                        await connection.close(); // Put the connection back in the pool
                    } catch (err) {
                        console.error (err);
                    }
                }
            }
            
        }


        static async  getCount() : Promise<Number> {

            let connection;
            try {
                connection = await oracledb.getConnection(/*use connection pool*//*dbConfiged*/);
                let result = await connection.execute(`SELECT count(*) FROM flight where status = '`  + BookingStatus.CONFIRMED + `'` );
                console.log("Result is:", result);
                return result.rows && result.rows[0] && Array.isArray(result.rows[0]) ? result.rows[0][0] : 0;
            } catch (err) {
                throw (err);
            } finally {
                if (connection) {
                    try {
                        await connection.close(); // Put the connection back in the pool
                    } catch (err) {
                        console.error (err);
                    }
                }
            }
            
        }

        static async selectAllAsObject() : Promise<Booking[]> {

            let connection;
            try {
                connection = await oracledb.getConnection(/*use connection pool*//*dbConfiged*/);
                let result = await connection.execute<Booking>(`SELECT id as "id", encodedid as "encodedId" ,name as "name",status as "status",type as "type" FROM flight`,[],{outFormat: oracledb.OUT_FORMAT_OBJECT});
                console.log("Result is:", result);
                return result.rows! as Booking[];
            } catch (err) {
                throw (err);
              
            } finally {
                if (connection) {
                    try {
                        await connection.close(); // Put the connection back in the pool
                    } catch (err) {
                        console.error (err);
                    }
                }
            }
            
        }




        static async  insert(booking: Booking) : Promise<boolean> {

            let connection;
            try {
                connection = await oracledb.getConnection(/*use connection pool*//*dbConfiged*/);
                const sql =
                'INSERT INTO flight (id, encodedid,name,status,type) VALUES (:id, :encodedid,:name,:status,:type )';
                const binds = [booking.id, booking.encodedId,booking.name,booking.status,booking.type];
                const options = {autoCommit: true} ;
                let result = await connection.execute(sql, binds, options);
                return true;
            } catch (err) {
                console.error(err);
                return false;
            } finally {
                if (connection) {
                    try {
                        await connection.close(); // Put the connection back in the pool
                    } catch (err) {
                        console.error(err);
                    }
                }
            }
            
        }


        static async  updateStatus(booking: Booking) : Promise<boolean> {

            let connection;
            try {
                connection = await oracledb.getConnection(/*use connection pool*//*dbConfiged*/);
                const sql =
                'UPDATE flight set status = :status where id = :id';
                const binds = [booking.status, booking.id];
                const options = {autoCommit: true} ;
                let result = await connection.execute(sql, binds, options);
                return true;
            } catch (err) {
                console.error(err);
                return false;
            } finally {
                if (connection) {
                    try {
                        await connection.close(); // Put the connection back in the pool
                    } catch (err) {
                        console.error(err);
                    }
                }
            }
            
        }


}

