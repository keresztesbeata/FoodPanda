package app.service.impl;

import app.model.DeliveryZone;
import app.repository.DeliveryZoneRepository;
import app.service.api.DeliveryZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryZoneServiceImpl implements DeliveryZoneService {

    @Autowired
    private DeliveryZoneRepository deliveryZoneRepository;

    @Override
    public List<String> getAllDeliveryZones() {
        return deliveryZoneRepository.findAll()
                .stream()
                .map(DeliveryZone::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getDeliveryZonesOfRestaurant(String restaurantName) {
        return deliveryZoneRepository.findByRestaurant(restaurantName)
                .stream()
                .map(DeliveryZone::getName)
                .collect(Collectors.toList());
    }
}
