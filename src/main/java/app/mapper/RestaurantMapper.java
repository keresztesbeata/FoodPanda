package app.mapper;

import app.dto.RestaurantDto;
import app.model.DeliveryZone;
import app.model.Restaurant;

import java.util.stream.Collectors;

public class RestaurantMapper implements Mapper<Restaurant, RestaurantDto> {

    @Override
    public RestaurantDto toDto(Restaurant restaurant) {
        RestaurantDto restaurantDto = new RestaurantDto();

        restaurantDto.setName(restaurant.getName());
        restaurantDto.setAddress(restaurant.getAddress());
        restaurantDto.setDeliveryZones(restaurant.getDeliveryZones()
                .stream()
                .map(DeliveryZone::getName)
                .collect(Collectors.toList()));

        return restaurantDto;
    }

    @Override
    public Restaurant toEntity(RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant();

        restaurant.setName(restaurantDto.getName());
        restaurant.setAddress(restaurantDto.getAddress());

        return restaurant;
    }
}
