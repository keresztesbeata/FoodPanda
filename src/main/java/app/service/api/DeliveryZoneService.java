package app.service.api;

import java.util.List;

public interface DeliveryZoneService {
    List<String> getAllDeliveryZones();

    List<String> getDeliveryZonesOfRestaurant(String restaurantName);
}
