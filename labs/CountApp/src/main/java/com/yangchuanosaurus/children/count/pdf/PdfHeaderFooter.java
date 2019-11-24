package com.yangchuanosaurus.children.count.pdf;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.File;

public class PdfHeaderFooter {
    public static final String DEST = "./tamp_header1.pdf";
    public static final String SRC = "./hello_international.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new PdfHeaderFooter().manipulatePdf(DEST);
    }

    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(DEST));
        Document doc = new Document(pdfDoc);
        Paragraph header = new Paragraph("Copy")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(14)
                .setFontColor(ColorConstants.RED);
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
            float x = pdfDoc.getPage(i).getPageSize().getWidth() / 2;
            float y = pdfDoc.getPage(i).getPageSize().getTop() - 20;
            doc.showTextAligned(header.setFontColor(ColorConstants.RED), x, y, i,
                    TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);
        }
        doc.close();
    }
}
