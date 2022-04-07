package app.model.order_states;

import app.model.OrderStatus;
import app.model.RestaurantOrder;

import java.util.Arrays;
import java.util.List;

public class InDeliveryState extends AbstractOrderState {

    @Override
    public List<OrderStatus> getNextStates() {
        return Arrays.asList(OrderStatus.DELIVERED);
    }

    public InDeliveryState(RestaurantOrder order) {
        super(order);
    }

    @Override
    public void setDelivered() {
        setOrderStatus(OrderStatus.DELIVERED);
    }

    @Override
    public void setInDelivery() {

    }

    @Override
    public void accept() {

    }

    @Override
    public void decline() {

    }
}
