package com.yangchuanosaurus.children.count.pdf;

import com.itextpdf.text.Chunk;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfInternational {
    public static final String DEST = "./hello_international.pdf";

    public static void main(String[] args)
            throws DocumentException, IOException {
        new PdfInternational().createPdf(DEST);
    }

    public void createPdf(String dest)
            throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        Font font = new Font(FontFamily.TIMES_ROMAN);
        Font font14pt = new Font(FontFamily.TIMES_ROMAN, 14);
        Font font10pt = new Font(FontFamily.TIMES_ROMAN, 10);

        BaseFont bf_cjk = BaseFont.createFont("./src/main/resources/NotoSansMonoCJKsc-Bold.otf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font cjk = new Font(bf_cjk, 12);
        Paragraph p = new Paragraph("Hello World! ", font);
        Chunk chunk = new Chunk("Hallo Wereld! ", font14pt);
        p.add(chunk);
        chunk = new Chunk("Bonjour le monde! ", font10pt);
        chunk.setTextRise(4);
        p.add(chunk);
        chunk = new Chunk(
                "你好", cjk);
        p.add(chunk);
        p.add(new Chunk("我是中文", cjk));
        p.add(new Chunk("中国字，中国话", cjk));
        document.add(p);
        document.close();
    }
}
