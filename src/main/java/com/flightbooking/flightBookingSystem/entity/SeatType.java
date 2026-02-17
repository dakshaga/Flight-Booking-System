package com.flightbooking.flightBookingSystem.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "seat_types")
public class SeatType {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "seatType")
    private List<FlightSeatInventory> seatInventories;

    public SeatType() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FlightSeatInventory> getSeatInventories() {
        return seatInventories;
    }

    public void setSeatInventories(List<FlightSeatInventory> seatInventories) {
        this.seatInventories = seatInventories;
    }
}
