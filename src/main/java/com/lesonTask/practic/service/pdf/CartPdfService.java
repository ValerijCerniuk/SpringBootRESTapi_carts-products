package com.lesonTask.practic.service.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.lesonTask.practic.model.Cart;
import com.lesonTask.practic.model.Product;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CartPdfService {

    public void exportCartToPdf(final Cart cart) {

        Document document = new Document();
        final String OWNER = cart.getOwnerName();
        final String EMAIL = cart.getOwnerEmail();
        final List<Product> PRODUCTS = cart.getProducts();

        try {
            Path path = Paths.get(ClassLoader.getSystemResource("img/BackTextForPdf.jpg").toURI());

            OutputStream outputStream = new FileOutputStream("Products.pdf");
            PdfWriter.getInstance(document, outputStream);
            document.open();

            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
                    BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            BaseFont bf2 = BaseFont.createFont("/usr/share/fonts/truetype/tlwg/Garuda-BoldOblique.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            final Font headerFont = new Font(bf2);
            final Font contentFont = new Font(bf);

            Paragraph ownerName = new Paragraph("For: " + OWNER, headerFont);
            Paragraph ownerEmail = new Paragraph("Email: " + EMAIL, headerFont);

            document.add(ownerName);
            document.add(ownerEmail);

            Image img = Image.getInstance(path.toAbsolutePath().toString());
            img.scaleAbsolute(400, 40);
            document.add(img);

            PdfPCell cellName = new PdfPCell((new Paragraph("Name", headerFont)));
            PdfPCell cellPrice = new PdfPCell((new Paragraph("Price", headerFont)));
            PdfPCell cellAmount = new PdfPCell((new Paragraph("Amount", headerFont)));

            PdfPTable pdfPTable = new PdfPTable(3);
            pdfPTable.addCell(cellName);
            pdfPTable.addCell(cellPrice);
            pdfPTable.addCell(cellAmount);

            PRODUCTS.forEach(product -> {
                PdfPCell productCellName = new PdfPCell(
                        new Paragraph(product.getName(), contentFont));
                pdfPTable.addCell(productCellName);

                PdfPCell productCellPrice = new PdfPCell(
                        new Paragraph(String.valueOf(product.getPrice()), contentFont));
                pdfPTable.addCell(productCellPrice);

                PdfPCell productCellAmount = new PdfPCell(
                        new Paragraph(String.valueOf(product.getAmount()), contentFont));
                pdfPTable.addCell(productCellAmount);
            });

            document.add(pdfPTable);
            document.close();

        } catch (DocumentException | IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}

