package com.yangchuanosaurus.children.count;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CountApp {

    public static void main(String args[]) throws Exception {
//        AdditionCount additionCount = CountFactory.createAdditionCount(10);
//        additionCount.getEquations().forEach(equation -> {
//            System.out.println(equation);
//        });

        SubtractionCount subtractionCount = CountFactory.createSubtractionCount(10);
        subtractionCount.getEquations().forEach(equation -> {
            System.out.println(equation);
        });

        PdfFactory.generatePdf("count_10_addition.pdf");
    }

    private static void createPdf() throws Exception {
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

    private static void addTableHeader(PdfPTable table) {
        Stream.of("Column header 1", "Column header 2", "Column header 3")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private static void addRows(PdfPTable table) {
        table.addCell("row1, col1");
        table.addCell("row1, col2");
        table.addCell("row1, col3");
    }

    private static void addCustomRows(PdfPTable table) throws URISyntaxException, BadElementException, IOException {
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

    private static void encryption() throws IOException, DocumentException {
        PdfReader pdfReader = new PdfReader("iTextHelloWorld.pdf");
        PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream("encryptedPdf.pdf"));

        pdfStamper.setEncryption("userpass".getBytes(), "".getBytes(), PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_256);
        pdfStamper.close();
    }

}
