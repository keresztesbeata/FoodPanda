package app.service.impl;


import app.mapper.RestaurantOrderMapper;
import app.model.*;
import app.repository.CartRepository;
import app.repository.RestaurantOrderRepository;
import app.repository.RestaurantRepository;
import app.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static app.service.impl.TestComponentFactory.*;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantOrderTest {

    @Spy
    private UserRepository userRepository;
    @Spy
    private RestaurantRepository restaurantRepository;
    @Spy
    private RestaurantOrderRepository restaurantOrderRepository;
    @Spy
    private CartRepository cartRepository;
    @Spy
    private RestaurantOrderMapper restaurantOrderMapper;
    @InjectMocks
    private RestaurantOrderServiceImpl restaurantOrderService;

    @Test
    public void testGetOrderByOrderNumber() {
        RestaurantOrder restaurantOrder = createRandomRestaurantOrder();
        String orderNumber = restaurantOrder.getOrderNumber();
        Mockito.when(restaurantOrderRepository.findByOrderNumber(orderNumber))
                .thenReturn(Optional.of(restaurantOrder));

        Assertions.assertEquals(orderNumber, restaurantOrderService.getOrderByOrderNumber(orderNumber).getOrderNumber());
    }

    @Test
    public void testGetOrdersOfCustomerByStatus() {
        int nrOrders = 10;
        List<RestaurantOrder> restaurantOrders = createRandomRestaurantOrderList(nrOrders);
        User customer = createRandomUser(UserRole.CUSTOMER);
        restaurantOrders.forEach(restaurantOrder -> restaurantOrder.setCustomer(customer));
        OrderStatus status = restaurantOrders.get(0).getOrderStatus();

        Mockito.when(restaurantOrderRepository.findByUserAndStatus(customer.getUsername(), status))
                .thenReturn(restaurantOrders);

        Assertions.assertEquals(nrOrders, restaurantOrderService.getOrdersOfCustomerByStatus(customer, Optional.of(status.name())).size());
    }

    @Test
    public void testGetOrdersOfRestaurantByStatus() {
        int nrOrders = 10;
        List<RestaurantOrder> restaurantOrders = createRandomRestaurantOrderList(nrOrders);
        Restaurant restaurant = createRandomRestaurant();
        restaurantOrders.forEach(restaurantOrder -> restaurantOrder.setRestaurant(restaurant));
        OrderStatus status = restaurantOrders.get(0).getOrderStatus();

        Mockito.when(restaurantOrderRepository.findByRestaurantAndStatus(restaurant.getName(), status))
                .thenReturn(restaurantOrders);

        Assertions.assertEquals(nrOrders, restaurantOrderService.getOrdersOfRestaurantByStatus(restaurant.getName(), Optional.of(status.name())).size());
    }

    @Test
    public void testUpdateOrderStatus() {
        RestaurantOrder restaurantOrder = createRandomRestaurantOrder();
        String orderNumber = restaurantOrder.getOrderNumber();

        Mockito.when(restaurantOrderRepository.findByOrderNumber(orderNumber))
                .thenReturn(Optional.of(restaurantOrder));

        restaurantOrder.setOrderStatus(OrderStatus.PENDING);
        Assertions.assertThrows(IllegalStateException.class, () -> restaurantOrderService.updateOrderStatus(orderNumber, OrderStatus.IN_DELIVERY.name()));
        Assertions.assertThrows(IllegalStateException.class, () -> restaurantOrderService.updateOrderStatus(orderNumber, OrderStatus.DELIVERED.name()));

        restaurantOrderService.updateOrderStatus(orderNumber, OrderStatus.ACCEPTED.name());
        Assertions.assertThrows(IllegalStateException.class, () -> restaurantOrderService.updateOrderStatus(orderNumber, OrderStatus.DECLINED.name()));
        Assertions.assertThrows(IllegalStateException.class, () -> restaurantOrderService.updateOrderStatus(orderNumber, OrderStatus.PENDING.name()));

        restaurantOrderService.updateOrderStatus(orderNumber, OrderStatus.IN_DELIVERY.name());
        Assertions.assertThrows(IllegalStateException.class, () -> restaurantOrderService.updateOrderStatus(orderNumber, OrderStatus.ACCEPTED.name()));
        Assertions.assertThrows(IllegalStateException.class, () -> restaurantOrderService.updateOrderStatus(orderNumber, OrderStatus.DECLINED.name()));

        restaurantOrderService.updateOrderStatus(orderNumber, OrderStatus.DELIVERED.name());
    }

    private List<RestaurantOrder> createRandomRestaurantOrderList(int size) {
        List<RestaurantOrder> orders = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            orders.add(createRandomRestaurantOrder());
        }
        return orders;
    }
}
