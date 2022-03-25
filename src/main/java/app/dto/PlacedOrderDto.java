package app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlacedOrderDto {

    private String orderStatus;

    private Date orderDate;

    private String restaurant;

    private String user;

    private List<String> foods;

    private boolean withCutlery;
}
