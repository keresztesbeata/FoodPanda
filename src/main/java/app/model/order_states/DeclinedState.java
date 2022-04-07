package app.model.order_states;

import app.model.OrderStatus;
import app.model.RestaurantOrder;

import java.util.Arrays;
import java.util.List;

public class DeclinedState extends AbstractOrderState {

    @Override
    public List<OrderStatus> getNextStates() {
        return Arrays.asList(OrderStatus.DECLINED);
    }

    public DeclinedState(RestaurantOrder order) {
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

    }

    @Override
    public void decline() {

    }
}
