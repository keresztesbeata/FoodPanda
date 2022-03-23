package app.controller;

import app.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantRestController {

    @Autowired
    private RestaurantRepository restaurantRepository;

}
