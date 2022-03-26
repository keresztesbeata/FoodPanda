package app.dto;

import app.model.OrderStatus;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
@Setter
@Getter
public class PlacedOrderDto {

    private String orderStatus;

    private LocalDate orderDate;

    @NotNull
    private String restaurant;

    @NotNull
    private String user;

    private CartDto cartDto;

    private boolean withCutlery;

    private String remark;

    private PlacedOrderDto() {
    }

    private PlacedOrderDto(String orderStatus, LocalDate orderDate, String restaurant, String user, CartDto cartDto, boolean withCutlery, String remark) {
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.restaurant = restaurant;
        this.user = user;
        this.cartDto = cartDto;
        this.withCutlery = withCutlery;
        this.remark = remark;
    }

    public static class PlacedOrderDtoBuilder {
        private String orderStatus;

        private LocalDate orderDate;

        @NotNull
        private String restaurant;

        @NotNull
        private String user;

        private boolean withCutlery;

        private String remark;

        private CartDto cartDto;

        public PlacedOrderDtoBuilder(String restaurant, String user, CartDto cartDto) {
            this.orderStatus = OrderStatus.PENDING.name();
            this.orderDate = LocalDate.now();
            this.restaurant = restaurant;
            this.user = user;
            this.cartDto = cartDto;
            this.withCutlery = false;
            this.remark = "";
        }

        public PlacedOrderDto build() {
            return new PlacedOrderDto(orderStatus, orderDate, restaurant, user, cartDto, withCutlery, remark);
        }

        public PlacedOrderDtoBuilder withCutlery(boolean withCutlery) {
            this.withCutlery = withCutlery;
            return this;
        }

        public PlacedOrderDtoBuilder withRemark(String remark) {
            this.remark = remark;
            return this;
        }

        public PlacedOrderDtoBuilder withCartDto(CartDto cartDto) {
            this.cartDto = cartDto;
            return this;
        }

        public PlacedOrderDtoBuilder withOrderDate(LocalDate orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public PlacedOrderDtoBuilder withOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

    }
}
