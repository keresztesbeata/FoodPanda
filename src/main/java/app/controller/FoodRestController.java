package app.controller;

import app.dto.FoodDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.service.api.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FoodRestController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/category")
    public ResponseEntity getAllFoodCategories() {
        return ResponseEntity.ok().body(foodService.getAllFoodCategories());
    }

    @GetMapping("/restaurant/menu")
    public ResponseEntity getMenuOfRestaurant(@RequestParam String restaurant) {
        try {
            return ResponseEntity.ok().body(foodService.getAllFoodsByRestaurant(restaurant));
        }catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/restaurant/menu/category")
    public ResponseEntity getFoodByRestaurantAndCategory(@RequestParam String restaurant, @RequestParam String category) {
        try {
            return ResponseEntity.ok().body(foodService.getFoodsByRestaurantAndCategory(restaurant, category));
        }catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/restaurant/food")
    public ResponseEntity getFoodByNameAndRestaurant(@RequestParam String food, @RequestParam String restaurant) {
        try {
            return ResponseEntity.ok().body(foodService.getFoodByNameAndRestaurant(food, restaurant));
        }catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PostMapping(value = "/restaurant/food/admin/new")
    public ResponseEntity addFood(@RequestBody FoodDto foodDto) {
        try {
            foodService.addFood(foodDto);
            return ResponseEntity.ok().build();
        }catch(InvalidDataException | DuplicateDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
        }
    }
}
