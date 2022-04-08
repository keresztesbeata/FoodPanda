package app.model.order_states;

import app.model.RestaurantOrder;

public class DeliveredState extends AbstractOrderState {

    public DeliveredState(RestaurantOrder order) {
        super(order);
    }

}
