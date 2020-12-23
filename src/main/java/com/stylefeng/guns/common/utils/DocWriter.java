package com.stylefeng.guns.common.utils;

import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.Map.Entry;
/**
 * Created by zhouhs on 2017/1/5.
 */
public class DocWriter {

    public static void searchAndReplace(String srcPath, OutputStream outputStream, Map<String, String> map) {
        try {
            XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(srcPath));
            /*
              替换段落中的指定文字
             */
            Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
            while (itPara.hasNext()) {
                XWPFParagraph paragraph = itPara.next();
                Set<String> set = map.keySet();
                /*
                 * 参数0表示生成的文字是要从哪一个地方开始放置,设置文字从位置0开始
                 * 就可以把原来的文字全部替换掉了
                 */
                for (String key : set) {
                    List<XWPFRun> run = paragraph.getRuns();
                    /*
                      参数0表示生成的文字是要从哪一个地方开始放置,设置文字从位置0开始
                      就可以把原来的文字全部替换掉了
                     */
                    for (XWPFRun xwpfRun : run) {
                        if (xwpfRun.getText(xwpfRun.getTextPosition()) != null &&
                                xwpfRun.getText(xwpfRun.getTextPosition()).equals(key)) {
                            /*
                              参数0表示生成的文字是要从哪一个地方开始放置,设置文字从位置0开始
                              就可以把原来的文字全部替换掉了
                             */
                            xwpfRun.setText(map.get(key), 0);
                        }
                    }
                }
            }

            /*
              替换表格中的指定文字
             */
            Iterator<XWPFTable> itTable = document.getTablesIterator();
            while (itTable.hasNext()) {
                XWPFTable table = itTable.next();
                int count = table.getNumberOfRows();
                for (int i = 0; i < count; i++) {
                    XWPFTableRow row = table.getRow(i);
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        for (Entry<String, String> e : map.entrySet()) {
                            if (cell.getText().contains(e.getKey())) {
                                cell.removeParagraph(0);
                                cell.setText(e.getValue());
                            }
                        }
                    }
                }
            }
//            FileOutputStream outStream;
//            outStream = new FileOutputStream(destPath);
            document.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("${text}", "12313123333");
        String srcPath = "D:\\yaerJsAssess.docx";
        String destPath = "D:\\2.doc";
//        searchAndReplace(srcPath, destPath, map);
    }
}