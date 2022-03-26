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

    @PostMapping("/placed_order/user/new")
    public void addOrder(@RequestBody PlacedOrderDto placedOrderDto) {
        placedOrderService.addOrder(placedOrderDto);
    }

    @GetMapping("/placed_order/user/all")
    public List<PlacedOrderDto> getAllPlacedOrdersOfUser(@RequestParam String username) {
        return placedOrderService.getPlacedOrdersOfUserByStatus(username, Optional.empty());
    }

    @GetMapping("/placed_order/user/status")
    public List<PlacedOrderDto> getAllPlacedOrdersOfUserByStatus(@RequestParam String username, @RequestParam String orderStatus) {
        return placedOrderService.getPlacedOrdersOfUserByStatus(username, Optional.of(orderStatus));
    }

    @GetMapping("/placed_order/admin/id/{id}")
    public PlacedOrderDto getPlacedOrderByOrderNumber(@PathVariable Integer id) {
        return placedOrderService.getPlacedOrderByOrderNumber(id);
    }

    @GetMapping("/placed_order/admin/restaurant/all")
    public List<PlacedOrderDto> getAllPlacedOrdersOfRestaurant(@RequestParam String restaurant) {
        return placedOrderService.getPlacedOrdersOfRestaurantByStatus(restaurant, Optional.empty());
    }

    @GetMapping("/placed_order/admin/restaurant/status")
    public List<PlacedOrderDto> getAllPlacedOrdersOfRestaurantByStatus(@RequestParam String restaurant, @RequestParam String orderStatus) {
        return placedOrderService.getPlacedOrdersOfRestaurantByStatus(restaurant, Optional.of(orderStatus));
    }

    @GetMapping("/placed_order/admin/available_statuses")
    public List<String> getAvailableOrderStatusesForOrder(@RequestParam Integer orderNumber) {
        return placedOrderService.getAvailableStatusForOrder(orderNumber);
    }

    @PostMapping("/placed_order/admin/update_status")
    public void updateOrderStatus(@RequestParam Integer orderNumber, @RequestParam String orderStatus) {
        placedOrderService.updateOrderStatus(orderNumber, orderStatus);
    }
}
