package app.model.order_states;

import app.model.OrderStatus;
import app.model.RestaurantOrder;
import lombok.Getter;

/**
 * Manages the status of a RestaurantOrder by providing methods for updating/changing the status of the given order.
 * Initially, none of the updates are supported. Each new order state which is added is represented by a class extending
 * the abstract class AbstractOrderState and providing an implementation for any of its methods.
 * It is part of the implementation of the State Design pattern.
 */
public abstract class AbstractOrderState {

    @Getter
    private RestaurantOrder order;

    public AbstractOrderState(RestaurantOrder order) {
        this.order = order;
    }

    /**
     * Update the state of the order.
     *
     * @param orderStatus the new state of the order
     */
    protected void updateStatus(OrderStatus orderStatus) {
        order.setOrderStatus(orderStatus);
    }

    /**
     * Change the state of the order to 'Delivered'.
     * The current state can be only 'In Delivery'.
     *
     * @return the class representing the new state of the order, DeliveredState
     * @throws IllegalStateException if the state of the order cannot be changed to the given new state from the current state
     */
    public AbstractOrderState setDelivered() throws IllegalStateException {
        throw new IllegalStateException("You cannot update the status of the order from "+order.getOrderStatus() + " to DELIVERED!");
    }

    /**
     * Change the state of the order to 'In delivery'.
     * The current state can be only 'Accepted'.
     *
     * @return the class representing the new state of the order, InDeliveryState
     * @throws IllegalStateException if the state of the order cannot be changed to the given new state from the current state
     */
    public AbstractOrderState setInDelivery() throws IllegalStateException {
        throw new IllegalStateException("You cannot update the status of the order from "+order.getOrderStatus() + " to IN_DELIVERY!");
    }

    /**
     * Change the state of the order to 'Accepted'.
     * The current state can be only 'Pending'.
     *
     * @return the class representing the new state of the order, AcceptedState
     * @throws IllegalStateException if the state of the order cannot be changed to the given new state from the current state
     */
    public AbstractOrderState accept() throws IllegalStateException {
        throw new IllegalStateException("You cannot update the status of the order from "+order.getOrderStatus() + " to ACCEPTED!");
    }

    /**
     * Change the state of the order to 'Declined'.
     * The current state can be only 'Pending'.
     *
     * @return the class representing the new state of the order, DeclinedState
     * @throws IllegalStateException if the state of the order cannot be changed to the given new state from the current state
     */
    public AbstractOrderState decline() throws IllegalStateException {
        throw new IllegalStateException("You cannot update the status of the order from "+order.getOrderStatus() + " to DECLINED!");
    }

    /**
     * Change the state of the order to 'Pending'.
     * This update would reset the status of the order to the initial status, 'Pending', which would ignore the fact that the order could have been already accepted/declined once.
     *
     * @return the class representing the new state of the order, PendingState
     * @throws IllegalStateException if the state of the order cannot be changed to the given new state from the current state
     */
    public AbstractOrderState resetToPending() throws IllegalStateException {
        throw new IllegalStateException("You cannot reset the status of the order from "+order.getOrderStatus() + " to PENDING!");
    }
}
