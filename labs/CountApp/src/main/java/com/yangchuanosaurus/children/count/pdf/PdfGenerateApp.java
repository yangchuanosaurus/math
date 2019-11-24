package com.yangchuanosaurus.children.count.pdf;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfViewerPreferences;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

// https://itextpdf.com/en/resources/examples
@SpringBootApplication
public class PdfGenerateApp implements CommandLineRunner {
    public static final String FONT = "./NotoSansMonoCJKsc-Bold.otf";
    public static final String[][] DATA = {
            {"John Edward Jr.", "AAA"},
            {"Pascal Einstein W. Alfi", "BBB"},
            {"St. John", "CCC"}
    };

    public static void main(String args[]) throws Exception {
        SpringApplication.run(PdfGenerateApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        PdfDocument pdf = new PdfDocument(new PdfWriter("test.pdf"));
        PdfViewerPreferences preferences = new PdfViewerPreferences();
        preferences.setFitWindow(true);
        preferences.setHideMenubar(true);
        preferences.setHideToolbar(true);
        preferences.setHideWindowUI(true);
        preferences.setCenterWindow(true);
        preferences.setDisplayDocTitle(true);
        pdf.getCatalog().setViewerPreferences(preferences);

        addMetaData(pdf);

        Document document = new Document(pdf, PageSize.A4);

        Paragraph p = new Paragraph();
        String s = "all text is written in red, except the letters b and g; they are written in blue and green. all text is written in red, except the letters b and g; they are written in blue and green.";
        for (int i = 0; i < s.length(); i++) {
            p.add(returnCorrectColor(s.charAt(i)));
        }
        document.add(p);

        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 1, 1, 1}));
        table.addCell(createCell("SKU", 2, 1, TextAlignment.LEFT));
        table.addCell(createCell("Description", 2, 1, TextAlignment.LEFT));
        table.addCell(createCell("Unit Price", 2, 1, TextAlignment.LEFT));
        table.addCell(createCell("Quantity", 2, 1, TextAlignment.LEFT));
        table.addCell(createCell("Extension", 2, 1, TextAlignment.LEFT));
        String[][] data = {
                {"ABC123", "The descriptive text may be more than one line and the text should wrap automatically",
                        "$5.00", "10", "$50.00"},
                {"QRS557", "Another description", "$100.00", "15", "$1,500.00"},
                {"XYZ999", "Some stuff", "$1.00", "2", "$2.00"}
        };
        for (String[] row : data) {
            table.addCell(createCell(row[0], 1, 1, TextAlignment.LEFT));
            table.addCell(createCell(row[1], 1, 1, TextAlignment.LEFT));
            table.addCell(createCell(row[2], 1, 1, TextAlignment.RIGHT));
            table.addCell(createCell(row[3], 1, 1, TextAlignment.RIGHT));
            table.addCell(createCell(row[4], 1, 1, TextAlignment.RIGHT));
        }
        table.addCell(createCell("Totals", 2, 4, TextAlignment.LEFT));
        table.addCell(createCell("$1,552.00", 2, 1, TextAlignment.RIGHT));
        document.add(table);

        int n = pdf.getNumberOfPages();
        for (int i = 1; i <= n; i++) {
            Paragraph pagePara = new Paragraph(String.format("page %s of %s", i, n));
            float pageParaWidth = 0;

            float x = (pdf.getPage(i).getPageSize().getWidth() - pageParaWidth) / 2;
            float y = pdf.getPage(i).getPageSize().getBottom() + 20;

            float width = pdf.getPage(i).getPageSize().getWidth();
            float height = pdf.getPage(i).getPageSize().getTop();
            System.out.println("PDF width " + width + ", height " + height);

            document.showTextAligned(pagePara,
                    x, y, i, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
        }

        document.close();
    }

    private Text returnCorrectColor(char letter) throws IOException {
        if (letter == 'b') {
            return new Text("b")
                    .setFontColor(ColorConstants.BLUE)
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD));
        } else if (letter == 'g') {
            return new Text("g")
                    .setFontColor(ColorConstants.GREEN)
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setItalic();
        } else {
            return new Text(String.valueOf(letter))
                    .setFontColor(ColorConstants.RED)
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA));
        }
    }

    public Cell createCell(String content, float borderWidth, int colspan, TextAlignment alignment) {
        Cell cell = new Cell(1, colspan).add(new Paragraph(content));
        cell.setTextAlignment(alignment);
        cell.setBorder(new SolidBorder(borderWidth));
        return cell;
    }

    private void addMetaData(PdfDocument pdf) {
        PdfDocumentInfo info = pdf.getDocumentInfo();
        info.setTitle("The Strange Case of Dr. Jekyll and Mr. Hyde");
        info.setAuthor("Robert Louis Stevenson");
        info.setSubject("A novel");
        info.setKeywords("Dr. Jekyll, Mr. Hyde");
        info.setCreator("A simple tutorial example");
    }
}
