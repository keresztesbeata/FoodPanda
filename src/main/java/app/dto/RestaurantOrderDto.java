package app.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
@Setter
@Getter
public class RestaurantOrderDto {
    private String orderNumber;
    private String restaurant;
    private String customer;
    private String orderStatus;
    private LocalDate dateCreated;
    private Map<String, Integer> orderedFoods;
    private String deliveryAddress;
    private Boolean withCutlery;
    private String remark;
}
