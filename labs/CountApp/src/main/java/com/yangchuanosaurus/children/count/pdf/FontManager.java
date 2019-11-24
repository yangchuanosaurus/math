package com.yangchuanosaurus.children.count.pdf;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

import java.io.IOException;

public class FontManager {
    private static final String NOTO_SANS_MONO_CJK_SC_BOLD_FONT = "./src/main/resources/NotoSansMonoCJKsc-Bold.otf";

    private FontManager() {

    }

    private interface LazyLoader {
        FontManager SINGLETON = new FontManager();
    }

    public static FontManager getDefault() {
        return LazyLoader.SINGLETON;
    }

    public Font getNotoSansMonoCJKsBold(int size) {
        BaseFont bf_cjk = null;
        try {
            bf_cjk = BaseFont.createFont(NOTO_SANS_MONO_CJK_SC_BOLD_FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Font(bf_cjk, size);
    }

    public PdfFont getNotoSansMonoCJKsBold7() {
        try {
            return PdfFontFactory.createFont(NOTO_SANS_MONO_CJK_SC_BOLD_FONT, PdfEncodings.IDENTITY_H, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
