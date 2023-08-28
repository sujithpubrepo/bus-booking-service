package com.booking.busbookingservice;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "passengers")
public class Passenger {
    @Id
    @Column(name = "passengerid", nullable = false, length = 10)
    private String passengerid;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bookingid", nullable = false)
    private Booking bookingid;

}