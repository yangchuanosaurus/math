package com.yangchuanosaurus.children.count.pdf;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

import java.io.File;

public class PdfDrawingLines {
    public static final String DEST = "./drawing_lines.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new PdfDrawingLines().manipulatePdf(DEST);
    }

    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());

        Color magentaColor = new DeviceCmyk(0.f, 1.f, 0.f, 0.f);

        float width = pdfDoc.getPage(1).getPageSize().getWidth();
        float height = pdfDoc.getPage(1).getPageSize().getTop();
        System.out.println("PDF width " + width + ", height " + height);
        canvas.setStrokeColor(magentaColor)
                .moveTo(1, 1)
                .lineTo(1, height-1)
                .lineTo(width-1, height-1)
                .lineTo(width-1, 1)
                .lineTo(1, 1)
                .closePathStroke();

        pdfDoc.close();
    }
}
