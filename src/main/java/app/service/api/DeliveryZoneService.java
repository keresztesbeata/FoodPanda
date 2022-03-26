package app.service.api;

import app.dto.DeliveryZoneDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.InvalidDataException;

import java.util.List;

public interface DeliveryZoneService {

    List<DeliveryZoneDto> getAllDeliveryZones();
}
