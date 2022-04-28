package app.service.impl;


import app.model.Food;
import app.model.RestaurantOrder;
import app.model.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.datetime.standard.DateTimeFormatterFactory;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * It is responsible for generating a bill or order invoice after an order was created.
 */
@Component
public class BillGenerator {
    /**
     * Represents the path to the directory where the bills are stored.
     */
    private static final String billDirPath = "bills/";
    /**
     * Represent some predefined fonts used to style the text of the pdf
     */
    private static final Font FOOTER_FONT = FontFactory.getFont(FontFactory.TIMES, 20, Font.NORMAL, BaseColor.DARK_GRAY);
    private static final Font HEADER_FONT = FontFactory.getFont(FontFactory.TIMES, 12, Font.ITALIC, BaseColor.BLACK);
    private static final Font TITLE_FONT = FontFactory.getFont(FontFactory.TIMES, 18, Font.BOLD, BaseColor.BLACK);
    private static final Font SECTION_FONT = FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, BaseColor.BLACK);
    /**
     * The logger instance which allows writing log messages.
     */
    protected static final Logger LOGGER = LogManager.getLogger(BillGenerator.class);

    /**
     * It creates a pdf with the relevant details of the order.
     *
     * @param order the order which was created
     */
    public void generateBill(RestaurantOrder order) {
        try {
            Document document = new Document();
            String documentName = "customer_" + order.getCustomer().getId() + "_order_" + order.getOrderNumber() + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(billDirPath + documentName));
            document.open();
            Chunk title = new Chunk("Bill");
            title.setFont(TITLE_FONT);
            Paragraph titleParagraph = new Paragraph();
            titleParagraph.add(title);
            document.add(titleParagraph);

            Paragraph header = new Paragraph();
            header.setFont(HEADER_FONT);
            header.add(new Phrase("The following invoice contains the details of your order.\n"));
            header.add(new Phrase("The order has been saved and it is going to be delivered shortly.\n"));
            document.add(header);

            Paragraph clientParagraph = createParagraphWithData("Customer details");
            clientParagraph.add(getDataOfCustomer(order.getCustomer()));
            document.add(clientParagraph);

            document.add(createParagraphWithData("Ordered food details"));
            for (Map.Entry<Food, Integer> foodWithQuantity : order.getOrderedFoods().entrySet()) {
                Paragraph foodParagraph = new Paragraph();
                foodParagraph.add(getDataOfFood(foodWithQuantity.getKey(), foodWithQuantity.getValue()));
                document.add(foodParagraph);
            }

            Paragraph orderParagraph = createParagraphWithData("Order details");
            orderParagraph.add(getDataOfOrder(order));
            document.add(orderParagraph);

            Paragraph footer = new Paragraph();
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setFont(FOOTER_FONT);
            footer.add(new Phrase("Thank you for buying from us!"));
            document.add(footer);

            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            LOGGER.error("BillGenerator: generateBill {}", e.getMessage());
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

    private PdfPTable getDataOfOrder(RestaurantOrder order) {
        PdfPTable table = new PdfPTable(new float[]{25, 75});
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Order Nr"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(order.getOrderNumber())));

        cell = new PdfPCell(new Phrase("Restaurant"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(order.getRestaurant().getName())));

        cell = new PdfPCell(new Phrase("Order Date"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(order.getDateCreated().toString())));

        cell = new PdfPCell(new Phrase("Cutlery"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(order.getWithCutlery()? "Yes":"No")));

        cell = new PdfPCell(new Phrase("Remark"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(order.getRemark())));

        cell = new PdfPCell(new Phrase("Delivery fee"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(order.getRestaurant().getDeliveryFee().toString())));

        cell = new PdfPCell(new Phrase("Total Price"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(order.getTotalPrice().toString())));

        return table;
    }

    private PdfPTable getDataOfFood(Food food, int quantity) {
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

        cell = new PdfPCell(new Phrase("Quantity"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(Integer.toString(quantity))));

        cell = new PdfPCell(new Phrase("Price"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(food.getPrice().toString())));

        return table;
    }

    private PdfPTable getDataOfCustomer(User customer) {
        PdfPTable table = new PdfPTable(new float[]{25, 75});
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Customer ID"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(customer.getId().toString())));

        cell = new PdfPCell(new Phrase("Username"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(new PdfPCell(new Phrase(customer.getUsername())));
        return table;
    }
}

