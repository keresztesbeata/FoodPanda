package app.controller;

import app.dto.FoodDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.service.api.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoodRestController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/restaurant/food")
    public List<FoodDto> getMenuOfRestaurant(@RequestParam String restaurant) throws EntityNotFoundException {
        return foodService.getAllFoodsByRestaurant(restaurant);
    }

    @GetMapping("/restaurant/food/category")
    public List<FoodDto> getFoodByRestaurantAndCategory(@RequestParam String restaurant, @RequestParam String category) throws EntityNotFoundException {
        return foodService.getFoodsByRestaurantAndCategory(restaurant, category);
    }

    @GetMapping("/restaurant/food/name")
    public FoodDto getFoodByName(@RequestParam String name) throws EntityNotFoundException {
        return foodService.getFoodByNameAndRestaurant(name);
    }

    @PostMapping(value = "/restaurant/food/admin/new")
    public void addFood(@RequestBody FoodDto foodDto) throws InvalidDataException, DuplicateDataException {
        foodService.addFood(foodDto);
    }
}
