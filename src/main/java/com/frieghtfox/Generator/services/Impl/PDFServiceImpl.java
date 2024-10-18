package com.frieghtfox.Generator.services.Impl;

import com.frieghtfox.Generator.model.InvoiceRequest;
import com.frieghtfox.Generator.model.Item;
import com.frieghtfox.Generator.services.PDFService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Service
public class PDFServiceImpl implements PDFService {
    @Override
    public String generatePdf(InvoiceRequest pdfRequest) {
        // Define the PDF file path
        String fileName = "invoice_" + System.currentTimeMillis() + ".pdf";
        String filePath = "C:\\Users\\Public\\Documents\\" + fileName; //you can change the location as per your choice

        try {
            // Create PDF document
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Add Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph title = new Paragraph("Invoice", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(Chunk.NEWLINE);

            // Add Seller and Buyer Table
            PdfPTable sellerBuyerTable = new PdfPTable(2);
            sellerBuyerTable.setWidthPercentage(100);
            sellerBuyerTable.setWidths(new float[]{1, 1});

            // Seller Info
            PdfPCell sellerCell = new PdfPCell();
            sellerCell.addElement(new Paragraph("Seller:"));
            sellerCell.addElement(new Paragraph(pdfRequest.getSeller()));
            sellerCell.addElement(new Paragraph(pdfRequest.getSellerAddress()));
            sellerCell.addElement(new Paragraph("GSTIN: " + pdfRequest.getSellerGstin()));
            sellerCell.setBorder(Rectangle.BOX);
            sellerBuyerTable.addCell(sellerCell);

            // Buyer Info
            PdfPCell buyerCell = new PdfPCell();
            buyerCell.addElement(new Paragraph("Buyer:"));
            buyerCell.addElement(new Paragraph(pdfRequest.getBuyer()));
            buyerCell.addElement(new Paragraph(pdfRequest.getBuyerAddress()));
            buyerCell.addElement(new Paragraph("GSTIN: " + pdfRequest.getBuyerGstin()));
            buyerCell.setBorder(Rectangle.BOX);
            sellerBuyerTable.addCell(buyerCell);

            document.add(sellerBuyerTable);

            document.add(Chunk.NEWLINE);

            // Add Items Table
            PdfPTable itemsTable = new PdfPTable(4);
            itemsTable.setWidthPercentage(100);
            itemsTable.setWidths(new float[]{3, 1, 1, 1});

            // Table Headers
            addTableHeader(itemsTable, "Item");
            addTableHeader(itemsTable, "Quantity");
            addTableHeader(itemsTable, "Rate");
            addTableHeader(itemsTable, "Amount");

            // Add each item to the table
            for (Item item : pdfRequest.getItems()) {
                itemsTable.addCell(item.getName());
                itemsTable.addCell(item.getQuantity());
                itemsTable.addCell(String.valueOf(item.getRate()));
                itemsTable.addCell(String.valueOf(item.getAmount()));
            }

            document.add(itemsTable);

            // Close the document
            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return filePath;  // Return the file path to download later
    }

    private void addTableHeader(PdfPTable table, String headerTitle) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setPhrase(new Phrase(headerTitle));
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);
    }
}