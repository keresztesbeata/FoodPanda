package app.service.api;

import app.dto.FoodDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;

import java.util.List;
import java.util.Optional;

public interface FoodService {

    /**
     * Get food by its name.
     *
     * @param food the name of the food to be searched
     * @return the existing food with the given name
     * @throws EntityNotFoundException if no food was found with the given name
     */
    FoodDto getFoodByName(String food) throws EntityNotFoundException;

    /**
     * Get all the foods from the menu of a restaurant.
     *
     * @param restaurant the name of the restaurant whose menu is to be retrieved
     * @return the list of foods belonging to the given restaurant
     */
    List<FoodDto> getAllFoodsByRestaurant(String restaurant);

    /**
     * Get all the foods of a restaurant belonging to the given category.
     *
     * @param restaurant the name of the restaurant whose foods are to be retrieved
     * @param category the name of the category to which the foods should belong
     * @return the list of foods belonging to the given restaurant, filtered by the given category
     */
    List<FoodDto> getFoodsByRestaurantAndCategory(String restaurant, String category);

    /**
     * Get food by its name and the name of the restaurant to which it belongs.
     *
     * @param food the name of the food to be retrieved
     * @param restaurant the name of the restaurant which contains the food in its menu
     * @return the existing food with the given name and belonging to the given restaurant
     * @throws EntityNotFoundException if no food was found with the given name and belonging to the given restaurant
     */
    FoodDto getFoodByNameAndRestaurant(String food, String restaurant) throws EntityNotFoundException;

    /**
     * Add a new food to a restaurant's menu.
     *
     * @param foodDto the data related to the food to be added
     * @throws InvalidDataException if the data related to the new food is invalid, ex. contains missing/null values
     * @throws DuplicateDataException if the name of the new food is already taken
     */
    void addFood(FoodDto foodDto) throws InvalidDataException, DuplicateDataException;

    /**
     * Get all the available food categories.
     *
     * @return a list of all food categories
     */
    List<String> getAllFoodCategories();
}
