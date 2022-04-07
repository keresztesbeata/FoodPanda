package app.mapper;

import app.dto.RestaurantOrderDto;
import app.exceptions.EntityNotFoundException;
import app.model.RestaurantOrder;
import app.repository.RestaurantRepository;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RestaurantOrderMapper implements Mapper<RestaurantOrder, RestaurantOrderDto> {

    private static final String INEXISTENT_USER_ERROR_MESSAGE = "The order cannot be created!\nNo user with the given username was found!";
    @Autowired
    private UserRepository userRepository;

    @Override
    public RestaurantOrderDto toDto(RestaurantOrder restaurantOrder) {
        RestaurantOrderDto restaurantOrderDto = new RestaurantOrderDto();

        restaurantOrderDto.setOrderNumber(restaurantOrder.getOrderNumber());
        restaurantOrderDto.setRestaurant(restaurantOrder.getRestaurant().getName());
        restaurantOrderDto.setCustomer(restaurantOrder.getCustomer().getUsername());
        restaurantOrderDto.setDeliveryAddress(restaurantOrder.getDeliveryAddress());
        restaurantOrderDto.setWithCutlery(restaurantOrder.getWithCutlery());
        restaurantOrderDto.setRemark(restaurantOrder.getRemark());
        restaurantOrderDto.setOrderStatus(restaurantOrder.getOrderStatus().name());
        restaurantOrderDto.setDateCreated(restaurantOrder.getDateCreated());
        restaurantOrderDto.setTotalPrice(restaurantOrder.getTotalPrice());
        restaurantOrderDto.setOrderedFoods(restaurantOrder.getOrderedFoods()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey().getName(), Map.Entry::getValue)));

        return restaurantOrderDto;
    }

    @Override
    public RestaurantOrder toEntity(RestaurantOrderDto restaurantOrderDto) {
        RestaurantOrder restaurantOrder = new RestaurantOrder();

        restaurantOrder.setCustomer(userRepository.findByUsername(restaurantOrderDto.getCustomer())
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_USER_ERROR_MESSAGE)));
        restaurantOrder.setDeliveryAddress(restaurantOrderDto.getDeliveryAddress());
        restaurantOrder.setRemark(restaurantOrderDto.getRemark());
        restaurantOrder.setWithCutlery(restaurantOrderDto.getWithCutlery());

        return restaurantOrder;
    }
}
