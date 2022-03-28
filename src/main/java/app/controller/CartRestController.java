package app.controller;

import app.dto.CartDto;
import app.service.api.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartRestController {

    @Autowired
    private CartService cartService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/user/cart")
    public CartDto getCartOfUser(@RequestParam String username) {
        return cartService.getCartOfCustomer(username);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/user/cart/add_food")
    public void addFoodToCart(@RequestParam String username, @RequestParam String foodName, @RequestParam Integer quantity) {
        cartService.addFoodToCart(username, foodName, quantity);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/user/cart/remove_food")
    public void removeFoodFromCart(@RequestParam String username, @RequestParam String foodName) {
        cartService.removeFoodFromCart(username, foodName);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/user/cart/reset")
    public void resetCart(@RequestParam String username) {
        cartService.resetCart(username);
    }
}
