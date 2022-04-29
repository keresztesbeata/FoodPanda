package app.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private String customerName;
    private Map<String, Integer> foods;
    private Double totalPrice;
}
