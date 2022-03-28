package app.service.impl;

import app.dto.FoodDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.mapper.FoodMapper;
import app.model.Category;
import app.model.Food;
import app.model.Restaurant;
import app.repository.FoodRepository;
import app.repository.RestaurantRepository;
import app.repository.CategoryRepository;
import app.service.api.FoodService;
import app.service.validator.FoodDataValidator;
import app.service.validator.DataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {
    private static final String DUPLICATE_NAME_ERROR_MESSAGE = "Duplicate food name!\nThis name is already taken!";

    @Autowired
    private FoodRepository foodRepository;

    private final FoodMapper foodMapper = new FoodMapper();
    private final DataValidator<FoodDto> foodDataValidator = new FoodDataValidator();

    @Override
    public List<FoodDto> getAllFoodsByRestaurant(String restaurant) {
        return foodRepository.findAllByRestaurant(restaurant)
                .stream()
                .map(foodMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FoodDto> getFoodsByRestaurantAndCategory(String restaurant, String category) {
        return foodRepository.findByRestaurantAndCategory(restaurant, category)
                .stream()
                .map(foodMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FoodDto getFoodByNameAndRestaurant(String name, String restaurant) throws EntityNotFoundException {
        return foodRepository.findByNameAndRestaurant(name, restaurant)
                .map(foodMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void addFood(FoodDto foodDto) throws InvalidDataException, DuplicateDataException {
        foodDataValidator.validate(foodDto);

        if (foodRepository.findByNameAndRestaurant(foodDto.getName(), foodDto.getRestaurant()).isPresent()) {
            throw new DuplicateDataException(DUPLICATE_NAME_ERROR_MESSAGE);
        }

        Food food = foodMapper.toEntity(foodDto);
        food.getRestaurant().addFood(food);
        food.getCategory().addFood(food);

        foodRepository.save(food);
    }
}
