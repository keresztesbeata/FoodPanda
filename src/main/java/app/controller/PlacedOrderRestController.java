package app.controller;

import app.dto.PlacedOrderDto;
import app.service.api.PlacedOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PlacedOrderRestController {

    @Autowired
    private PlacedOrderService placedOrderService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/user/placed_order/new")
    public void addOrder(@RequestBody PlacedOrderDto placedOrderDto) {
        placedOrderService.addOrder(placedOrderDto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/placed_order/all")
    public List<PlacedOrderDto> getAllPlacedOrdersOfUser(@RequestParam String username) {
        return placedOrderService.getPlacedOrdersOfUserByStatus(username, Optional.empty());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/placed_order/status")
    public List<PlacedOrderDto> getAllPlacedOrdersOfUserByStatus(@RequestParam String username, @RequestParam String orderStatus) {
        return placedOrderService.getPlacedOrdersOfUserByStatus(username, Optional.of(orderStatus));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/placed_order/id/{id}")
    public PlacedOrderDto getPlacedOrderByOrderNumber(@PathVariable Integer id) {
        return placedOrderService.getPlacedOrderByOrderNumber(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/placed_order/restaurant/all")
    public List<PlacedOrderDto> getAllPlacedOrdersOfRestaurant(@RequestParam String restaurant) {
        return placedOrderService.getPlacedOrdersOfRestaurantByStatus(restaurant, Optional.empty());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/placed_order/restaurant/status")
    public List<PlacedOrderDto> getAllPlacedOrdersOfRestaurantByStatus(@RequestParam String restaurant, @RequestParam String orderStatus) {
        return placedOrderService.getPlacedOrdersOfRestaurantByStatus(restaurant, Optional.of(orderStatus));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/placed_order/available_statuses")
    public List<String> getAvailableOrderStatusesForOrder(@RequestParam Integer orderNumber) {
        return placedOrderService.getAvailableStatusForOrder(orderNumber);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/placed_order/update_status")
    public void updateOrderStatus(@RequestParam Integer orderNumber, @RequestParam String orderStatus) {
        placedOrderService.updateOrderStatus(orderNumber, orderStatus);
    }
}
