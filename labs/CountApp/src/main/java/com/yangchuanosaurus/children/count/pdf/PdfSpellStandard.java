package com.yangchuanosaurus.children.count.pdf;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class PdfSpellStandard {

    private static final String FONT_FANG_ZHENG_PY = "./src/main/resources/FangZhengKaiTiPinYinZiKu-1.ttc";
    static final String DEST = "./standard_spell.pdf";

    public static void main(String[] args) throws Exception {
        new PdfSpellStandard().createPdf(DEST);
    }

    void createPdf(String dest) throws Exception {
        PageSize pageSize = PageSize.A4;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, pageSize);

        PdfFont fzFont = FontManager.getDefault().getFont(FONT_FANG_ZHENG_PY);
        Paragraph samplePara = new Paragraph("a b c d")
                .setFont(fzFont)
                .setFontSize(26);
        doc.add(samplePara);
        doc.close();
    }
}
