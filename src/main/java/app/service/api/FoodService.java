package app.service.api;

import app.dto.FoodDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;

import java.util.List;
import java.util.Optional;

public interface FoodService {

    Optional<FoodDto> getFoodByName(String food);

    List<FoodDto> getAllFoodsByRestaurant(String restaurant);

    List<FoodDto> getFoodsByRestaurantAndCategory(String restaurant, String category);

    FoodDto getFoodByNameAndRestaurant(String food, String restaurant) throws EntityNotFoundException;

    void addFood(FoodDto foodDto) throws InvalidDataException, DuplicateDataException;

    List<String> getAllFoodCategories();
}
