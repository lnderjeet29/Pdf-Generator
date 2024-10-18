package com.frieghtfox.Generator.controller;

import com.frieghtfox.Generator.model.InvoiceRequest;
import com.frieghtfox.Generator.services.PDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PDFService pdfService;

    @PostMapping("/generate")
    public ResponseEntity<String> generatePdf(@RequestBody InvoiceRequest pdfRequest) {
        try {
            String filePath = pdfService.generatePdf(pdfRequest);
            return ResponseEntity.ok("PDF generated successfully. Download from: /api/pdf/download?file=" + filePath);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to generate PDF: " + e.getMessage());
        }
    }

    // Endpoint to download PDF by file path
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadPdf(@RequestParam String file) {
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
