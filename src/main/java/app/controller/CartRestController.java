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

import static app.controller.Utils.getCurrentUser;

@RestController
public class CartRestController {

    @Autowired
    private CartService cartService;

    @GetMapping("/customer/cart")
    public ResponseEntity getCartOfUser() {
        try {
            return ResponseEntity.ok().body(cartService.getCartOfCustomer(getCurrentUser()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PostMapping("/customer/cart/add_food")
    public ResponseEntity addFoodToCart(@RequestParam String foodName, @RequestParam Integer quantity) {
        try {
            cartService.addFoodToCart(getCurrentUser(), foodName, quantity);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PostMapping("/customer/cart/remove_food")
    public ResponseEntity removeFoodFromCart(@RequestParam String foodName) {
        try {
            cartService.removeFoodFromCart(getCurrentUser(), foodName);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PostMapping("/customer/cart/reset")
    public ResponseEntity resetCart() {
        try {
            cartService.resetCart(getCurrentUser());
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
