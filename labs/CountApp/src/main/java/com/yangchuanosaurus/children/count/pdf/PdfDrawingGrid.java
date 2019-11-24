package com.yangchuanosaurus.children.count.pdf;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

import java.io.File;
import java.io.IOException;

public class PdfDrawingGrid {

    public static final String DEST = "./grid.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new PdfDrawingGrid().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST));
        PageSize pageSize = PageSize.A4;
        pdfDoc.setDefaultPageSize(pageSize);

        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        int col = 5;
        int row = 8;
        float gridWidth = pageSize.getWidth() / col;
        float gridHeight = pageSize.getHeight() / row;
        for (float x = 0; x < pageSize.getWidth(); ) {
            for (float y = 0; y < pageSize.getHeight(); ) {
                canvas.circle(x, y, 1f);
                y += gridHeight;
            }
            x += gridWidth;
        }
        canvas.fill();

        pdfDoc.close();
    }
}
