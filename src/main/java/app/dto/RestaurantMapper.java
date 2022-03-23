package app.dto;

import app.model.DeliveryZone;
import app.model.Restaurant;
import app.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantMapper {
    @Autowired
    private ZoneRepository zoneRepository;

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

    public Restaurant toEntity(RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant();

        restaurant.setName(restaurantDto.getName());
        restaurant.setAddress(restaurantDto.getAddress());
        List<DeliveryZone> availableZones = zoneRepository.findAll();
        restaurant.setDeliveryZones(restaurantDto.getDeliveryZones()
                .stream()
                .flatMap(zone -> availableZones.stream()
                        .filter(availableZone -> availableZone.getName().equals(zone)))
                .collect(Collectors.toList()));

        return restaurant;
    }
}
