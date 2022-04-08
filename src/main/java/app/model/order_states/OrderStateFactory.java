package app.model.order_states;

import app.model.RestaurantOrder;
import org.springframework.stereotype.Component;

@Component
public class OrderStateFactory {

    public AbstractOrderState getOrderState(RestaurantOrder order) {
        switch (order.getOrderStatus()) {
            case PENDING: {
                return new PendingState(order);
            }
            case ACCEPTED: {
                return new AcceptedState(order);
            }
            case IN_DELIVERY: {
                return new InDeliveryState(order);
            }
            case DELIVERED: {
                return new DeliveredState(order);
            }
            case DECLINED:
            default: {
                return new DeclinedState(order);
            }
        }
    }
}
