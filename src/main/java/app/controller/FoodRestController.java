package app.controller;

import app.dto.BasicFoodDto;
import app.dto.FoodDto;
import app.dto.FoodDtoFactory;
import app.dto.HealthyFoodRequestBody;
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

    @Autowired
    private FoodDtoFactory foodDtoFactory;

    @GetMapping("/food/categories")
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

    @GetMapping("/food")
    public ResponseEntity getFoodByName(@RequestParam String food) {
        return ResponseEntity.of(foodService.getFoodByName(food));
    }

    @GetMapping("/restaurant/food")
    public ResponseEntity getFoodByNameAndRestaurant(@RequestParam String food, @RequestParam String restaurant) {
        try {
            return ResponseEntity.ok().body(foodService.getFoodByNameAndRestaurant(food, restaurant));
        }catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PostMapping(value = "/admin/restaurant/food/new")
    public ResponseEntity addFood(@RequestBody FoodDto food) {
        try {
            //foodService.addFood(foodDtoFactory.createFoodDto(foodDto));
            foodService.addFood(food);
            return ResponseEntity.ok().build();
        }catch(InvalidDataException | DuplicateDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
        }
    }

//    @PostMapping(value = "/restaurant/food/healthy/admin/new")
//    public ResponseEntity addHealthyFood(@RequestBody FoodDto foodDto) {
//        try {
//            foodService.addFood(foodDtoFactory.createHealthyFoodDto(healthyFoodRequestBody.getBasicFoodDto(), healthyFoodRequestBody.getNutritionFacts()));
//            return ResponseEntity.ok().build();
//        }catch(InvalidDataException | DuplicateDataException e) {
//            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
//        }
//    }
}
