package app.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {
    @NotNull
    private String name;

    @NotNull
    private String address;

    private List<String> deliveryZones;
}
