package com.example.Backend.util;

import com.itextpdf.text.*;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

    
import java.io.File;
import java.io.FileOutputStream;
    

public class pdfGenerator {
    
        public static void generatePdf(String summary, String education, String experience, String awards,
                                String project, String name, String contact, String email, String address) {
    
            try {
                // 1. Create directory if it doesn't exist
                File folder = new File("Resume");
                if (!folder.exists()) folder.mkdir();
    
                // 2. Set file path
                String filePath = "Resume/" + name.replaceAll(" ", "_") + "_Resume.pdf";
    
                // 3. Create Document
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();
    
                // 4. Add Content
                Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
                Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
    
                document.add(new Paragraph(name, headingFont));
                document.add(new Paragraph(contact + " | " + email + " | " + address, normalFont));
                document.add(new Paragraph("\n"));
    
                document.add(new Paragraph("Professional Summary", headingFont));
                document.add(new Paragraph(summary, normalFont));
                document.add(new Paragraph("\n"));
    
                document.add(new Paragraph("Education", headingFont));
                document.add(new Paragraph(education, normalFont));
                document.add(new Paragraph("\n"));
    
                document.add(new Paragraph("Experience", headingFont));
                document.add(new Paragraph(experience, normalFont));
                document.add(new Paragraph("\n"));
    
                document.add(new Paragraph("Awards", headingFont));
                document.add(new Paragraph(awards, normalFont));
                document.add(new Paragraph("\n"));
    
                document.add(new Paragraph("Projects", headingFont));
                document.add(new Paragraph(project, normalFont));
    
                // 5. Close document
                document.close();
    
                System.out.println("✅ Resume PDF generated: " + filePath);
    
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
