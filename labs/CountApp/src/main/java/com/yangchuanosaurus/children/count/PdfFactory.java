package com.yangchuanosaurus.children.count;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDiv;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PdfFactory {

    public static final String FONT = "NotoSansCJKsc-Regular.otf";//"NotoMono-Regular.ttf";
    public static final String CHINESE = "\u5341\u950a\u57cb\u4f0f";

    public static void generatePdf(String fileName, List<Equation> equationList) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));

        document.open();

        int equationSize = equationList.size();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk(equationSize + " total, " + (100.0f / equationSize) + " / equation, score 100.", font);
        document.add(chunk);

        //
        long seed = System.nanoTime();
        Collections.shuffle(equationList, new Random(seed));

        // Insert Table
        PdfPTable table = new PdfPTable(3);
        addRows(table, equationList);
        PdfDiv tableDiv = new PdfDiv();
        tableDiv.addElement(table);
        document.add(tableDiv);

        document.close();

        System.out.println(equationSize + " equation has been created in " + fileName + ", print it on paper.");
    }

    private static void addRows(PdfPTable table, List<Equation> equationList) {
        for (int i = 0; i < equationList.size(); i++) {
            Equation equation = equationList.get(i);
            table.addCell(equation.toString());
        }
    }
}
