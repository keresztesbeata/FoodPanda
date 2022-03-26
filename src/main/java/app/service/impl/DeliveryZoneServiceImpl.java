package app.service.impl;

import app.dto.DeliveryZoneDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.InvalidDataException;
import app.mapper.DeliveryZoneMapper;
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

    private final DeliveryZoneMapper deliveryZoneMapper = new DeliveryZoneMapper();

    @Override
    public List<DeliveryZoneDto> getAllDeliveryZones() {
        return deliveryZoneRepository.findAll()
                .stream()
                .map(deliveryZoneMapper::toDto)
                .collect(Collectors.toList());
    }
}
