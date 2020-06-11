package com.example.demo;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UserPDFExporter {
    private List<Product> list;

    public UserPDFExporter(List<Product> list) {
        this.list = list;
    }
    private void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.blue);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);


        cell.setPhrase(new Phrase("id" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("name" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("email" , font));
        table.addCell(cell);
    }
    private void writeTableData(PdfPTable table){
        for (Product product: list) {
            table.addCell(String.valueOf(product.getId()));
            table.addCell(product.getName());
            table.addCell(product.getEmail());

        }
    }
    public void Export(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document , response.getOutputStream());
        document.open();
        document.add(new Paragraph("List all of the Product"));
        // xet so cot trong bang ma ban muon xuat vd 3
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);
        writeTableHeader(table);
        writeTableData(table);
        document.add(table);
        document.close();

    }
}
