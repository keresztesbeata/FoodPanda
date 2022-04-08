package app.model.order_states;

import app.model.OrderStatus;
import app.model.RestaurantOrder;

public class AcceptedState extends AbstractOrderState{

    public AcceptedState(RestaurantOrder order) {
        super(order);
    }

    @Override
    public AbstractOrderState setInDelivery() throws IllegalStateException {
        updateStatus(OrderStatus.IN_DELIVERY);
        return new InDeliveryState(getOrder());
    }
}
