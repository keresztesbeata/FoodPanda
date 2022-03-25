package app.service.impl;

import app.dto.RestaurantDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.mapper.RestaurantMapper;
import app.model.Restaurant;
import app.model.User;
import app.repository.DeliveryZoneRepository;
import app.repository.RestaurantRepository;
import app.repository.UserRepository;
import app.service.api.RestaurantService;
import app.service.validator.RestaurantValidator;
import app.service.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private static final String DUPLICATE_NAME_ERROR_MESSAGE = "Duplicate restaurant name!\nThis name is already taken!";
    private static final String INVALID_DELIVERY_ZONE_ERROR_MESSAGE = "Invalid delivery zone!\nNo such delivery zone exists!";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DeliveryZoneRepository deliveryZoneRepository;

    @Autowired
    private UserRepository userRepository;

    private final RestaurantMapper restaurantMapper = new RestaurantMapper();
    private final Validator<RestaurantDto> restaurantValidator = new RestaurantValidator();

    @Override
    public List<RestaurantDto> getAllRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(restaurantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantDto getRestaurantById(Integer id) throws EntityNotFoundException {
        return restaurantRepository.findById(id)
                .map(restaurantMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public RestaurantDto getRestaurantByName(String name) throws EntityNotFoundException {
        return restaurantRepository.findByName(name)
                .map(restaurantMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
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
    public void addRestaurant(RestaurantDto restaurantDto) throws InvalidDataException, DuplicateDataException {
        restaurantValidator.validate(restaurantDto);

        if (restaurantRepository.findByName(restaurantDto.getName()).isPresent()) {
            throw new DuplicateDataException(DUPLICATE_NAME_ERROR_MESSAGE);
        }

        Restaurant restaurant = restaurantMapper.toEntity(restaurantDto);
        restaurant.setDeliveryZones(restaurantDto.getDeliveryZones()
                .stream()
                .map(zone -> deliveryZoneRepository.findByName(zone)
                        .orElseThrow(() -> new InvalidDataException(INVALID_DELIVERY_ZONE_ERROR_MESSAGE)))
                .collect(Collectors.toList()));
        User admin = userRepository.findByUsername(restaurantDto.getAdmin()).orElseThrow(InvalidDataException::new);
        restaurant.setAdmin(admin);

        restaurantRepository.save(restaurant);
    }
}
