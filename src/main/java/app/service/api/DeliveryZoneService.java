package app.service.api;

import java.util.List;

public interface DeliveryZoneService {
    /**
     * Get all delivery zones.
     *
     * @return a list of all existing delivery zones
     */
    List<String> getAllDeliveryZones();

    /**
     * Get all delivery zones of a restaurant.
     *
     * @param restaurantName the name of the restaurant whose delivery zones are to be retrieved
     * @return the list of delivery zones belonging to the given restaurant
     */
    List<String> getDeliveryZonesOfRestaurant(String restaurantName);
}
