package app.mapper;

import app.dto.PlacedOrderDto;
import app.model.OrderStatus;
import app.model.PlacedOrder;

public class PlacedOrderMapper implements Mapper<PlacedOrder, PlacedOrderDto> {

    @Override
    public PlacedOrderDto toDto(PlacedOrder placedOrder) {
        CartMapper cartMapper = new CartMapper();
        return new PlacedOrderDto.PlacedOrderDtoBuilder(
                placedOrder.getRestaurant().getName(),
                placedOrder.getUser().getUsername(),
                cartMapper.toDto(placedOrder.getCart()))
                .withCutlery(placedOrder.getWithCutlery())
                .withRemark(placedOrder.getRemark())
                .withOrderNumber(placedOrder.getId())
                .withOrderStatus(placedOrder.getOrderStatus().name())
                .withOrderDate(placedOrder.getOrderDate())
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
