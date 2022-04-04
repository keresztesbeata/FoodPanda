package app.controller;

import app.dto.FoodDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.service.api.FoodService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoodRestController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/category")
    public ResponseEntity getAllFoodCategories() {
        return ResponseEntity.ok().body(foodService.getAllFoodCategories());
    }

    @GetMapping("/restaurant/menu")
    public ResponseEntity getMenuOfRestaurant(@RequestParam String restaurant) throws EntityNotFoundException {
        return ResponseEntity.ok().body(foodService.getAllFoodsByRestaurant(restaurant));
    }

    @GetMapping("/restaurant/menu/category")
    public List<FoodDto> getFoodByRestaurantAndCategory(@RequestParam String restaurant, @RequestParam String category) throws EntityNotFoundException {
        return foodService.getFoodsByRestaurantAndCategory(restaurant, category);
    }

    @GetMapping("/restaurant/food")
    public FoodDto getFoodByNameAndRestaurant(@RequestParam String food, @RequestParam String restaurant) throws EntityNotFoundException {
        return foodService.getFoodByNameAndRestaurant(food, restaurant);
    }

    @PostMapping(value = "/restaurant/food/admin/new")
    public void addFood(@RequestBody FoodDto foodDto) throws InvalidDataException, DuplicateDataException {
        foodService.addFood(foodDto);
    }
}
