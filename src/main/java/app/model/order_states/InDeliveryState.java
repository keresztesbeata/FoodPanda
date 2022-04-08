package app.model.order_states;

import app.model.OrderStatus;
import app.model.RestaurantOrder;

public class InDeliveryState extends AbstractOrderState {
    public InDeliveryState(RestaurantOrder order) {
        super(order);
    }

    @Override
    public AbstractOrderState setDelivered() throws IllegalStateException {
        updateStatus(OrderStatus.DELIVERED);
        return new DeliveredState(getOrder());
    }
}
