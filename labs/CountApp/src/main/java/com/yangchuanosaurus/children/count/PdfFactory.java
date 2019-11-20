package com.yangchuanosaurus.children.count;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDiv;
import com.itextpdf.text.pdf.PdfPCell;
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

//        Paragraph paraHeader = new Paragraph();
//        Font headerFont = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.BLACK);
//        Chunk headerChunk = new Chunk("Count Exercises", headerFont);
//        paraHeader.add(headerChunk);
//        paraHeader.setAlignment(Element.ALIGN_CENTER);
//        document.add(paraHeader);

        int equationSize = equationList.size();
        Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
        Chunk chunk = new Chunk(equationSize + " total, " + (100.0f / equationSize) + " / equation, score 100.", font);
        Paragraph paraSummary = new Paragraph();
        paraSummary.setAlignment(Element.ALIGN_CENTER);
        paraSummary.add(chunk);
        document.add(paraSummary);

        //
        long seed = System.nanoTime();
        Collections.shuffle(equationList, new Random(seed));

        // Insert Table
        PdfPTable table = new PdfPTable(3);
        addRows(table, equationList);
        PdfDiv tableDiv = new PdfDiv();
        tableDiv.addElement(table);

        table.setSpacingBefore(20);
        table.setSpacingAfter(20);

        document.add(tableDiv);

        document.close();

        System.out.println(equationSize + " equation has been created in " + fileName + ", print it on paper.");
    }

    private static void addRows(PdfPTable table, List<Equation> equationList) {
        Font font = FontFactory.getFont(FontFactory.COURIER, 18, BaseColor.BLACK);
        for (int i = 0; i < equationList.size(); i++) {
            Equation equation = equationList.get(i);
//            table.addCell(equation.toString());
            Phrase phrase = new Phrase(equation.toString(), font);
            PdfPCell cell = new PdfPCell(phrase);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }
}
