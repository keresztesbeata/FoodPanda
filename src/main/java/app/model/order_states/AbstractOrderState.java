package app.model.order_states;

import app.model.OrderStatus;
import app.model.RestaurantOrder;
import lombok.Getter;

import java.util.List;

public abstract class AbstractOrderState {

    @Getter
    private RestaurantOrder order;

    public abstract List<OrderStatus> getNextStates();

    public void setOrderStatus(OrderStatus orderStatus) {
        order.setOrderStatus(orderStatus);
    }

    public AbstractOrderState(RestaurantOrder order) {
        this.order = order;
    }

    public abstract void setDelivered();

    public abstract void setInDelivery();

    public abstract void accept();

    public abstract void decline();
}
