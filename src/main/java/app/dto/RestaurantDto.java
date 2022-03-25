package app.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Setter
@Getter
public class RestaurantDto {
    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
    private String admin;

    private List<String> deliveryZones;

    private Integer openingHour;

    private Integer closingHour;

    private Double deliveryFee;

    private RestaurantDto() {
    }

    private RestaurantDto(String name, String address, String admin, List<String> deliveryZones, Integer openingHour, Integer closingHour, Double deliveryFee) {
        this.name = name;
        this.address = address;
        this.admin = admin;
        this.deliveryZones = deliveryZones;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.deliveryFee = deliveryFee;
    }

    public static class RestaurantDtoBuilder {
        @NotNull
        private String name;

        @NotNull
        private String address;

        @NotNull
        private String admin;

        private List<String> deliveryZones;

        private Integer openingHour;

        private Integer closingHour;

        private Double deliveryFee;

        public RestaurantDtoBuilder(String name, String address, String admin) {
            this.name = name;
            this.address = address;
            this.admin = admin;
            this.deliveryZones = new ArrayList<>();
            this.openingHour = 6;
            this.closingHour = 23;
            this.deliveryFee = 0d;
        }

        public RestaurantDto build() {
            return new RestaurantDto(name, address, admin, deliveryZones, openingHour, closingHour, deliveryFee);
        }

        public RestaurantDtoBuilder withDeliveryZones(List<String> deliveryZones) {
            this.deliveryZones = deliveryZones;
            return this;
        }

        public RestaurantDtoBuilder withOpeningHour(Integer openingHour) {
            this.openingHour = openingHour;
            return this;
        }

        public RestaurantDtoBuilder withClosingHour(Integer closingHour) {
            this.closingHour = closingHour;
            return this;
        }

        public RestaurantDtoBuilder withDeliveryFee(Double deliveryFee) {
            this.deliveryFee = deliveryFee;
            return this;
        }
    }
}
