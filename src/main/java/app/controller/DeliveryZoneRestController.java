package app.controller;

import app.service.api.DeliveryZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryZoneRestController {

    @Autowired
    private DeliveryZoneService deliveryZoneService;

    @GetMapping("/delivery_zone/all")
    public ResponseEntity getAllDeliveryZones() {
        return ResponseEntity.ok().body(deliveryZoneService.getAllDeliveryZones());
    }

    @GetMapping("/delivery_zone/restaurant/all")
    public ResponseEntity getDeliveryZonesOfRestaurant(@RequestParam String restaurant) {
        return ResponseEntity.ok().body(deliveryZoneService.getDeliveryZonesOfRestaurant(restaurant));
    }
}
