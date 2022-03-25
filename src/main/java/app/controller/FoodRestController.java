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

    @GetMapping("/food/all")
    public List<FoodDto> getAllFoods() {
        return foodService.getAllFoods();
    }

    @GetMapping("/food/id/{id}")
    public FoodDto getRestaurantById(@PathVariable Integer id) throws EntityNotFoundException {
        return foodService.getFoodById(id);
    }

    @GetMapping("/food/name")
    public FoodDto getRestaurantByName(@RequestParam String name) throws EntityNotFoundException {
        return foodService.getFoodByName(name);
    }

    @GetMapping("/food/category")
    public List<FoodDto> getFoodByCategory(@RequestParam String category) throws EntityNotFoundException {
        return foodService.getFoodsByCategory(category);
    }

    @GetMapping("/food/restaurant")
    public List<FoodDto> getFoodByRestaurant(@RequestParam String restaurant) throws EntityNotFoundException {
        return foodService.getFoodsByRestaurant(restaurant);
    }

    @GetMapping("/food/restaurant/category")
    public List<FoodDto> getFoodByRestaurant(@RequestParam String restaurant, @RequestParam String category) throws EntityNotFoundException {
        return foodService.getFoodsByRestaurantAndCategory(restaurant, category);
    }

    @PostMapping(value = "/food/new")
    public void addFood(@RequestBody FoodDto foodDto) throws InvalidDataException, DuplicateDataException {
        foodService.addFood(foodDto);
    }
}
