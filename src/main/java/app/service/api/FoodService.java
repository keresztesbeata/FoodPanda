package app.service.api;

import app.dto.FoodDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;

import java.util.List;

public interface FoodService {
    List<FoodDto> getAllFoods();
    List<FoodDto> getFoodsByCategory(String category);
    List<FoodDto> getFoodsByRestaurant(String restaurant);
    List<FoodDto> getFoodsByRestaurantAndCategory(String restaurant, String category);
    FoodDto getFoodById(Integer id) throws EntityNotFoundException;
    FoodDto getFoodByName(String name) throws EntityNotFoundException;
    void addFood(FoodDto foodDto) throws InvalidDataException, DuplicateDataException;
}
