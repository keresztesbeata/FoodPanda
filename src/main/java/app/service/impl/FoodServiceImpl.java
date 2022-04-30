package app.service.impl;

import app.dto.FoodDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.mapper.FoodMapper;
import app.model.Category;
import app.model.Food;
import app.repository.CategoryRepository;
import app.repository.FoodRepository;
import app.service.api.FoodService;
import app.service.validator.FoodDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {
    private static final String DUPLICATE_NAME_ERROR_MESSAGE = "Duplicate food name!\nThis name is already taken!";

    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FoodMapper foodMapper;
    private FoodDataValidator foodDataValidator = new FoodDataValidator();

    @Override
    public FoodDto getFoodByName(String food) throws EntityNotFoundException{
        return foodRepository.findByName(food)
                .map(foodMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

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
    public FoodDto getFoodByNameAndRestaurant(String food, String restaurant) throws EntityNotFoundException {
        return foodRepository.findByNameAndRestaurant(food, restaurant)
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

    @Override
    public List<String> getAllFoodCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }
}
