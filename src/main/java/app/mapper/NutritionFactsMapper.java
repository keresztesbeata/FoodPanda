package app.mapper;

import app.dto.NutritionFactsDto;
import app.model.NutritionFacts;
import org.springframework.stereotype.Component;

@Component
public class NutritionFactsMapper implements Mapper<NutritionFacts, NutritionFactsDto> {

    @Override
    public NutritionFactsDto toDto(NutritionFacts nutritionFacts) {
        NutritionFactsDto nutritionFactsDto = new NutritionFactsDto();

        nutritionFactsDto.setFat(nutritionFacts.getFat());
        nutritionFactsDto.setCarbohydrate(nutritionFacts.getCarbohydrate());
        nutritionFactsDto.setProtein(nutritionFacts.getProtein());
        nutritionFactsDto.setSodium(nutritionFactsDto.getSodium());
        nutritionFactsDto.setCholesterol(nutritionFacts.getCholesterol());

        return nutritionFactsDto;
    }

    @Override
    public NutritionFacts toEntity(NutritionFactsDto nutritionFactsDto) {
        NutritionFacts nutritionFacts = new NutritionFacts();

        nutritionFacts.setCarbohydrate(nutritionFactsDto.getCarbohydrate());
        nutritionFacts.setFat(nutritionFactsDto.getFat());
        nutritionFacts.setProtein(nutritionFactsDto.getProtein());
        nutritionFacts.setCholesterol(nutritionFacts.getCholesterol());
        nutritionFacts.setSodium(nutritionFacts.getSodium());

        return nutritionFacts;
    }
}
