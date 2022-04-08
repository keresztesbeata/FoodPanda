package app.controller;

import app.dto.RestaurantDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.exceptions.InvalidOperationException;
import app.service.api.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestaurantRestController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurant/all")
    public ResponseEntity getAllRestaurants() {
        return ResponseEntity.ok().body(restaurantService.getAllRestaurants());
    }

    @GetMapping("/restaurant")
    public ResponseEntity getRestaurantByName(@RequestParam String name) {
        try {
            return ResponseEntity.ok().body(restaurantService.getRestaurantByName(name));
        }catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/restaurant/delivery_zone")
    public ResponseEntity getRestaurantByDeliveryZone(@RequestParam String deliveryZoneName) {
        try {
            return ResponseEntity.ok().body(restaurantService.getRestaurantsByDeliveryZone(deliveryZoneName));
        }catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/admin/restaurant")
    public ResponseEntity getRestaurantOfAdmin(@RequestParam String admin) {
        try {
            return ResponseEntity.ok().body(restaurantService.getRestaurantOfAdmin(admin));
        }catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PostMapping(value = "/admin/restaurant/new")
    public ResponseEntity addRestaurant(@RequestBody RestaurantDto restaurantDto) {
        try{
            restaurantService.addRestaurant(restaurantDto);
            return ResponseEntity.ok().build();
        }catch(InvalidDataException | DuplicateDataException | InvalidOperationException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
        }
    }

}
