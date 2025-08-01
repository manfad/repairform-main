package jans.repairform.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jans.repairform.model.QrCode;
import jans.repairform.repository.QrCodeRepository;
import jans.repairform.service.QrCodeService;

@Controller
@RequestMapping("/qrcode")
public class QrCodeController {

    @Autowired QrCodeService qrCodeService;
    @Autowired QrCodeRepository qrCodeRepo;
    @Value("${qr.code.image.path}")
    private String qrCodeImagePath;
   
    @PostMapping("/generate-pdf-qr")
    public @ResponseBody Map<String, Object> generatePdfQRCode(
            @RequestParam String formid, 
            HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Create base URL for PDF
            String baseUrl = request.getScheme() + "://" + 
                          request.getServerName() + ":" + 
                          request.getServerPort() + 
                          "/repairform/repairform/pdf?formid=";
            
            QrCode qrCode = qrCodeService.qrGenerate(formid, baseUrl);
            
            response.put("success", true);
            response.put("qrCodeId", qrCode.getId());
            
        } catch (Exception ex) {
            ex.printStackTrace();
            response.put("success", false);
            response.put("error", "Failed to generate QR code");
        }
        return response;
    }
    
    @GetMapping("/view/pdf/{id}")
    public String showPdfQRCode(@PathVariable Long id, Model model) {
        QrCode qrCode = qrCodeRepo.findById(id)
                .orElse(null);
        
        if (qrCode != null) {
            model.addAttribute("qrCodeImage", "/qrcode/image/" + qrCode.getId());
            model.addAttribute("title", "Scan to view PDF");
        } else {
            model.addAttribute("error", "QR Code not found");
        }
        
        return "qrcodeView";
    }
    
    @GetMapping("/view/page/{id}")
    public String showPageQRCode(@PathVariable Long id, Model model) {
        QrCode qrCode = qrCodeRepo.findById(id)
                .orElse(null);
        
        if (qrCode != null) {
            model.addAttribute("qrCodeImage", "/qrcode/image/" + qrCode.getId());
            model.addAttribute("title", "Scan to visit page");
        } else {
            model.addAttribute("error", "QR Code not found");
        }
        
        return "qrcodeView";
    }
    
    // Common image serving endpoint
    @GetMapping("/image/{id}")
    public void getQRCodeImage(@PathVariable Long id, HttpServletResponse response) 
            throws IOException {
        try {
            QrCode qrCode = qrCodeRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("QR Code not found"));
            
            Path imagePath = Paths.get(qrCode.getQrcodePath());
            System.out.println("Attempting to read QR code from: " + imagePath); // Debug log
            
            if (!Files.exists(imagePath)) {
                System.out.println("File not found at path: " + imagePath); // Debug log
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "QR Code image file not found");
                return;
            }

            byte[] imageBytes = Files.readAllBytes(imagePath);
            response.setContentType("image/png");
            response.setContentLength(imageBytes.length);
            response.setHeader("Cache-Control", "public, max-age=31536000");
            
            try (var outputStream = response.getOutputStream()) {
                outputStream.write(imageBytes);
                outputStream.flush();
            }
            
        } catch (Exception e) {
            System.out.println("Error processing QR code: " + e.getMessage()); // Debug log
            e.printStackTrace(); // Debug log
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing QR code image");
        }
    }

    @PostMapping("/generate-page-qr")
    public @ResponseBody Map<String, Object> generatePageQRCode(
            @RequestParam String pageUrl) {
        Map<String, Object> response = new HashMap<>();
        try {
            QrCode qrCode = qrCodeService.generatePageQrCode(pageUrl);
            
            response.put("success", true);
            response.put("qrCodeId", qrCode.getId());
            
        } catch (Exception ex) {
            ex.printStackTrace();
            response.put("success", false);
            response.put("error", "Failed to generate page QR code");
        }
        return response;
    }
}