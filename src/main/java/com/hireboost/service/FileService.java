package com.hireboost.service;

import com.hireboost.exception.FileGenerateException;
import com.hireboost.model.enums.FileType;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Slf4j
@Service
public class FileService {

    public byte[] generateFile(String content, FileType fileType) {
        return switch (fileType) {
            case PDF -> generatePdf(content);
            case DOCX -> generateDocx(content);
        };
    }

    private byte[] generatePdf(String content) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            com.lowagie.text.Document document = new com.lowagie.text.Document();
            com.lowagie.text.pdf.PdfWriter.getInstance(document, out);

            document.open();
            document.add(new com.lowagie.text.Paragraph(content));
            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            log.error("Error generating PDF file", e);
            throw new FileGenerateException("Failed to generate PDF file", e);
        }
    }

    private byte[] generateDocx(String content) {
        try (XWPFDocument document = new XWPFDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            document.createParagraph().createRun().setText(content);
            document.write(out);
            return out.toByteArray();

        } catch (Exception e) {
            log.error("Error generating DOCX file", e);
            throw new FileGenerateException("Failed to generate DOCX file", e);
        }
    }
}
