package com.booking.busbookingservice;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class Inventory {

    private String inventoryid;

    private String busid;

    private Integer numberofseats;

    private Integer availableseats;

    private LocalDate date;
    private Double price;

    private String userid;

    private String source;
    private String destination;

    private List<Passenger> passengers;

}