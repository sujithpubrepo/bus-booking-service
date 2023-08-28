package com.booking.busbookingservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("findbus")
    public ResponseEntity<List<Busroute>> searchBus(@RequestParam("source") String source, @RequestParam("destination") String destination, String date){
        List<Busroute> routes = bookingService.searchBus(source, destination, date);
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }

    @PostMapping("inventory")
    public ResponseEntity<Inventory> inventory(@RequestBody InventorySearch inventorySearch){
        Inventory inventory = bookingService.inventory(inventorySearch);
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @PostMapping("bus/book")
    public ResponseEntity<Booking> book(@RequestBody Inventory inventory){
        Booking booking = bookingService.book(inventory);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @GetMapping ("bus/book/{bookingid}")
    public ResponseEntity<Booking> getBooking(@PathVariable String bookingid){
        Booking booking = bookingService.getBooking(bookingid);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @DeleteMapping("bus/book/{bookingid}")
    public ResponseEntity<Booking> cancel(@PathVariable("bookingid") String bookingid){
        Booking booking = bookingService.cancel(bookingid);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

}
