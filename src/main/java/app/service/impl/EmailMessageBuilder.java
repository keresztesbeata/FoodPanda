package app.service.impl;

import app.model.RestaurantOrder;

public class EmailMessageBuilder {
    private RestaurantOrder restaurantOrder;

    public EmailMessageBuilder(RestaurantOrder restaurantOrder) {
        this.restaurantOrder = restaurantOrder;
    }

    public String buildTitle() {
        return "Order #" + restaurantOrder.getOrderNumber() + " has been created";
    }

    public String buildContent() {
        StringBuilder builder = new StringBuilder();
        builder.append("The details of the order:\n");
        builder.append("Restaurant: ")
                .append(restaurantOrder.getRestaurant().getName())
                .append("\n");
        builder.append("Order number: ")
                .append(restaurantOrder.getOrderNumber())
                .append("\n");
        builder.append("Customer name: ")
                .append(restaurantOrder.getCustomer().getUsername())
                .append("\n");
        builder.append("Created at: ")
                .append(restaurantOrder.getDateCreated())
                .append("\n");
        builder.append("Ordered foods: \n");
        restaurantOrder.getOrderedFoods()
                .forEach((key, value) -> builder.append(key.getName())
                        .append(" x ")
                        .append(value)
                        .append("\n"));
        builder.append("Total price: ")
                .append(restaurantOrder.getTotalPrice())
                .append("\n");
        builder.append("Want cutlery: ")
                .append(restaurantOrder.getWithCutlery())
                .append("\n");
        builder.append("Remarks: ")
                .append(restaurantOrder.getRemark())
                .append("\n");
        builder.append("Delivery address: ")
                .append(restaurantOrder.getDeliveryAddress())
                .append("\n");

        return builder.toString();
    }
}
