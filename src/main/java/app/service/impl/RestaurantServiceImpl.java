package app.service.impl;

import app.dto.RestaurantDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.exceptions.InvalidOperationException;
import app.mapper.RestaurantMapper;
import app.model.Restaurant;
import app.model.User;
import app.repository.RestaurantRepository;
import app.service.api.RestaurantService;
import app.service.validator.RestaurantDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private static final String DUPLICATE_NAME_ERROR_MESSAGE = "Duplicate restaurant name!\nThis name is already taken!";
    private static final String INVALID_ADMIN_ERROR_MESSAGE = "The admin has no associated restaurant!";
    private static final String INVALID_NAME_ERROR_MESSAGE = "No restaurant with the given name exists!";
    private static final String ALREADY_HAVE_RESTAURANT_ERROR_MESSAGE = "You are already assigned as admin of another restaurant,\ntherefore you cannot manage other restaurants!";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private RestaurantDataValidator restaurantDataValidator;

    @Override
    public List<RestaurantDto> getAllRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(restaurantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantDto getRestaurantOfAdmin(User admin) throws EntityNotFoundException {
        return restaurantRepository.findByAdmin(admin.getUsername())
                .map(restaurantMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(INVALID_ADMIN_ERROR_MESSAGE));
    }

    @Override
    public RestaurantDto getRestaurantByName(String name) throws EntityNotFoundException {
        return restaurantRepository.findByName(name)
                .map(restaurantMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(INVALID_NAME_ERROR_MESSAGE));
    }

    @Override
    @Transactional
    public List<RestaurantDto> getRestaurantsByDeliveryZone(String deliveryZoneName) {
        return restaurantRepository.findByDeliveryZone(deliveryZoneName)
                .stream()
                .map(restaurantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addRestaurant(RestaurantDto restaurantDto) throws InvalidDataException, DuplicateDataException, InvalidOperationException {
        restaurantDataValidator.validate(restaurantDto);

        if (restaurantRepository.findByName(restaurantDto.getName()).isPresent()) {
            throw new DuplicateDataException(DUPLICATE_NAME_ERROR_MESSAGE);
        }

        if (restaurantRepository.findByAdmin(restaurantDto.getAdmin()).isPresent()) {
            throw new InvalidOperationException(ALREADY_HAVE_RESTAURANT_ERROR_MESSAGE);
        }

        Restaurant restaurant = restaurantMapper.toEntity(restaurantDto);
        restaurant.getDeliveryZones().forEach(deliveryZone -> deliveryZone.addRestaurant(restaurant));
        restaurantRepository.save(restaurant);
    }
}
