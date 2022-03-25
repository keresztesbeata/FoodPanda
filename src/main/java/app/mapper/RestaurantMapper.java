package app.mapper;

import app.dto.RestaurantDto;
import app.model.DeliveryZone;
import app.model.Restaurant;

import java.util.stream.Collectors;

public class RestaurantMapper implements Mapper<Restaurant, RestaurantDto> {

    @Override
    public RestaurantDto toDto(Restaurant restaurant) {

        return new RestaurantDto.RestaurantDtoBuilder(
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getAdmin().getUsername())
                .withOpeningHour(restaurant.getOpeningHour())
                .withClosingHour(restaurant.getClosingHour())
                .withDeliveryFee(restaurant.getDeliveryFee())
                .withDeliveryZones(restaurant.getDeliveryZones()
                        .stream()
                        .map(DeliveryZone::getName)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public Restaurant toEntity(RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant();

        restaurant.setName(restaurantDto.getName());
        restaurant.setAddress(restaurantDto.getAddress());
        restaurant.setDeliveryFee(restaurantDto.getDeliveryFee());
        restaurant.setOpeningHour(restaurantDto.getOpeningHour());
        restaurant.setClosingHour(restaurantDto.getClosingHour());

        return restaurant;
    }
}
