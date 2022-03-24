package app.service.impl;

import app.dto.DeliveryZoneDto;
import app.dto.RestaurantDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.mapper.RestaurantMapper;
import app.model.DeliveryZone;
import app.model.Restaurant;
import app.repository.DeliveryZoneRepository;
import app.repository.RestaurantRepository;
import app.service.api.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private static final String DUPLICATE_NAME_ERROR_MESSAGE = "Duplicate restaurant name!\n This name is already taken!";
    private static final String INVALID_NAME_ERROR_MESSAGE = "Invalid restaurant name!\n The name cannot be null!";

    private final RestaurantRepository restaurantRepository;
    private final DeliveryZoneRepository deliveryZoneRepository;
    private final RestaurantMapper restaurantMapper;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, DeliveryZoneRepository deliveryZoneRepository) {
        this.restaurantRepository = restaurantRepository;
        this.deliveryZoneRepository = deliveryZoneRepository;
        this.restaurantMapper = new RestaurantMapper();
    }

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
    public List<RestaurantDto> getRestaurantsByDeliveryZone(DeliveryZoneDto deliveryZoneDto) {
        DeliveryZone deliveryZone = deliveryZoneRepository.findByName(deliveryZoneDto.getName())
                .orElseThrow(EntityNotFoundException::new);
        return restaurantRepository.findByDeliveryZone(deliveryZone)
                .stream()
                .map(restaurantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addRestaurant(RestaurantDto restaurantDto) throws InvalidDataException, DuplicateDataException{
        if(restaurantDto.getName() == null || restaurantDto.getName().isEmpty()) {
            throw new InvalidDataException(INVALID_NAME_ERROR_MESSAGE);
        }
        if (restaurantRepository.findByName(restaurantDto.getName()).isPresent()) {
            throw new DuplicateDataException(DUPLICATE_NAME_ERROR_MESSAGE);
        }
        Restaurant restaurant = restaurantMapper.toEntity(restaurantDto);

        restaurantRepository.save(restaurant);
    }
}
