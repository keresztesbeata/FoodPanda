package app.service.impl;

import app.model.Category;
import app.model.DeliveryZone;
import app.model.Food;
import app.model.Restaurant;
import app.repository.CategoryRepository;
import app.repository.FoodRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.stream.Collectors;

/**
 * It is responsible for exporting the menu of a restaurant in pdf format.
 */
@Log4j2
@Component
public class MenuExporter {
    // Represents the path to the directory where the menus are stored.
    private static final String menuDirPath = "menus/";
    // Represent some predefined fonts used to style the text of the pdf
    private static final Font FOOTER_FONT = FontFactory.getFont(FontFactory.TIMES, 20, Font.NORMAL, BaseColor.DARK_GRAY);
    private static final Font HEADER_FONT = FontFactory.getFont(FontFactory.TIMES, 12, Font.ITALIC, BaseColor.BLACK);
    private static final Font TITLE_FONT = FontFactory.getFont(FontFactory.TIMES, 18, Font.BOLD, BaseColor.BLACK);
    private static final Font SECTION_FONT = FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, BaseColor.BLACK);

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FoodRepository foodRepository;

    public void exportMenuAsPDF(Restaurant restaurant) {
        try {
            Document document = new Document();
            String documentName = restaurant.getName() + "_menu.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(menuDirPath + documentName));
            document.open();
            Chunk title = new Chunk("Menu");
            title.setFont(TITLE_FONT);
            Paragraph titleParagraph = new Paragraph();
            titleParagraph.add(title);
            document.add(titleParagraph);

            Paragraph header = new Paragraph();
            header.setFont(HEADER_FONT);
            header.add(new Phrase("The menu of the restaurant:\n"));
            document.add(header);

            Paragraph restaurantParagraph = createParagraphWithData("Restaurant details");
            restaurantParagraph.add(getDataOfRestaurant(restaurant));
            document.add(restaurantParagraph);

            document.add(createParagraphWithData("Food Selection:"));
            for (Category category : categoryRepository.findAll()) {
                Paragraph categoryParagraph = createParagraphWithData(category.getName());

                for (Food food : foodRepository.findByRestaurantAndCategory(restaurant.getName(), category.getName())) {
                    Paragraph foodParagraph = new Paragraph();
                    foodParagraph.add(getDataOfFood(food));
                    categoryParagraph.add(foodParagraph);
                }

                document.add(categoryParagraph);
            }

            Paragraph footer = new Paragraph();
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setFont(FOOTER_FONT);
            footer.add(new Phrase("We hope you liked our food selection!"));
            document.add(footer);

            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            log.error("MenuExporter: exportMenuAsPDF {}", e.getMessage());
        }
    }

    private Paragraph createParagraphWithData(String name) {
        Paragraph paragraph = new Paragraph();
        paragraph.setSpacingBefore(10);
        paragraph.setSpacingAfter(10);
        Chunk title = new Chunk(name + "\n");
        title.setFont(SECTION_FONT);
        paragraph.add(title);
        return paragraph;
    }

    private PdfPTable getDataOfFood(Food food) {
        PdfPTable table = new PdfPTable(new float[]{25, 75});
        PdfPCell cell;

        cell = new PdfPCell(new Phrase("Food name"));
        cell.setExtraParagraphSpace(20.0f);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(food.getName())));

        cell = new PdfPCell(new Phrase("Description"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(food.getDescription())));

        cell = new PdfPCell(new Phrase("Price"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(food.getPrice().toString())));

        return table;
    }

    private PdfPTable getDataOfRestaurant(Restaurant restaurant) {
        PdfPTable table = new PdfPTable(new float[]{25, 75});
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Name"));
        cell.setExtraParagraphSpace(20.0f);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(restaurant.getName())));

        cell = new PdfPCell(new Phrase("Location"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(restaurant.getAddress())));

        cell = new PdfPCell(new Phrase("Delivery fee"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(restaurant.getDeliveryFee().toString())));

        cell = new PdfPCell(new Phrase("Delivery zones"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(restaurant.getDeliveryZones()
                .stream()
                .map(DeliveryZone::getName)
                .collect(Collectors.joining(",")))));

        cell = new PdfPCell(new Phrase("Open at"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(restaurant.getOpeningHour().toString())));

        cell = new PdfPCell(new Phrase("Close at"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(restaurant.getClosingHour().toString())));

        return table;
    }
}
