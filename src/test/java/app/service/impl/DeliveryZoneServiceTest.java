package app.service.impl;

import app.model.DeliveryZone;
import app.model.Restaurant;
import app.repository.DeliveryZoneRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static app.service.impl.TestComponentFactory.createRandomDeliveryZones;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryZoneServiceTest {
    @Spy
    private DeliveryZoneRepository deliveryZoneRepository;
    @InjectMocks
    private DeliveryZoneServiceImpl deliveryZoneService;

    @Test
    public void testGetDeliveryZonesOfRestaurant() {
        Restaurant restaurant = new Restaurant();
        int nrDeliveryZones = 10;
        List<DeliveryZone> deliveryZones = createRandomDeliveryZones(nrDeliveryZones, restaurant).stream().toList();

        Mockito.when(deliveryZoneRepository.findByRestaurant(restaurant.getName()))
                .thenReturn(deliveryZones);

        Assertions.assertEquals(nrDeliveryZones, deliveryZoneService.getDeliveryZonesOfRestaurant(restaurant.getName()).size());
    }
}
