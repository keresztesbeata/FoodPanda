package app.mapper;

import app.dto.DeliveryZoneDto;
import app.model.DeliveryZone;

public class DeliveryZoneMapper implements Mapper<DeliveryZone, DeliveryZoneDto> {

    @Override
    public DeliveryZoneDto toDto(DeliveryZone deliveryZone) {
        DeliveryZoneDto deliveryZoneDto = new DeliveryZoneDto();
        deliveryZoneDto.setName(deliveryZone.getName());
        return deliveryZoneDto;
    }

    @Override
    public DeliveryZone toEntity(DeliveryZoneDto deliveryZoneDto) {
        DeliveryZone deliveryZone = new DeliveryZone();
        deliveryZone.setName(deliveryZoneDto.getName());
        return deliveryZone;
    }
}
