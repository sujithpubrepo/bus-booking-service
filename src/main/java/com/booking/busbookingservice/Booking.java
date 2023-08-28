package com.booking.busbookingservice;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @Column(name = "bookingid", nullable = false, length = 10)
    private String bookingid;

    @Column(name = "busid", nullable = false, length = 10)
    private String busid;

    @Column(name = "bookingdate", nullable = false)
    private LocalDate bookingdate;

    @Column(name = "source", nullable = false, length = 20)
    private String source;

    @Column(name = "destination", nullable = false, length = 20)
    private String destination;

    @Column(name = "numberofseats", nullable = false)
    private Integer numberofseats;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "lastupdated", nullable = false)
    private LocalDate lastupdated;

    @Column(name = "reason", length = 20)
    private String reason;

    @Column(name = "bookingtype", nullable = false, length = 10)
    private String bookingtype;

    @Column(name = "paymentid", length = 10)
    private String paymentid;

    @Column(name = "inventoryid", nullable = false, length = 10)
    private String inventoryid;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "amount", nullable = false)
    private Double amount;


    @Column(name = "userid", nullable = false, length = 20)
    private String userid;

}