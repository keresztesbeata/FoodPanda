package app.controller;

import app.dto.RestaurantDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.exceptions.InvalidOperationException;
import app.service.api.RestaurantService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static app.controller.Utils.getCurrentUser;

@RestController
@Log4j2
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
        } catch (EntityNotFoundException e) {
            log.error("RestaurantRestController: getRestaurantByName {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/restaurant/delivery_zone")
    public ResponseEntity getRestaurantByDeliveryZone(@RequestParam String deliveryZoneName) {
        try {
            return ResponseEntity.ok().body(restaurantService.getRestaurantsByDeliveryZone(deliveryZoneName));
        } catch (EntityNotFoundException e) {
            log.error("RestaurantRestController: getRestaurantByDeliveryZone {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/admin/restaurant")
    public ResponseEntity getRestaurantOfAdmin() {
        try {
            return ResponseEntity.ok().body(restaurantService.getRestaurantOfAdmin(getCurrentUser()));
        } catch (EntityNotFoundException e) {
            log.error("RestaurantRestController: getRestaurantOfAdmin {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PostMapping(value = "/admin/restaurant/new")
    public ResponseEntity addRestaurant(@RequestBody RestaurantDto restaurantDto) {
        try {
            restaurantService.addRestaurant(restaurantDto);
            return ResponseEntity.ok().build();
        } catch (InvalidDataException | DuplicateDataException | InvalidOperationException e) {
            log.error("RestaurantRestController: addRestaurant {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PostMapping(value = "/admin/restaurant/menu/export")
    public ResponseEntity exportMenuOfRestaurant(@RequestParam String restaurant) {
        try {
            restaurantService.exportMenu(restaurant);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("RestaurantRestController: exportMenuOfRestaurant {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
}
