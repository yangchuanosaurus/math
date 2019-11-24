package com.yangchuanosaurus.children.count.pdf;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.EncryptionConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.BlockElement;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.text.pdf.BaseFont;

public class PdfAdditionMaze {
    static final String DEST = "./maze_addition.pdf";

    public static void main(String[] args) throws Exception {
        new PdfAdditionMaze().createPdf(DEST);
    }

    void createPdf(String dest) throws Exception {
        PageSize pageSize = PageSize.A4;

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, pageSize);

        // Here in itext7 there is only one way of adding paragraph to table. See itext5 example.
        Table table = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
        table.setBorder(Border.NO_BORDER);
        Paragraph titlePara = new Paragraph("加法闯迷宫")
                .setFont(FontManager.getDefault().getNotoSansMonoCJKsBold7())
                .setFontSize(26);
        Cell leftCell = new Cell().add(titlePara);
        leftCell.setBorder(Border.NO_BORDER);
        table.addCell(leftCell);

        Paragraph rightPara = new Paragraph("请你从起点开始，按照加法算式的正确答案，一步一步走出迷宫。")
                .setFont(FontManager.getDefault().getNotoSansMonoCJKsBold7())
                .setFontSize(14);
        rightPara.setMarginLeft(14);
        Cell rightCell = new Cell().add(rightPara);
        rightCell.setBorder(Border.NO_BORDER);
        table.addCell(rightCell);

        doc.add(table);

        ElementSize tableSize = calcElementSize(doc, table, pageSize);

        PdfCanvas canvas = new PdfCanvas(pdfDoc.getPage(1));


        Color magentaColor = new DeviceCmyk(0.f, 1.f, 0.f, 0.f);


        float marginWithTable = 10;
        float startX = doc.getLeftMargin();
        float startY = pageSize.getHeight() - doc.getTopMargin() - tableSize.getHeight() - marginWithTable;
        float availableWidth = pageSize.getWidth() - doc.getLeftMargin() - doc.getRightMargin();
        float availableHeight = pageSize.getHeight() - doc.getTopMargin() - doc.getBottomMargin() - tableSize.getHeight() - marginWithTable;

        int cellCount = 6;
        float squareMargin = 10;
        float squareSize = (availableWidth - (cellCount - 1) * squareMargin) / cellCount;
        int rowCount = (int) (availableHeight / squareSize);
        float textHeight = 10;
        float centerSquare = (squareSize - textHeight) / 2;


        PdfFont textFont = FontManager.getDefault().getNotoSansMonoCJKsBold7();
        int textFontSize = 12;
        for (int r = 0; r < rowCount; r++) {
            for (int col = 0; col < cellCount; col++) {
                float squareX = col * squareSize + col * squareMargin + startX;
                float squareY = startY - r * squareSize - r * squareMargin;
                canvas.setStrokeColor(magentaColor)
                        .moveTo(squareX, squareY)
                        .lineTo(squareX + squareSize, squareY)
                        .lineTo(squareX + squareSize, squareY - squareSize)
                        .lineTo(squareX, squareY - squareSize)
                        .lineTo(squareX, squareY)
                        .closePathStroke();

                String eq = "6 + 1";
                String top = "上";
                String left = "左";
                String right = "右";
                String bottom = "下";

                float eqWidth = textFont.getWidth(eq, textFontSize);
                float topWidth = textFont.getWidth(top, textFontSize);
                float leftWidth = textFont.getWidth(left, textFontSize);
                float rightWidth = textFont.getWidth(right, textFontSize);
                float bottomWidth = textFont.getWidth(bottom, textFontSize);

                canvas.beginText();
                canvas.setFontAndSize(textFont, textFontSize);
                canvas.moveText(squareX + (squareSize - eqWidth) / 2, squareY - centerSquare - textHeight);
                canvas.showText(eq);
                canvas.moveText((eqWidth - topWidth) / 2, centerSquare);
                canvas.showText(top);
                canvas.moveText((topWidth - squareSize) / 2, -centerSquare);
                canvas.showText(left);
                canvas.moveText(squareSize - rightWidth, 0);
                canvas.showText(right);
                canvas.moveText((rightWidth - squareSize / 2) - bottomWidth / 2, -centerSquare);
                canvas.showText(bottom);
                canvas.endText();
            }
        }

        doc.close();
    }

    private ElementSize calcElementSize(Document doc, BlockElement element, PageSize pageSize) {
        IRenderer tableRenderer = element.createRendererSubTree();
        LayoutResult result = tableRenderer.setParent(doc.getRenderer())
                .layout(new LayoutContext(
                        new LayoutArea(1, new Rectangle(pageSize.getWidth(), pageSize.getHeight()))));
        return new ElementSize(result.getOccupiedArea().getBBox().getWidth(), result.getOccupiedArea().getBBox().getHeight());
    }

    static class ElementSize {
        float width;
        float height;
        public ElementSize(float width, float height) {
            this.width = width;
            this.height = height;
        }

        public float getWidth() {
            return width;
        }

        public float getHeight() {
            return height;
        }
    }
}
