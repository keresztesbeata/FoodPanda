package app.mapper;

import app.dto.RestaurantDto;
import app.exceptions.InvalidDataException;
import app.model.DeliveryZone;
import app.model.Restaurant;
import app.repository.DeliveryZoneRepository;
import app.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RestaurantMapper implements Mapper<Restaurant, RestaurantDto> {
    private static final String INVALID_DELIVERY_ZONE_ERROR_MESSAGE = "Invalid delivery zone!\nNo such delivery zone exists!";
    private static final String INVALID_ADMIN_ERROR_MESSAGE = "The admin has no restaurants!";

    @Autowired
    @Setter
    private DeliveryZoneRepository deliveryZoneRepository;
    @Autowired
    @Setter
    private UserRepository userRepository;

    @Override
    public RestaurantDto toDto(Restaurant restaurant) {
        RestaurantDto restaurantDto = new RestaurantDto();

        restaurantDto.setName(restaurant.getName());
        restaurantDto.setAdmin(restaurant.getAdmin().getUsername());
        restaurantDto.setAddress(restaurant.getAddress());
        restaurantDto.setOpeningHour(restaurant.getOpeningHour());
        restaurantDto.setClosingHour(restaurant.getClosingHour());
        restaurantDto.setDeliveryFee(restaurant.getDeliveryFee());
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
        restaurant.setAdmin(userRepository.findByUsername(restaurantDto.getAdmin())
                .orElseThrow(() -> new InvalidDataException(INVALID_ADMIN_ERROR_MESSAGE)));
        restaurant.setAddress(restaurantDto.getAddress());
        restaurant.setDeliveryFee(restaurantDto.getDeliveryFee());
        restaurant.setOpeningHour(restaurantDto.getOpeningHour());
        restaurant.setClosingHour(restaurantDto.getClosingHour());
        restaurant.setDeliveryZones(restaurantDto.getDeliveryZones()
                .stream()
                .map(zoneName -> deliveryZoneRepository.findByName(zoneName)
                        .orElseThrow(() -> new InvalidDataException(INVALID_DELIVERY_ZONE_ERROR_MESSAGE)))
                .collect(Collectors.toSet()));

        return restaurant;
    }
}
