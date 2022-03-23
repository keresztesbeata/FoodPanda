package app.repository;

import app.model.DeliveryZone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<DeliveryZone,Integer> {
}
