package app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private String customerName;
    private Map<String, Integer> foods;
    private Double totalPrice;
}
