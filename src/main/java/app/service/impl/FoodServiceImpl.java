package app.service.impl;

import app.dto.FoodDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.mapper.FoodMapper;
import app.model.Food;
import app.repository.FoodRepository;
import app.repository.RestaurantRepository;
import app.service.api.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private final FoodMapper foodMapper = new FoodMapper();

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
        // todo: add validator for food data

        Food food = foodMapper.toEntity(foodDto);
        food.setRestaurant(restaurantRepository.findByName(foodDto.getRestaurant()).orElseThrow(InvalidDataException::new));

        foodRepository.save(food);
    }
}
