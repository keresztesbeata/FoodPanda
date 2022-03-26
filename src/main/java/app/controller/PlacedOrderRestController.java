package app.controller;

import app.dto.PlacedOrderDto;
import app.service.api.PlacedOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PlacedOrderRestController {

    @Autowired
    private PlacedOrderService placedOrderService;

    @PostMapping("/placed_order/new")
    public void addOrder(@RequestBody PlacedOrderDto placedOrderDto) {
        placedOrderService.addOrder(placedOrderDto);
    }

    @GetMapping("/placed_order/user_all")
    public List<PlacedOrderDto> getAllPlacedOrdersOfUser(@RequestParam String username) {
        return placedOrderService.getPlacedOrdersOfUserByStatus(username, Optional.empty());
    }

    @GetMapping("/placed_order/user_status")
    public List<PlacedOrderDto> getAllPlacedOrdersOfUserByStatus(@RequestParam String username, @RequestParam String orderStatus) {
        return placedOrderService.getPlacedOrdersOfUserByStatus(username, Optional.of(orderStatus));
    }

    @GetMapping("/placed_order/restaurant_all")
    public List<PlacedOrderDto> getAllPlacedOrdersOfRestaurant(@RequestParam String restaurant) {
        return placedOrderService.getPlacedOrdersOfRestaurantByStatus(restaurant, Optional.empty());
    }

    @GetMapping("/placed_order/restaurant_status")
    public List<PlacedOrderDto> getAllPlacedOrdersOfRestaurantByStatus(@RequestParam String restaurant, @RequestParam String orderStatus) {
        return placedOrderService.getPlacedOrdersOfRestaurantByStatus(restaurant, Optional.of(orderStatus));
    }
}
