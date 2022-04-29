package app.service.impl;


import app.mapper.RestaurantMapper;
import app.model.Restaurant;
import app.repository.RestaurantRepository;
import app.service.api.RestaurantOrderService;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static app.service.impl.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantOrderTest {

    @Spy
    RestaurantRepository restaurantRepository;
    @InjectMocks
    RestaurantOrderServiceImpl restaurantOrderService;

}
