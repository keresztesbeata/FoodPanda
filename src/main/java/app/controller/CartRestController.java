package app.controller;

import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.service.api.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartRestController {

    @Autowired
    private CartService cartService;

    @GetMapping("/customer/cart")
    public ResponseEntity getCartOfUser(@RequestParam String username) {
        try {
            return ResponseEntity.ok().body(cartService.getCartOfCustomer(username));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PostMapping("/customer/cart/add_food")
    public ResponseEntity addFoodToCart(@RequestParam String username, @RequestParam String foodName, @RequestParam Integer quantity) {
        try {
            cartService.addFoodToCart(username, foodName, quantity);
            return ResponseEntity.ok().build();
        } catch (InvalidDataException | EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PostMapping("/customer/cart/remove_food")
    public ResponseEntity removeFoodFromCart(@RequestParam String username, @RequestParam String foodName) {
        try {
            cartService.removeFoodFromCart(username, foodName);
            return ResponseEntity.ok().build();
        } catch (InvalidDataException | EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PostMapping("/customer/cart/reset")
    public ResponseEntity resetCart(@RequestParam String username) {
        try {
            cartService.resetCart(username);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
