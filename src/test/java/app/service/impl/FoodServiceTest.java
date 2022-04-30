package app.service.impl;

import app.dto.FoodDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.InvalidDataException;
import app.mapper.FoodMapper;
import app.model.Category;
import app.model.Food;
import app.model.Restaurant;
import app.repository.CategoryRepository;
import app.repository.FoodRepository;
import app.repository.RestaurantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static app.service.impl.TestComponentFactory.*;

@RunWith(MockitoJUnitRunner.class)
public class FoodServiceTest {

    @Spy
    private FoodRepository foodRepository;
    @Spy
    private RestaurantRepository restaurantRepository;
    @Spy
    private CategoryRepository categoryRepository;
    @Spy
    private FoodMapper foodMapper;
    @InjectMocks
    private FoodServiceImpl foodService;

    @Before
    public void init() {
        foodMapper.setCategoryRepository(categoryRepository);
        foodMapper.setRestaurantRepository(restaurantRepository);
    }

    @Test
    public void testGetFoodByName() {
        Restaurant restaurant = createRandomRestaurant();
        Food food = createRandomFood(restaurant);
        String foodName = food.getName();

        Mockito.when(foodRepository.findByName(foodName))
                .thenReturn(Optional.of(food));

        Assertions.assertEquals(foodName, foodService.getFoodByName(foodName).getName());
    }

    @Test
    public void testGetAllFoodsByRestaurant() {
        Restaurant restaurant = createRandomRestaurant();
        int nrFoods = 10;
        List<Food> menu = createRandomFoodsList(restaurant, nrFoods);

        Mockito.when(foodRepository.findAllByRestaurant(restaurant.getName()))
                .thenReturn(menu);

        Assertions.assertEquals(nrFoods, foodService.getAllFoodsByRestaurant(restaurant.getName()).size());
    }

    @Test
    public void testGetFoodsByRestaurantAndCategory() {
        Restaurant restaurant = createRandomRestaurant();
        int nrFoods = 10;
        List<Food> menu = createRandomFoodsList(restaurant, nrFoods);
        Category category = menu.get(0).getCategory();
        long nrFoodsInCategory = countFoodsOfCategory(menu, category);

        Mockito.when(foodRepository.findByRestaurantAndCategory(restaurant.getName(), category.getName()))
                .thenReturn(menu.stream()
                        .filter(food -> food.getCategory().equals(category))
                        .collect(Collectors.toList()));

        Assertions.assertEquals(nrFoodsInCategory, foodService.getFoodsByRestaurantAndCategory(restaurant.getName(), category.getName()).size());
    }

    @Test
    public void testGetFoodsByNameAndRestaurant() {
        Restaurant restaurant = createRandomRestaurant();
        Food food = createRandomFood(restaurant);

        Mockito.when(foodRepository.findByNameAndRestaurant(food.getName(), restaurant.getName()))
                .thenReturn(Optional.of(food));

        Assertions.assertEquals(food.getName(), foodService.getFoodByNameAndRestaurant(food.getName(), restaurant.getName()).getName());
    }

    @Test
    public void testAddFood() {
        Restaurant restaurant = createRandomRestaurant();
        Food food = createRandomFood(restaurant);
        FoodDto foodDto = foodMapper.toDto(food);

        Mockito.when(restaurantRepository.findByName(restaurant.getName()))
                .thenReturn(Optional.of(restaurant));
        Mockito.when(categoryRepository.findByName(food.getCategory().getName()))
                .thenReturn(Optional.ofNullable(food.getCategory()));

        Assertions.assertDoesNotThrow(() -> foodService.addFood(foodDto));

        Mockito.when(foodRepository.findByNameAndRestaurant(food.getName(), restaurant.getName()))
                .thenReturn(Optional.of(food));
        Assertions.assertThrows(DuplicateDataException.class, () -> foodService.addFood(foodDto));

        foodDto.setName(null);
        Assertions.assertThrows(InvalidDataException.class, () -> foodService.addFood(foodDto));

        foodDto.setName(food.getName());
        foodDto.setRestaurant(null);
        Assertions.assertThrows(InvalidDataException.class, () -> foodService.addFood(foodDto));

        foodDto.setRestaurant(food.getRestaurant().getName());
        foodDto.setCategory(null);
        Assertions.assertThrows(InvalidDataException.class, () -> foodService.addFood(foodDto));

        foodDto.setCategory(food.getCategory().getName());
        foodDto.setPrice(-10.0);
        Assertions.assertThrows(InvalidDataException.class, () -> foodService.addFood(foodDto));

        foodDto.setPrice(food.getPrice());
        foodDto.setPortion(-10);
        Assertions.assertThrows(InvalidDataException.class, () -> foodService.addFood(foodDto));
    }

    @Test
    public void testGetAllFoodCategories() {
        int nrCategories = 10;
        List<Category> categories = createRandomCategoriesList(nrCategories);

        Mockito.when(categoryRepository.findAll())
                .thenReturn(categories);

        Assertions.assertEquals(nrCategories, foodService.getAllFoodCategories().size());
    }

    private long countFoodsOfCategory(List<Food> menu, Category category) {
        return menu.stream()
                .filter(food -> food.getCategory().getName().equals(category.getName()))
                .count();
    }
}
