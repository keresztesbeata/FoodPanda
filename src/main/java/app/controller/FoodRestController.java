package app.controller;

import app.dto.FoodDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.service.api.FoodService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
public class FoodRestController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/food/categories")
    public ResponseEntity getAllFoodCategories() {
        return ResponseEntity.ok().body(foodService.getAllFoodCategories());
    }

    @GetMapping("/restaurant/menu")
    public ResponseEntity getMenuOfRestaurant(@RequestParam String restaurant) {
        try {
            return ResponseEntity.ok().body(foodService.getAllFoodsByRestaurant(restaurant));
        }catch(EntityNotFoundException e) {
            log.error("FoodRestController: getMenuOfRestaurant {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/restaurant/menu/category")
    public ResponseEntity getFoodByRestaurantAndCategory(@RequestParam String restaurant, @RequestParam String category) {
        try {
            return ResponseEntity.ok().body(foodService.getFoodsByRestaurantAndCategory(restaurant, category));
        }catch(EntityNotFoundException e) {
            log.error("FoodRestController: getFoodByRestaurantAndCategory {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/food")
    public ResponseEntity getFoodByName(@RequestParam String food) {
        try{
            return ResponseEntity.ok().body(foodService.getFoodByName(food));
        }catch(EntityNotFoundException e) {
            log.error("FoodRestController: getFoodByName {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/restaurant/food")
    public ResponseEntity getFoodByNameAndRestaurant(@RequestParam String food, @RequestParam String restaurant) {
        try {
            return ResponseEntity.ok().body(foodService.getFoodByNameAndRestaurant(food, restaurant));
        }catch(EntityNotFoundException e) {
            log.error("FoodRestController: getFoodByNameAndRestaurant {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PostMapping(value = "/admin/restaurant/food/new")
    public ResponseEntity addFood(@RequestBody FoodDto food) {
        try {
            foodService.addFood(food);
            return ResponseEntity.ok().build();
        }catch(InvalidDataException | DuplicateDataException e) {
            log.error("FoodRestController: addFood {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
        }
    }
}
