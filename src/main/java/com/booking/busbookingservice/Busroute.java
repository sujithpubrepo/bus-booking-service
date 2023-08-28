package com.booking.busbookingservice;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;

@Getter
@Setter
public class Busroute {

    private String busid;

    private String source;

    private String destination;

    private Double price;

    private Integer totalseats;

    private String busname;

    private LocalTime starttime;

    private LocalTime endtime;

}