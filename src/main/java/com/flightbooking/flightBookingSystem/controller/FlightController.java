package com.flightbooking.flightBookingSystem.controller;

import com.flightbooking.flightBookingSystem.dto.FlightDTO;
import com.flightbooking.flightBookingSystem.dto.FlightSeatInventoryDTO;
import com.flightbooking.flightBookingSystem.payload.ApiResponse;
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

    // getting flights using pagination
    @GetMapping
    public ResponseEntity<ApiResponse<List<FlightDTO>>> getAllFlights(@RequestParam int page, @RequestParam int size) {
        List<FlightDTO> flights = flightService.getAllFlightsWithPagination(page, size);
        ApiResponse<List<FlightDTO>> response = new ApiResponse<>(true, "Flights fetched Successfully", flights);
        return ResponseEntity.ok(response);
    }

    // add flight
    @PostMapping
    public ResponseEntity<ApiResponse<FlightDTO>> addFlight(@Valid @RequestBody Map<String, Object> request) {
        FlightDTO flightDTO = objectMapper.convertValue(request.get("flight"), FlightDTO.class);
        List<FlightSeatInventoryDTO> inventories = objectMapper.convertValue(request.get("inventories"), new TypeReference<List<FlightSeatInventoryDTO>>() {
        });

        FlightDTO createFlight = flightService.addFlight(flightDTO, inventories);
        ApiResponse<FlightDTO> response = new ApiResponse<>(true, "Flight Added Successfully", createFlight);
        return ResponseEntity.ok(response);
    }

    // duration
    @GetMapping("/sort/duration")
    public ResponseEntity<ApiResponse<List<FlightDTO>>> sortByDuration(@RequestParam boolean ascending) {
        List<FlightDTO> flights = flightService.getAllFLightsSortedByDuration(ascending);
        ApiResponse<List<FlightDTO>> response = new ApiResponse<>(true, "Flights sorted by duration", flights);
        return ResponseEntity.ok(response);
    }

    // early departure
    @GetMapping("/early")
    public ResponseEntity<ApiResponse<List<FlightDTO>>> earlyFlights() {
        List<FlightDTO> flights = flightService.getEarlyDepartureFlights();
        ApiResponse<List<FlightDTO>> response = new ApiResponse<>(true, "Early departure flights fetched", flights);
        return ResponseEntity.ok(response);
    }

    // late departure
    @GetMapping("/late")
    public ResponseEntity<ApiResponse<List<FlightDTO>>> lateFlights() {
        List<FlightDTO> flights = flightService.getLateDepartureFlights();
        ApiResponse<List<FlightDTO>> response = new ApiResponse<>(true, "Late departure flights fetched", flights);
        return ResponseEntity.ok(response);
    }


    // get flight by id
    @GetMapping("/number/{flightId}")
    public ResponseEntity<ApiResponse<FlightDTO>> getFlightById(@PathVariable int flightId) {
        FlightDTO flightDTO = flightService.getFlightById(flightId);
        ApiResponse<FlightDTO> response = new ApiResponse<>(true, "Flight fetched successfully", flightDTO);
        return ResponseEntity.ok(response);
    }

    // update
    @PutMapping("/{flightId}")
    public ResponseEntity<ApiResponse<FlightDTO>> updateFlight(@PathVariable int flightId,
                                                  @Valid @RequestBody FlightDTO flightDTO) {
        FlightDTO updatedFlight = flightService.updateFlight(flightId, flightDTO);
        ApiResponse<FlightDTO> response = new ApiResponse<>(true, "Flight updated successfully", updatedFlight);
        return ResponseEntity.ok(response);
    }

    // Get seatInventory By flight
    @GetMapping("/seat-inventory/{flightId}")
    public ResponseEntity<ApiResponse<List<FlightSeatInventoryDTO>>> getSeatInventory(@PathVariable int flightId) {
        List<FlightSeatInventoryDTO> inventories = flightService.getSeatInventory(flightId);
        ApiResponse<List<FlightSeatInventoryDTO>> response = new ApiResponse<>(true, "Seat Inventory fetched", inventories);
        return ResponseEntity.ok(response);
    }

    // delete
    @DeleteMapping("/{flightId}")
    public ResponseEntity<ApiResponse<Void>> deleteFlight(@PathVariable int flightId) {
        flightService.deleteFlight(flightId);
        ApiResponse<Void> response = new ApiResponse<>(true, "Flight deleted successfully", null);
        return ResponseEntity.ok(response);
    }

}
