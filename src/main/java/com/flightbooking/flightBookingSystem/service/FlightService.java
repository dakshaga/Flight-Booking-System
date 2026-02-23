package com.flightbooking.flightBookingSystem.service;

import com.flightbooking.flightBookingSystem.dto.FlightDTO;
import com.flightbooking.flightBookingSystem.dto.FlightSeatInventoryDTO;
import com.flightbooking.flightBookingSystem.entity.Flight;
import com.flightbooking.flightBookingSystem.entity.FlightSeatInventory;
import com.flightbooking.flightBookingSystem.enums.SeatType;
import com.flightbooking.flightBookingSystem.exception.custom.ResourceNotFoundException;
import com.flightbooking.flightBookingSystem.repository.FlightRepository;
import com.flightbooking.flightBookingSystem.repository.FlightSeatInventoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightSeatInventoryRepository flightSeatInventoryRepository;

    // Constructor
    public FlightService(FlightRepository flightRepository, FlightSeatInventoryRepository flightSeatInventoryRepository) {
        this.flightRepository = flightRepository;
        this.flightSeatInventoryRepository = flightSeatInventoryRepository;
    }

    // Adding of the Flight
    public FlightDTO addFlight(FlightDTO flightDTO, List<FlightSeatInventoryDTO> inventories) {
        Flight flight = new Flight();
        flight.setSource(flightDTO.getSource());
        flight.setDestination(flightDTO.getDestination());
        flight.setDepartureTime(flightDTO.getDepartureTime());
        flight.setArrivalTime(flightDTO.getArrivalTime());

        Flight savedFlight = flightRepository.save(flight);

        // !!!!!!!!!!!!!!!!!!!!HELP!!!!!!!!!!!!!!!!!!!!
        List<FlightSeatInventory> inventoryList = new ArrayList<>();
        for(FlightSeatInventoryDTO invDTO : inventories) {
            FlightSeatInventory inventory = new FlightSeatInventory();
            inventory.setFlight(savedFlight);
            inventory.setSeatType(SeatType.valueOf(invDTO.getSeatType().toUpperCase()));  // check for error handling
            inventory.setTotalSeats(invDTO.getTotalSeats());
            inventory.setAvailableSeats(invDTO.getAvailableSeats());
            inventory.setFare(invDTO.getFare());
            inventoryList.add(inventory);
        }
        flightSeatInventoryRepository.saveAll(inventoryList);
        flightDTO.setId(flight.getId());
        return flightDTO;
    }

    // Update Flight
    public FlightDTO updateFlight(int flightId, FlightDTO flightDTO) {
        Optional<Flight> isFlight = flightRepository.findById(flightId);
        if(isFlight.isEmpty()) {
            throw new ResourceNotFoundException("Flight not found with id: " + flightId);
        }
        Flight flight = isFlight.get();
        flight.setSource(flightDTO.getSource());
        flight.setDestination(flightDTO.getDestination());
        flight.setDepartureTime(flightDTO.getDepartureTime());
        flight.setArrivalTime(flightDTO.getArrivalTime());
        flightRepository.save(flight);

        flightDTO.setId(flight.getId());
        return flightDTO;
    }

    // Delete Flight
    public void deleteFlight(int flightId) {
        if(!flightRepository.existsById(flightId)){
            throw new ResourceNotFoundException("Flight not found with id: " + flightId);
        }
        flightRepository.deleteById(flightId);
    }

    // Get Flight by Id
    public FlightDTO getFlightById(int flightId) {
        Optional<Flight> isFlight = flightRepository.findById(flightId);
        if(isFlight.isEmpty()) {
            throw new ResourceNotFoundException("Flight not found with id: " + flightId);
        }
        return mapToDTO(isFlight.get());
    }


    // Get all flights with pagination
    public List<FlightDTO> getAllFlightsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Flight> flightPage = flightRepository.findAll(pageable);

        List<FlightDTO> dtoList = new ArrayList<>();

        for (Flight flight : flightPage.getContent()) {
            dtoList.add(mapToDTO(flight));
        }

        return dtoList;

    }

    // Get flight with seat inventory
    public List<FlightSeatInventoryDTO> getSeatInventory(int flightId) {
        Optional<Flight> isFlight = flightRepository.findById(flightId);
        if(isFlight.isEmpty()) {
            throw new ResourceNotFoundException("Flight not found with id: " + flightId);
        }

        Flight flight = isFlight.get();
        List<FlightSeatInventoryDTO> inventoryDTOS = new ArrayList<>();
        for(FlightSeatInventory inv : flight.getSeatInventories()) {
            FlightSeatInventoryDTO seatdto = new FlightSeatInventoryDTO();
            seatdto.setId(inv.getId());
            seatdto.setFlightId(flight.getId());
            seatdto.setSeatType(inv.getSeatType().name());
            seatdto.setTotalSeats(inv.getTotalSeats());
            seatdto.setAvailableSeats(inv.getAvailableSeats());
            seatdto.setFare(inv.getFare());
            inventoryDTOS.add(seatdto);
        }

        return inventoryDTOS;
    }

    // Sorting on the basis of duration
    public List<FlightDTO> getAllFLightsSortedByDuration(boolean ascending) {
        List<Flight> flights = flightRepository.findAll();
        flights.sort(Comparator.comparing((Flight f) -> Duration.between(f.getDepartureTime(), f.getArrivalTime())));
        if(!ascending) {
            flights.sort(Comparator.comparing((Flight f) -> Duration.between(f.getDepartureTime(), f.getArrivalTime())).reversed());
        }

        List<FlightDTO> flightByDurationListDTO = new ArrayList<>();
        for(Flight f : flights) {
            flightByDurationListDTO.add(mapToDTO(f));
        }
        return flightByDurationListDTO;
    }

    // Early Departure Flights
    public List<FlightDTO> getEarlyDepartureFlights() {
        List<Flight> flights = flightRepository.findEarlyDepartureFlights();
        List<FlightDTO> earlyDepartureFlightListDTO = new ArrayList<>();
        for(Flight f : flights) {
            earlyDepartureFlightListDTO.add(mapToDTO(f));
        }

        return earlyDepartureFlightListDTO;
    }

    // Late Departure Flights
    public List<FlightDTO> getLateDepartureFlights() {
        List<Flight> flights = flightRepository.findLateDepartureFlights();
        List<FlightDTO> lateDepartureFlightListDTO = new ArrayList<>();
        for(Flight f : flights) {
            lateDepartureFlightListDTO.add(mapToDTO(f));
        }

        return lateDepartureFlightListDTO;
    }

    //Flight->FlightDTO
    private FlightDTO mapToDTO(Flight flight) {
        return new FlightDTO(
                flight.getId(),
                flight.getSource(),
                flight.getDestination(),
                flight.getDepartureTime(),
                flight.getArrivalTime()
        );
    }
}
