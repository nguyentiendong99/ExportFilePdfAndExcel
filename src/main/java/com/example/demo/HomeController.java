package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductService service;

    @RequestMapping("/")
    public String ShowProduct(Model model) {
        List<Product> products = service.listAll();
        model.addAttribute("product", products);
        return "home";
    }

    @RequestMapping("/new")
    public String newProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "newProduct";
    }

    @RequestMapping("/save")
    public String SaveProduct(@ModelAttribute("product") Product product) {
        service.SaveProduct(product);
        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView EditProduct(@PathVariable("id") int id) {
        ModelAndView model = new ModelAndView("editProduct");
        Product product = service.get(id);
        model.addObject("product", product);
        return model;
    }

    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        service.DeleteProduct(id);
        return "redirect:/";
    }

    @GetMapping("/product/export")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'");
        String currentDataTime = dateFormat.format(new Date());
        String filename = "product" + currentDataTime + ".csv";
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=" + filename;
        response.setHeader(headerKey, headerValue);
        List<Product> list = service.listAll();
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_PREFERENCE);
        String[] csvHearder = {"User id", "name", "email"};
        String[] nameMapping = {"id", "name", "email"};
        csvWriter.writeHeader(csvHearder);
        for (Product product : list) {
            csvWriter.write(product, nameMapping);
        }
        csvWriter.close();
    }

    @GetMapping("/product/exportExcel")
    public void ExportExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'");
        String currentDateTime = dateFormater.format(new Date());
        String fileName = "product_" + currentDateTime + ".xlsx";
        String headerValue = "attachment ; filename=" + fileName;
        response.setHeader(headerKey, headerValue);
        List<Product> list = service.listAll();
        ProductExcelExporter excelExporter = new ProductExcelExporter(list);
        excelExporter.Export(response);
    }
    @GetMapping("/product/exportPdf")
    public void ExportPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'");
        String currentDateTime = format.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=products_"+ currentDateTime +".pdf";
        response.setHeader(headerKey , headerValue);
        List<Product> list = service.listAll();
        UserPDFExporter exporter = new UserPDFExporter(list);
        exporter.Export(response);
    }
}
