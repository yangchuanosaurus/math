package com.yangchuanosaurus.children.count;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class CountApp implements CommandLineRunner {

    public static void main(String args[]) throws Exception {
        SpringApplication.run(CountApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        AdditionCount additionCount = CountFactory.createAdditionCount(10);
        SubtractionCount subtractionCount = CountFactory.createSubtractionCount(10);
        List<Equation> combinedEquations = new ArrayList<>();
        combinedEquations.addAll(additionCount.getEquations());
        combinedEquations.addAll(subtractionCount.getEquations());

        PdfFactory.generatePdf("count_10_addition.pdf", combinedEquations);
    }

    private void createPdf() throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));

        document.open();

        // Insert Text
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Hello World", font);
        document.add(chunk);

        // Add image
        Path path = Paths.get(ClassLoader.getSystemResource("count.png").toURI());
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        document.add(img);

        // Insert Table
        PdfPTable table = new PdfPTable(3);
        addTableHeader(table);
        addRows(table);
        addCustomRows(table);
        document.add(table);

        document.close();

        encryption();
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Column header 1", "Column header 2", "Column header 3")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table) {
        table.addCell("row1, col1");
        table.addCell("row1, col2");
        table.addCell("row1, col3");
    }

    private void addCustomRows(PdfPTable table) throws URISyntaxException, BadElementException, IOException {
        Path path = Paths.get(ClassLoader.getSystemResource("count.png").toURI());
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        img.scalePercent(10);

        PdfPCell imageCell = new PdfPCell(img);
        table.addCell(imageCell);

        PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
        horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(horizontalAlignCell);

        PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col3"));
        verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(verticalAlignCell);
    }

    private void encryption() throws IOException, DocumentException {
        PdfReader pdfReader = new PdfReader("iTextHelloWorld.pdf");
        PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream("encryptedPdf.pdf"));

        pdfStamper.setEncryption("userpass".getBytes(), "".getBytes(), PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_256);
        pdfStamper.close();
    }

}
