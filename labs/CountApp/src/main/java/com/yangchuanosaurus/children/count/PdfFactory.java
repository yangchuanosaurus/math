package com.yangchuanosaurus.children.count;

import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PdfFactory {

    public static final String FONT = "NotoMono-Regular.ttf";
    public static final String CHINESE = "\u5341\u950a\u57cb\u4f0f";

    public static void generatePdf(String fileName) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));

        document.open();

        // Insert Text
        Font font = FontFactory.getFont(FONT, 16, BaseColor.BLACK);
        Paragraph header = new Paragraph("Header", font);
        document.add(header);
//        Chunk chunk = new Chunk("算数", font);

//        document.add(Chunk.NEWLINE);

        Font afont = FontFactory.getFont(FONT, 12, BaseColor.BLACK);
//        document.add(new Paragraph("Name", afont));
        document.add(new Chunk("Name", afont));


        // Insert Table
//        PdfPTable table = new PdfPTable(3);
//        addTableHeader(table);
//        addRows(table);
//        addCustomRows(table);
//        document.add(table);

        document.close();
    }
}
