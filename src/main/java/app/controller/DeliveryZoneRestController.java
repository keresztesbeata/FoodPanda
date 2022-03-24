package app.controller;

import app.dto.DeliveryZoneDto;
import app.service.api.DeliveryZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeliveryZoneRestController {

    @Autowired
    private DeliveryZoneService deliveryZoneService;


    @GetMapping("/delivery_zone/all")
    public List<DeliveryZoneDto> getAllDeliveryZones() {
        return deliveryZoneService.getAllDeliveryZones();
    }
}
