package com.oracle.trm.lra.demo.hotel;





import javax.persistence.*;

/**
 * A Hotel entity class. A Hotel is represented as a triple of an
 * ID, a name and a type.
 *
 * Hotel, and Hotel character names are trademarks of Nintendo.
 */
@Entity(name = "Hotel")
@Table(name = "HOTEL")
@Access(AccessType.PROPERTY)
@NamedQueries({
        @NamedQuery(name = "getHotels",
                query = "SELECT p FROM Hotel p"),
        @NamedQuery(name = "getHotelBookingCount",
                query = "SELECT count(p) FROM Hotel p")
})
public class Hotel {

    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private String type;
    private String encodedId;

    public Hotel() {
    }

    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic(optional = false)
    @Column(name = "NAME", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic(optional = false)
    @Column(name = "STATUS", nullable = false)
    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    @Basic(optional = false)
    @Column(name = "TYPE", nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Column(name = "ENCODEDID", nullable = true)
    public String getEncodedId() {
        return encodedId;
    }

    public void setEncodedId(String encodedId) {
        this.encodedId = encodedId;
    }

    public enum BookingStatus {
        CONFIRMED, CANCELLED, PROVISIONAL, CONFIRMING, CANCEL_REQUESTED, FAILED;
    }
}
