package app.service.impl;

import app.dto.UserDto;
import app.mapper.UserMapper;
import app.model.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class TestUtils {

    public static UserDto createRandomUserDto(UserRole userRole) {
        UserDto userDto = new UserDto();
        userDto.setUsername(generateRandomName(30));
        userDto.setPassword(generateRandomName(30));
        userDto.setUserRole(userRole.name());

        return userDto;
    }

    public static User createRandomUser(UserRole userRole) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setId(getRandomInt());
        user.setUsername(generateRandomName(30));
        user.setPassword(passwordEncoder.encode(generateRandomName(30)));
        user.setUserRole(userRole);

        return user;
    }

    public static User convertUserDtoToEntity(UserDto userDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserMapper userMapper = new UserMapper();

        User user = userMapper.toEntity(userDto);
        user.setId(getRandomInt());
        user.setPassword(passwordEncoder.encode(generateRandomName(30)));

        return user;
    }

    public static String generateRandomName(int length) {
        return (new Random(0))
                .ints(length)
                .asLongStream()
                .mapToObj(value -> String.valueOf(Character.charCount((int) (value % 255))))
                .collect(Collectors.joining());
    }

    public static Set<DeliveryZone> createRandomDeliveryZones(int size, Restaurant restaurant) {
        Set<DeliveryZone> deliveryZones = new HashSet<>();
        for (int i = 1; i <= size; i++) {
            DeliveryZone deliveryZone = new DeliveryZone();
            deliveryZone.setId(i);
            deliveryZone.setName(generateRandomName(30));
            deliveryZone.setRestaurants(new HashSet<>());
            deliveryZone.addRestaurant(restaurant);
            deliveryZones.add(deliveryZone);
        }
        return deliveryZones;
    }

    public static int getRandomInt() {
        return (new Random()).nextInt();
    }

    public static int getRandomInt(int range) {
        return (new Random()).nextInt(range);
    }

    public static double getRandomDouble() {
        return (new Random()).nextDouble();
    }

    public static Category createRandomCategory() {
        Category category = new Category();
        category.setId(getRandomInt());
        category.setName(generateRandomName(30));

        return category;
    }

    public static Food createRandomFood(Restaurant restaurant) {
        Food food = new Food();
        food.setId(getRandomInt());
        food.setName(generateRandomName(30));
        food.setPortion(getRandomInt());
        food.setRestaurant(restaurant);
        food.setCategory(createRandomCategory());
        food.setDescription(generateRandomName(100));
        food.setPrice(getRandomDouble());

        return food;
    }

    public static Set<Food> createRandomMenu(int size, Restaurant restaurant) {
        Set<Food> menu = new HashSet<>();
        for (int i = 0; i < size; i++) {
            menu.add(createRandomFood(restaurant));
        }
        return menu;
    }

    public static Restaurant createRandomRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(getRandomInt());
        restaurant.setAdmin(createRandomUser(UserRole.ADMIN));
        restaurant.setName(generateRandomName(30));
        restaurant.setAddress(generateRandomName(100));
        restaurant.setDeliveryZones(createRandomDeliveryZones(4, restaurant));
        restaurant.setDeliveryFee(getRandomDouble());
        restaurant.setOpeningHour(getRandomInt(23));
        restaurant.setClosingHour(getRandomInt(23));

        return restaurant;
    }
}
