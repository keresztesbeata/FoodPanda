package app.model.order_states;

import app.model.OrderStatus;
import app.model.RestaurantOrder;

import java.util.Arrays;
import java.util.List;

public class AcceptedState extends AbstractOrderState{

    @Override
    public List<OrderStatus> getNextStates() {
        return Arrays.asList(OrderStatus.IN_DELIVERY);
    }

    public AcceptedState(RestaurantOrder order) {
        super(order);
    }

    @Override
    public void setDelivered() {

    }

    @Override
    public void setInDelivery() {
        setOrderStatus(OrderStatus.IN_DELIVERY);
    }

    @Override
    public void accept() {

    }

    @Override
    public void decline() {

    }
}
