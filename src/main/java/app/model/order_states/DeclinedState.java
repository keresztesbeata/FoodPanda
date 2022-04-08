package app.model.order_states;

import app.model.RestaurantOrder;

public class DeclinedState extends AbstractOrderState {

    public DeclinedState(RestaurantOrder order) {
        super(order);
    }

}
