package app.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantOrderDto {
    private String orderNumber;
    private String restaurant;
    private String customer;
    private String orderStatus;
    private LocalDate dateCreated;
    private Map<String, Integer> orderedFoods = new HashMap<>();
    private String deliveryAddress;
    private Boolean withCutlery = false;
    private String remark;
    private Double totalPrice;
}
