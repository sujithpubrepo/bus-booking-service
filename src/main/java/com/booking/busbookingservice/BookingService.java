package com.booking.busbookingservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    Producer producer;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${inventorylookup}")
    private String inventorylookup;

    @Value("${bussearch}")
    private String bussearch;

    public List<Busroute> searchBus(String source, String destination, String date) {
        Flux<Busroute> busroutes = (webClientBuilder.build().get().
                uri(uriBuilder -> uriBuilder
                        .path(bussearch)
                        .queryParam("source", source)
                        .queryParam("destination", destination)
                        .queryParam("date", date)
                        .build()).retrieve().bodyToFlux(Busroute.class));


        return busroutes.collectList().block();
    }

    public Inventory inventory(InventorySearch inventorySearch) {
        Mono<Inventory> paymentStatus = webClientBuilder.build().post().
                uri(inventorylookup).
                body(inventorySearch,InventorySearch.class).retrieve().bodyToMono(Inventory.class);

        return paymentStatus.block();
    }

    public Booking book(Inventory inventory) {
        String bookingid ="INV"+(int)(Math.random()*100000);
        Booking booking = new Booking();
        booking.setBookingid(bookingid);
        booking.setBusid(inventory.getBusid());
        booking.setBookingdate(inventory.getDate());
        booking.setSource(inventory.getSource());
        booking.setDestination(inventory.getDestination());
        booking.setNumberofseats(inventory.getNumberofseats());
        booking.setStatus("PENDING");
        booking.setLastupdated(LocalDate.now());
        booking.setBookingtype("ADD");
        booking.setInventoryid(inventory.getInventoryid());
        booking.setPrice(inventory.getPrice());
        booking.setAmount(inventory.getPrice()*inventory.getNumberofseats());
        booking.setUserid(inventory.getUserid());
        bookingRepository.save(booking);

        try {
            producer.sendBooking(getBookingDetails(booking));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return booking;
    }

    public Booking cancel(String bookingid) {
        Booking booking = bookingRepository.findByBookingid(bookingid);
        booking.setStatus("CANCEL_INITIATED");
        bookingRepository.save(booking);
        try {
            producer.sendBooking(getBookingDetails(booking));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return booking;
    }

    public Booking getBooking(String bookingid) {
        return bookingRepository.findByBookingid(bookingid);
    }

    public void updateBookingStatus(BookingDetails bookingDetails) {
       Booking booking =  bookingRepository.findByBookingid(bookingDetails.getBookingid());
       booking.setPaymentid(bookingDetails.getPaymentid());
       booking.setStatus(bookingDetails.getStatus());
       booking.setReason(bookingDetails.getReason());
       bookingRepository.save(booking);

    }

    private BookingDetails getBookingDetails(Booking booking){
        boolean isCancel = "CANCEL_INITIATED".equals(booking.getStatus());
        BookingDetails bookingDetails= new BookingDetails();
        bookingDetails.setBookingid(booking.getBookingid());
        bookingDetails.setBookingtype(booking.getBookingtype());
        bookingDetails.setNumberofseats(isCancel?-1*booking.getNumberofseats():booking.getNumberofseats());
        bookingDetails.setInventoryid(booking.getInventoryid());
        bookingDetails.setAmount(isCancel?-1*booking.getAmount():booking.getAmount());
        bookingDetails.setStatus(booking.getStatus());
        bookingDetails.setUserid(booking.getUserid());

        return bookingDetails;
    }

}