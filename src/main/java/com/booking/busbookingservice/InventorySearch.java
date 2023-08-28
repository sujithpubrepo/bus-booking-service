package com.booking.busbookingservice;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InventorySearch {

    private String busid;

    private LocalDate date;

}
