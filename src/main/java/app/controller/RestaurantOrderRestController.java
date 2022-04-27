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

import static app.controller.Utils.getCurrentUser;

@RestController
public class RestaurantOrderRestController {

    @Autowired
    private RestaurantOrderService restaurantOrderService;

    @PostMapping("/customer/order/new")
    public ResponseEntity addOrder(@RequestBody RestaurantOrderDto orderDetails) {
        try {
            orderDetails.setCustomer(getCurrentUser().getUsername());
            restaurantOrderService.addOrder(orderDetails);
            return ResponseEntity.ok().build();
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
        }
    }

    @GetMapping("/customer/orders")
    public ResponseEntity getAllOrdersOfCustomer() {
        return ResponseEntity.ok().body(restaurantOrderService.getOrdersOfCustomerByStatus(getCurrentUser(), Optional.empty()));
    }

    @GetMapping("/customer/orders/status")
    public ResponseEntity getAllOrdersOfCustomerByStatus(@RequestParam String orderStatus) {
        return ResponseEntity.ok().body(restaurantOrderService.getOrdersOfCustomerByStatus(getCurrentUser(), Optional.of(orderStatus)));
    }

    @GetMapping("/admin/restaurant/orders/orderNumber")
    public ResponseEntity getOrderByOrderNumber(@RequestParam String orderNumber) {
        try {
            return ResponseEntity.ok().body(restaurantOrderService.getOrderByOrderNumber(orderNumber));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/order_statuses")
    public ResponseEntity getAllOrderStates() {
        return ResponseEntity.ok().body(restaurantOrderService.getAllOrderStatuses());
    }

    @GetMapping("/admin/restaurant/orders")
    public ResponseEntity getAllOrdersOfRestaurant(@RequestParam String restaurant) {
        return ResponseEntity.ok().body(restaurantOrderService.getOrdersOfRestaurantByStatus(restaurant, Optional.empty()));
    }

    @GetMapping("/admin/restaurant/orders/status")
    public ResponseEntity getAllOrdersOfRestaurantByStatus(@RequestParam String restaurant, @RequestParam String orderStatus) {
        return ResponseEntity.ok().body(restaurantOrderService.getOrdersOfRestaurantByStatus(restaurant, Optional.of(orderStatus)));
    }

    @PostMapping("/admin/restaurant/order/update_status")
    public ResponseEntity updateOrderStatus(@RequestParam String orderNumber, @RequestParam String orderStatus) {
        try {
            restaurantOrderService.updateOrderStatus(orderNumber, orderStatus);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
