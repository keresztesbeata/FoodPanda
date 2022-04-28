package app.model.order_states;

import app.model.RestaurantOrder;
import org.springframework.stereotype.Component;

/**
 * It is responsible for instantiating the classes representing the different order statuses.
 * It represents an implementation of the Factory method.
 */
@Component
public class OrderStateFactory {

    /**
     * Get appropriate order status instance for the given order.
     * Parametrized factory method.
     *
     * @param order the order whose status is checked
     * @return a subclass of AbstractOrderState representing the given order's current state
     */
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
