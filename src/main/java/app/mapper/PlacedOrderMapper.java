package app.mapper;

import app.dto.PlacedOrderDto;
import app.model.OrderStatus;
import app.model.PlacedOrder;

import java.util.Map;
import java.util.stream.Collectors;

public class PlacedOrderMapper implements Mapper<PlacedOrder, PlacedOrderDto> {

    @Override
    public PlacedOrderDto toDto(PlacedOrder placedOrder) {
        Map<String, Integer> orderedFoodsMap = placedOrder.getCart()
                .getFoods()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey().getName(), Map.Entry::getValue));

        return new PlacedOrderDto.PlacedOrderDtoBuilder(
                placedOrder.getRestaurant().getName(),
                placedOrder.getUser().getUsername(),
                orderedFoodsMap)
                .withCutlery(placedOrder.getWithCutlery())
                .withRemark(placedOrder.getRemark())
                .build();
    }

    @Override
    public PlacedOrder toEntity(PlacedOrderDto placedOrderDto) {
        PlacedOrder placedOrder = new PlacedOrder();

        placedOrder.setOrderStatus(OrderStatus.valueOf(placedOrderDto.getOrderStatus()));
        placedOrder.setOrderDate(placedOrderDto.getOrderDate());
        placedOrder.setRemark(placedOrder.getRemark());
        placedOrder.setWithCutlery(placedOrder.getWithCutlery());

        return placedOrder;
    }
}
