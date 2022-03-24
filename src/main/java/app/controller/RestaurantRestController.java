package app.controller;

import app.dto.DeliveryZoneDto;
import app.dto.RestaurantDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
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

    @GetMapping("/restaurant/id/{id}")
    public RestaurantDto getRestaurantById(@PathVariable Integer id) throws EntityNotFoundException {
        return restaurantService.getRestaurantById(id);
    }

    @GetMapping("/restaurant/{name}")
    public RestaurantDto getRestaurantByName(@PathVariable String name) throws EntityNotFoundException {
        return restaurantService.getRestaurantByName(name);
    }

    @GetMapping("/restaurant/delivery_zone/{deliveryZoneName}")
    public List<RestaurantDto> getRestaurantByDeliveryZone(@PathVariable String deliveryZoneName) throws EntityNotFoundException {
        DeliveryZoneDto deliveryZoneDto = new DeliveryZoneDto();
        deliveryZoneDto.setName(deliveryZoneName);
        return restaurantService.getRestaurantsByDeliveryZone(deliveryZoneDto);
    }

    @PostMapping(value = "/restaurant/new")
    public void addRestaurant(@RequestBody RestaurantDto restaurantDto) throws DuplicateDataException {
        restaurantService.addRestaurant(restaurantDto);
    }

}
