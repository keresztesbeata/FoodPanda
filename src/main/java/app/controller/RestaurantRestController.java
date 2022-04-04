package app.controller;

import app.dto.RestaurantDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.service.api.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestaurantRestController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurant/all")
    public List<RestaurantDto> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/restaurant")
    public RestaurantDto getRestaurantByName(@RequestParam String name) throws EntityNotFoundException {
        return restaurantService.getRestaurantByName(name);
    }

    @GetMapping("/restaurant/delivery_zone")
    public List<RestaurantDto> getRestaurantByDeliveryZone(@RequestParam String deliveryZoneName) throws EntityNotFoundException {
        return restaurantService.getRestaurantsByDeliveryZone(deliveryZoneName);
    }

    @GetMapping("/restaurant/admin")
    public RestaurantDto getRestaurantOfAdmin(@RequestParam String admin) throws EntityNotFoundException {
        return restaurantService.getRestaurantOfAdmin(admin);
    }

    @PostMapping(value = "/restaurant/admin/new")
    public void addRestaurant(@RequestBody RestaurantDto restaurantDto) throws InvalidDataException, DuplicateDataException {
        restaurantService.addRestaurant(restaurantDto);
    }

}
