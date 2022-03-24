package app.mapper;

import app.dto.RestaurantDto;
import app.model.DeliveryZone;
import app.model.Restaurant;
import app.repository.DeliveryZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

public class RestaurantMapper implements Mapper<Restaurant, RestaurantDto> {
    @Autowired
    private DeliveryZoneRepository deliveryZoneRepository;

    private DeliveryZoneMapper deliveryZoneMapper = new DeliveryZoneMapper();

    @Override
    public RestaurantDto toDto(Restaurant restaurant) {
        RestaurantDto restaurantDto = new RestaurantDto();

        restaurantDto.setName(restaurant.getName());
        restaurantDto.setAddress(restaurant.getAddress());
        restaurantDto.setDeliveryZones(restaurant.getDeliveryZones()
                .stream()
                .map(deliveryZone -> deliveryZoneMapper.toDto(deliveryZone))
                .collect(Collectors.toList()));

        return restaurantDto;
    }

    @Override
    public Restaurant toEntity(RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant();

        restaurant.setName(restaurantDto.getName());
        restaurant.setAddress(restaurantDto.getAddress());
        restaurant.setDeliveryZones(restaurantDto.getDeliveryZones()
                .stream()
                .map(zone -> deliveryZoneRepository.findByName(zone.getName())
                        .orElseGet(DeliveryZone::new))
                .collect(Collectors.toList()));

        return restaurant;
    }
}
