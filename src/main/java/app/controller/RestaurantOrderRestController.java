package app.controller;

import app.dto.RestaurantOrderDto;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.service.api.RestaurantOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class RestaurantOrderRestController {

    @Autowired
    private RestaurantOrderService restaurantOrderService;

    @PostMapping("/customer/order/new")
    public ResponseEntity addOrder(@RequestBody RestaurantOrderDto orderDetails) {
        try {
            restaurantOrderService.addOrder(orderDetails);
            return ResponseEntity.ok().build();
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
        }
    }

    @GetMapping("/customer/order/all")
    public ResponseEntity getAllOrdersOfUser(@RequestParam String username) {
        return ResponseEntity.ok().body(restaurantOrderService.getOrdersOfUserByStatus(username, Optional.empty()));
    }

    @GetMapping("/customer/order/status")
    public ResponseEntity getAllOrdersOfUserByStatus(@RequestParam String username, @RequestParam String orderStatus) {
        return ResponseEntity.ok().body(restaurantOrderService.getOrdersOfUserByStatus(username, Optional.of(orderStatus)));
    }

    @GetMapping("/admin/restaurant/order/orderNumber")
    public ResponseEntity getOrderByOrderNumber(@RequestParam String orderNumber) {
        try {
            return ResponseEntity.ok().body(restaurantOrderService.getOrderByOrderNumber(orderNumber));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/admin/restaurant/order/all")
    public ResponseEntity getAllOrdersOfRestaurant(@RequestParam String restaurant) {
        return ResponseEntity.ok().body(restaurantOrderService.getOrdersOfRestaurantByStatus(restaurant, Optional.empty()));
    }

    @GetMapping("/admin/restaurant/order/status")
    public ResponseEntity getAllOrdersOfRestaurantByStatus(@RequestParam String restaurant, @RequestParam String orderStatus) {
        return ResponseEntity.ok().body(restaurantOrderService.getOrdersOfRestaurantByStatus(restaurant, Optional.of(orderStatus)));
    }

    @GetMapping("/admin/restaurant/order/orderNumber/available_statuses")
    public ResponseEntity getAvailableOrderStatusesForOrder(@RequestParam String orderNumber) {
        try {
            return ResponseEntity.ok().body(restaurantOrderService.getAvailableStatusForOrder(orderNumber));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PostMapping("/admin/restaurant/order/orderNumber/update_status")
    public ResponseEntity updateOrderStatus(@RequestParam String orderNumber, @RequestParam String orderStatus) {
        try {
            restaurantOrderService.updateOrderStatus(orderNumber, orderStatus);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
