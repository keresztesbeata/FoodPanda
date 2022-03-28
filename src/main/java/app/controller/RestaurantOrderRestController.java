package app.controller;

import app.dto.RestaurantOrderDto;
import app.service.api.RestaurantOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RestaurantOrderRestController {

    @Autowired
    private RestaurantOrderService restaurantOrderService;

    @PostMapping("/restaurant/order/user/new")
    public void addOrder(@RequestBody RestaurantOrderDto restaurantOrderDto) {
        restaurantOrderService.addOrder(restaurantOrderDto);
    }

    @GetMapping("/restaurant/order/user/all")
    public List<RestaurantOrderDto> getAllOrdersOfUser(@RequestParam String username) {
        return restaurantOrderService.getOrdersOfUserByStatus(username, Optional.empty());
    }

    @GetMapping("/restaurant/order/user/status")
    public List<RestaurantOrderDto> getAllOrdersOfUserByStatus(@RequestParam String username, @RequestParam String orderStatus) {
        return restaurantOrderService.getOrdersOfUserByStatus(username, Optional.of(orderStatus));
    }

    @GetMapping("/restaurant/order/admin/orderNumber")
    public RestaurantOrderDto getOrderByOrderNumber(@RequestParam String orderNumber) {
        return restaurantOrderService.getOrderByOrderNumber(orderNumber);
    }

    @GetMapping("/restaurant/order/admin/all")
    public List<RestaurantOrderDto> getAllOrdersOfRestaurant(@RequestParam String restaurant) {
        return restaurantOrderService.getOrdersOfRestaurantByStatus(restaurant, Optional.empty());
    }

    @GetMapping("/restaurant/order/admin/status")
    public List<RestaurantOrderDto> getAllOrdersOfRestaurantByStatus(@RequestParam String restaurant, @RequestParam String orderStatus) {
        return restaurantOrderService.getOrdersOfRestaurantByStatus(restaurant, Optional.of(orderStatus));
    }

    @GetMapping("/restaurant/order/admin/orderNumber/available_statuses")
    public List<String> getAvailableOrderStatusesForOrder(@RequestParam String orderNumber) {
        return restaurantOrderService.getAvailableStatusForOrder(orderNumber);
    }

    @PostMapping("/restaurant/order/admin/orderNumber/update_status")
    public void updateOrderStatus(@RequestParam String orderNumber, @RequestParam String orderStatus) {
        restaurantOrderService.updateOrderStatus(orderNumber, orderStatus);
    }
}
