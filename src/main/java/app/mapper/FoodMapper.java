package app.mapper;

import app.dto.FoodDto;
import app.exceptions.EntityNotFoundException;
import app.model.Food;
import app.repository.FoodRepository;
import app.repository.RestaurantRepository;
import app.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper implements Mapper<Food,FoodDto> {

    private static final String CATEGORY_NOT_FOUND_ERROR_MESSAGE = "No category of the given name exists!";
    private static final String RESTAURANT_NOT_FOUND_ERROR_MESSAGE = "No restaurant of the given name exists!";

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Food toEntity(FoodDto foodDto) {
        Food food = new Food();

        food.setName(foodDto.getName());
        food.setPrice(foodDto.getPrice());
        food.setCategory(categoryRepository.findByName(foodDto.getName())
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND_ERROR_MESSAGE)));
        food.setRestaurant(restaurantRepository.findByName(foodDto.getRestaurant())
                .orElseThrow(() -> new EntityNotFoundException(RESTAURANT_NOT_FOUND_ERROR_MESSAGE)));
        food.setDescription(foodDto.getDescription());

        return food;
    }

    public FoodDto toDto(Food food) {
        FoodDto foodDto = new FoodDto();

        foodDto.setName(food.getName());
        foodDto.setPrice(food.getPrice());
        foodDto.setCategory(food.getCategory().getName());
        foodDto.setRestaurant(food.getRestaurant().getName());
        foodDto.setDescription(food.getDescription());

        return foodDto;
    }
}
