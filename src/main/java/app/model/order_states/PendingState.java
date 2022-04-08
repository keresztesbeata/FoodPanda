package app.model.order_states;

import app.model.OrderStatus;
import app.model.RestaurantOrder;

public class PendingState extends AbstractOrderState {

    public PendingState(RestaurantOrder order) {
        super(order);
    }

    @Override
    public AbstractOrderState accept() throws IllegalStateException {
        updateStatus(OrderStatus.ACCEPTED);
        return new AcceptedState(getOrder());
    }

    @Override
    public AbstractOrderState decline() throws IllegalStateException {
        updateStatus(OrderStatus.DECLINED);
        return new DeclinedState(getOrder());
    }
}
