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
import app.service.api.CategoryRepository;
import app.service.api.FoodService;
import app.service.validator.FoodValidator;
import app.service.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {
    private static final String DUPLICATE_NAME_ERROR_MESSAGE = "Duplicate food name!\nThis name is already taken!";
    private static final String INVALID_RESTAURANT_ERROR_MESSAGE = "Invalid restaurant!\nNo such restaurant exists!";
    private static final String INVALID_CATEGORY_ERROR_MESSAGE = "Invalid category!\nNo such category exists!";

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private final FoodMapper foodMapper = new FoodMapper();
    private final Validator<FoodDto> foodValidator = new FoodValidator();

    @Override
    public List<FoodDto> getAllFoods() {
        return foodRepository.findAll()
                .stream()
                .map(foodMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FoodDto> getFoodsByCategory(String category) {
        return foodRepository.findByCategory(category)
                .stream()
                .map(foodMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FoodDto> getFoodsByRestaurant(String restaurant) {
        return foodRepository.findByRestaurant(restaurant)
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
    public FoodDto getFoodById(Integer id) throws EntityNotFoundException {
        return foodRepository.findById(id)
                .map(foodMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public FoodDto getFoodByName(String name) throws EntityNotFoundException {
        return foodRepository.findByName(name)
                .map(foodMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void addFood(FoodDto foodDto) throws InvalidDataException, DuplicateDataException {
        foodValidator.validate(foodDto);

        if (foodRepository.findByName(foodDto.getName()).isPresent()) {
            throw new DuplicateDataException(DUPLICATE_NAME_ERROR_MESSAGE);
        }

        Food food = foodMapper.toEntity(foodDto);
        Restaurant restaurant = restaurantRepository.findByName(foodDto.getRestaurant())
                .orElseThrow(() -> new InvalidDataException(INVALID_RESTAURANT_ERROR_MESSAGE));
        food.setRestaurant(restaurant);
        restaurant.addFood(food);

        Category category = categoryRepository.getCategoryByName(foodDto.getCategory())
                .orElseThrow(() -> new InvalidDataException(INVALID_CATEGORY_ERROR_MESSAGE));
        food.setCategory(category);
        category.addFood(food);

        foodRepository.save(food);
    }
}
