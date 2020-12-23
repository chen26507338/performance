package com.stylefeng.guns.base;

import com.alibaba.druid.util.Base64;
import com.stylefeng.guns.core.util.FileUtils;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NormalJunit {



    @Test
    public void httpStatus() {
        System.out.println(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void base64Pic() {
        try {
            System.out.println(Base64.byteArrayToBase64(FileUtils.readFileToByteArray(new File("C:\\Users\\Administrator\\Desktop\\TIM截图20180316085222.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPattern() {
        Pattern p = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        Matcher m = p.matcher("1992-99-99");
        System.out.println(m.find());
    }

    @Test
    public void test() {
        System.out.println(6*1.2);
    }

    @Test
    public void testWord() {
        String templatePath = "D:\\word\\template.doc";
//        XWPFDocument
//        InputStream is = new FileInputStream(templatePath);
//        XWPFDocument doc = new XWPFDocument(is);
//        Range range = doc.getRange();
//        //把range范围内的${reportDate}替换为当前的日期
//        range.replaceText("${reportDate}", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//        range.replaceText("${appleAmt}", "100.00");
//        range.replaceText("${bananaAmt}", "200.00");
//        range.replaceText("${totalAmt}", "300.00");
//        OutputStream os = new FileOutputStream("D:\\word\\write.doc");
//        //把doc输出到输出流中
//        doc.write(os);
//        this.closeStream(os);
//        this.closeStream(is);
    }

    @Test
    public void pdfToWord() {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("d:\\yaerJsAssess.docx");
            XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            XHTMLConverter.getInstance().convert(xwpfDocument, outputStream, null);

            String value = new String(outputStream.toByteArray());
            System.out.println(value);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
