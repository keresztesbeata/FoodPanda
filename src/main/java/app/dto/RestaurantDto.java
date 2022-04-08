package app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.swing.plaf.basic.BasicButtonUI;
import java.util.List;

@Component
@Setter
@Getter
public class RestaurantDto {
    private String name;
    private String address;
    private String admin;
    private List<String> deliveryZones;
    private Integer openingHour;
    private Integer closingHour;
    private Double deliveryFee;
}
