package com.flightbooking.flightBookingSystem.controller;

import com.flightbooking.flightBookingSystem.dto.FlightDTO;
import com.flightbooking.flightBookingSystem.dto.FlightSeatInventoryDTO;
import com.flightbooking.flightBookingSystem.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/flight")
public class FlightController {

    private final FlightService flightService;
    private final ObjectMapper objectMapper;

    public FlightController(FlightService flightService, ObjectMapper objectMapper) {
        this.flightService = flightService;
        this.objectMapper = objectMapper;
    }

    // getting using pagination
    @GetMapping
    public ResponseEntity<List<FlightDTO>> getAllFlights(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(flightService.getAllFlightsWithPagination(page, size));
    }

    // addflight
    @PostMapping("/addflight")
    public ResponseEntity<FlightDTO> addFlight(@Valid @RequestBody Map<String, Object> request) {
        FlightDTO flightDTO = objectMapper.convertValue(request.get("flight"), FlightDTO.class);

        List<FlightSeatInventoryDTO> inventories = objectMapper.convertValue(request.get("inventories"), new TypeReference<List<FlightSeatInventoryDTO>>() {
        });

        FlightDTO createdFlight = flightService.addFlight(flightDTO, inventories);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFlight);
    }

    // duration
    @GetMapping("/sort/duration")
    public ResponseEntity<List<FlightDTO>> sortByDuration(@RequestParam boolean ascending) {
        return ResponseEntity.ok(flightService.getAllFLightsSortedByDuration(ascending));
    }

    // early departure
    @GetMapping("/early")
    public ResponseEntity<List<FlightDTO>> earlyFlights() {
        return ResponseEntity.ok(flightService.getEarlyDepartureFlights());
    }

    // late departure
    @GetMapping("/late")
    public ResponseEntity<List<FlightDTO>> lateFlights() {
        return ResponseEntity.ok(flightService.getLateDepartureFlights());
    }


    // get flight by id
    @GetMapping("/number/{flightId}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable int flightId) {
        return ResponseEntity.ok(flightService.getFlightById(flightId));
    }

    // update
    @PutMapping("/{flightId}")
    public ResponseEntity<FlightDTO> updateFlight(@PathVariable int flightId,
                                                  @Valid @RequestBody FlightDTO flightDTO) {
        return ResponseEntity.ok(flightService.updateFlight(flightId, flightDTO));
    }

    // Get seatInventory By flight
    @GetMapping("/seat-inventory/{flightId}")
    public ResponseEntity<List<FlightSeatInventoryDTO>> getSeatInventory(@PathVariable int flightId) {
        List<FlightSeatInventoryDTO> inventories = flightService.getSeatInventory(flightId);
        return ResponseEntity.ok(inventories);
    }

    // delete
    @DeleteMapping("/{flightId}")
    public ResponseEntity<String> deleteFlight(@PathVariable int flightId) {
        flightService.deleteFlight(flightId);
        return ResponseEntity.ok("Flight Deleted Successfully");
    }

}
