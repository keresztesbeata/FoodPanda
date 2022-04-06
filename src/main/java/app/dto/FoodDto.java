package app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Setter
@Getter
@NoArgsConstructor
public class FoodDto {
    private String name;
    private String description;
    private String category;
    private Integer portion;
    private Double price;
    private String restaurant;
    /*
    private Optional<NutritionFactsDto> nutritionFacts;

    private FoodDto(String name, String description, String category, Integer portion, Double price, String restaurant, Optional<NutritionFactsDto> nutritionFactsDto) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.portion = portion;
        this.price = price;
        this.restaurant = restaurant;
        this.nutritionFacts = nutritionFactsDto;
    }

    @Getter
    static class FoodDtoBuilder {
        private String name;
        private String description;
        private String category;
        private Integer portion;
        private Double price;
        private String restaurant;
        private Optional<NutritionFactsDto> nutritionFacts = Optional.empty();

        public FoodDtoBuilder withBasicAttributes(BasicFoodDto basicFoodDto) {
            this.name = basicFoodDto.getName();
            this.description = basicFoodDto.getDescription();
            this.category = basicFoodDto.getCategory();
            this.portion = basicFoodDto.getPortion();
            this.price = basicFoodDto.getPrice();
            this.restaurant = basicFoodDto.getRestaurant();
            return this;
        }

        public FoodDtoBuilder withNutritionFacts(NutritionFactsDto nutritionFactsDto) {
            this.nutritionFacts = Optional.of(nutritionFactsDto);
            return this;
        }

        public FoodDto build() {
            return new FoodDto(name, description, category, portion, price, restaurant, nutritionFacts);
        }
    }

     */
}
