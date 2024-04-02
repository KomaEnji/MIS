import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class test1 {
    public static void main(String[] args){
        String s = "asdasd1111";
        try(XWPFDocument doc = new XWPFDocument(Files.newInputStream(Paths.get("C:\\Users\\Home\\Downloads\\shablon.docx")))) {
            List<XWPFParagraph> xwpfParagraphList = doc.getParagraphs();
            for (XWPFParagraph xwpfParagraph:xwpfParagraphList) {
                for (XWPFRun xwpfRun:xwpfParagraph.getRuns()) {
                    String docText = xwpfRun.getText(0);
                    if (docText.equals("ИМЯ")) {
                        docText = docText.replace("ИМЯ", s);
                    }
                    xwpfRun.setText(docText);
                }
            }
            try (FileOutputStream out = new FileOutputStream("C:\\Users\\Home\\Downloads\\shablon.docx")) {
                doc.write(out);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } ;

    }
}
