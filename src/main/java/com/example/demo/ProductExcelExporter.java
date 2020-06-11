package com.example.demo;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Product> list;

    private void writeHeaderRow() {
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        Cell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell.setCellStyle(style);


        cell = row.createCell(1);
        cell.setCellValue("Name");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("Email");
        cell.setCellStyle(style);

    }

    private void writeDateRow() {
        int rowCount = 1;
        for (Product product : list) {
            Row row = sheet.createRow(rowCount++);

            Cell cell = row.createCell(0);
            cell.setCellValue(product.getId());
            sheet.autoSizeColumn(0);

            cell = row.createCell(1);
            cell.setCellValue(product.getName());
            sheet.autoSizeColumn(1);

            cell = row.createCell(2);
            cell.setCellValue(product.getEmail());
            sheet.autoSizeColumn(2);
        }
    }

    public void Export(HttpServletResponse response) throws IOException {
        writeDateRow();
        writeHeaderRow();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public ProductExcelExporter(List<Product> list) {
        this.list = list;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Products");
    }
}
