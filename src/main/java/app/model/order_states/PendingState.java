package app.model.order_states;

import app.model.OrderStatus;
import app.model.RestaurantOrder;

import java.util.Arrays;
import java.util.List;

public class PendingState extends AbstractOrderState {

    @Override
    public List<OrderStatus> getNextStates() {
        return Arrays.asList(OrderStatus.ACCEPTED, OrderStatus.DECLINED);
    }

    public PendingState(RestaurantOrder order) {
        super(order);
    }

    @Override
    public void setDelivered() {

    }

    @Override
    public void setInDelivery() {

    }

    @Override
    public void accept() {
        setOrderStatus(OrderStatus.ACCEPTED);
    }

    @Override
    public void decline() {
        setOrderStatus(OrderStatus.DECLINED);
    }
}
