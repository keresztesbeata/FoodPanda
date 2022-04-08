package app.model.order_states;

import app.model.OrderStatus;
import app.model.RestaurantOrder;
import lombok.Getter;

public abstract class AbstractOrderState {

    @Getter
    private RestaurantOrder order;

    public AbstractOrderState(RestaurantOrder order) {
        this.order = order;
    }

    protected void updateStatus(OrderStatus orderStatus) {
        order.setOrderStatus(orderStatus);
    }

    public AbstractOrderState setDelivered() throws IllegalStateException {
        throw new IllegalStateException("You cannot update the status of the order from "+order.getOrderStatus() + " to DELIVERED!");
    }

    public AbstractOrderState setInDelivery() throws IllegalStateException {
        throw new IllegalStateException("You cannot update the status of the order from "+order.getOrderStatus() + " to IN_DELIVERY!");
    }

    public AbstractOrderState accept() throws IllegalStateException {
        throw new IllegalStateException("You cannot update the status of the order from "+order.getOrderStatus() + " to ACCEPTED!");
    }

    public AbstractOrderState decline() throws IllegalStateException {
        throw new IllegalStateException("You cannot update the status of the order from "+order.getOrderStatus() + " to DECLINED!");
    }
}
