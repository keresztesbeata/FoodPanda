package app.mapper;

import app.dto.FoodDto;
import app.model.Food;

public class FoodMapper implements Mapper<Food,FoodDto> {

    public Food toEntity(FoodDto foodDto) {
        Food food = new Food();

        food.setName(foodDto.getName());
        food.setPrice(foodDto.getPrice());

        return food;
    }

    public FoodDto toDto(Food food) {
        FoodDto foodDto = new FoodDto();

        foodDto.setName(food.getName());
        foodDto.setPrice(food.getPrice());
        foodDto.setCategory(food.getCategory().getName());
        foodDto.setRestaurant(food.getRestaurant().getName());

        return foodDto;
    }
}
