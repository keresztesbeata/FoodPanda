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

    @GetMapping("/restaurant/menu")
    public ResponseEntity getMenuOfRestaurant(@RequestParam String name) throws EntityNotFoundException {
        return ResponseEntity.ok().body(foodService.getAllFoodsByRestaurant(name));
    }

    @GetMapping("/restaurant/menu/category")
    public List<FoodDto> getFoodByRestaurantAndCategory(@RequestParam String name, @RequestParam String category) throws EntityNotFoundException {
        return foodService.getFoodsByRestaurantAndCategory(name, category);
    }

    @GetMapping("/restaurant/food")
    public FoodDto getFoodByName(@RequestParam String name) throws EntityNotFoundException {
        return foodService.getFoodByNameAndRestaurant(name);
    }

    @PostMapping(value = "/restaurant/food/admin/new")
    public void addFood(@RequestBody FoodDto foodDto) throws InvalidDataException, DuplicateDataException {
        foodService.addFood(foodDto);
    }
}
