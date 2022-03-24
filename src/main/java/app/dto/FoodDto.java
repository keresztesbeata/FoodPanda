package app.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodDto {
    @NotNull
    private String name;

    @NotNull
    private String category;

    private Double price;

    @NotNull
    private String restaurant;
}
