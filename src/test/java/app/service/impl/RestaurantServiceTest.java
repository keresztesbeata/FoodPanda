package app.service.impl;

import app.dto.RestaurantDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidOperationException;
import app.mapper.RestaurantMapper;
import app.model.DeliveryZone;
import app.model.Restaurant;
import app.model.User;
import app.repository.DeliveryZoneRepository;
import app.repository.RestaurantRepository;
import app.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static app.service.impl.TestUtils.*;
import static app.service.impl.TestComponentFactory.*;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantServiceTest {
    @Spy
    private RestaurantRepository restaurantRepository;
    @Spy
    private UserRepository userRepository;
    @Spy
    private DeliveryZoneRepository deliveryZoneRepository;
    @Spy
    private RestaurantMapper restaurantMapper;
    @InjectMocks
    private RestaurantServiceImpl restaurantService;


    @Test
    public void testGetAllRestaurants() {
        List<Restaurant> allRestaurants = createRandomRestaurantList(10);
        Mockito.when(restaurantRepository.findAll())
                .thenReturn(allRestaurants);

        Assertions.assertTrue(compareRestaurantLists(
                restaurantRepository.findAll(),
                restaurantService.getAllRestaurants()));
    }

    @Test
    public void testGetRestaurantOfAdmin() {
        Restaurant restaurant = createRandomRestaurant();
        User admin = restaurant.getAdmin();
        Mockito.when(restaurantRepository.findByAdmin(admin.getUsername()))
                .thenReturn(Optional.of(restaurant));

        Assertions.assertEquals(restaurantMapper.toDto(restaurant), restaurantService.getRestaurantOfAdmin(admin));
    }

    @Test
    public void testGetRestaurantByName() {
        Restaurant restaurant = createRandomRestaurant();
        String name = restaurant.getName();
        Mockito.when(restaurantRepository.findByName(name))
                .thenReturn(Optional.of(restaurant));

        Assertions.assertEquals(restaurantMapper.toDto(restaurant), restaurantService.getRestaurantByName(name));
    }

    @Test
    public void testGetRestaurantsByDeliveryZone() {
        Restaurant restaurant = createRandomRestaurant();
        DeliveryZone deliveryZone = restaurant.getDeliveryZones().stream().findFirst().orElseThrow(EntityNotFoundException::new);
        List<Restaurant> restaurants = createRandomRestaurantList(5);
        restaurants.forEach(restaurant1 -> restaurant1.getDeliveryZones().add(deliveryZone));

        Mockito.when(restaurantRepository.findByDeliveryZone(deliveryZone.getName()))
                .thenReturn(restaurants);

        Assertions.assertTrue(compareRestaurantLists(
                restaurantRepository.findByDeliveryZone(deliveryZone.getName()),
                restaurantService.getRestaurantsByDeliveryZone(deliveryZone.getName())
        ));
    }

    @Test
    public void testAddRestaurantSuccess() {
        Restaurant restaurant = createRandomRestaurant();
        restaurantMapper.setUserRepository(userRepository);
        restaurantMapper.setDeliveryZoneRepository(deliveryZoneRepository);

        Mockito.when(restaurantRepository.findByName(restaurant.getName()))
                .thenReturn(Optional.empty());
        Mockito.when(userRepository.findByUsername(restaurant.getAdmin().getUsername()))
                .thenReturn(Optional.ofNullable(restaurant.getAdmin()));
        restaurant.getDeliveryZones().forEach(
                deliveryZone -> Mockito.when(deliveryZoneRepository.findByName(deliveryZone.getName()))
                        .thenReturn(Optional.of(deliveryZone)));

        Assertions.assertDoesNotThrow(() -> restaurantService.addRestaurant(restaurantMapper.toDto(restaurant)));
    }

    @Test
    public void testAddRestaurantFailure() {
        Restaurant restaurant = createRandomRestaurant();
        restaurantMapper.setUserRepository(userRepository);
        restaurantMapper.setDeliveryZoneRepository(deliveryZoneRepository);

        Mockito.when(restaurantRepository.findByName(restaurant.getName()))
                .thenReturn(Optional.of(restaurant));
        Mockito.when(restaurantRepository.findByAdmin(restaurant.getAdmin().getUsername()))
                .thenReturn(Optional.empty());
        restaurant.getDeliveryZones().forEach(
                deliveryZone -> Mockito.when(deliveryZoneRepository.findByName(deliveryZone.getName()))
                        .thenReturn(Optional.of(deliveryZone)));

        Assertions.assertThrows(DuplicateDataException.class, () -> restaurantService.addRestaurant(restaurantMapper.toDto(restaurant)));

        Mockito.when(restaurantRepository.findByName(restaurant.getName()))
                .thenReturn(Optional.empty());
        Mockito.when(restaurantRepository.findByAdmin(restaurant.getAdmin().getUsername()))
                .thenReturn(Optional.of(createRandomRestaurant()));

        Assertions.assertThrows(InvalidOperationException.class, () -> restaurantService.addRestaurant(restaurantMapper.toDto(restaurant)));
    }

    private List<Restaurant> createRandomRestaurantList(int size) {
        List<Restaurant> restaurants = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            restaurants.add(createRandomRestaurant());
        }
        return restaurants;
    }

    private boolean compareRestaurantLists(List<Restaurant> restaurants, List<RestaurantDto> restaurantDtos) {
        return restaurants
                .stream()
                .map(restaurant -> (new RestaurantMapper()).toDto(restaurant).getName())
                .collect(Collectors.toSet())
                .equals(restaurantDtos
                        .stream()
                        .map(RestaurantDto::getName)
                        .collect(Collectors.toSet()));
    }
}
