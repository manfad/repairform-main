package jans.repairform.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jans.repairform.model.QrCode;
import jans.repairform.model.Response;
import jans.repairform.repository.QrCodeRepository;
import jans.repairform.service.QrCodeService;

@Controller
@RequestMapping("/qrcode")
public class QrCodeController {

    @Autowired QrCodeService qrCodeService;
    @Autowired QrCodeRepository qrCodeRepo;

   
    @PostMapping("generate")
    public @ResponseBody Response generateQRCode(HttpServletRequest request, Model model) {
        Response res = new Response();
        try {
            // Get the current page URL
            String currentUrl = request.getHeader("Referer");

            // Generate QR code for the current page URL
            qrCodeService.generateQRCode(currentUrl);

            res.setMessage("QRCODE generated success");
            res.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage("QRCODE generated failed");
        }
        return res;
    }
    @GetMapping("/qr/{id}")
    public String redirectToOriginalUrl(@PathVariable Long id, Model model) {
        Optional<QrCode> qrCode = qrCodeRepo.findById(id);
        if (qrCode.isPresent()) {
            // Redirect to the original URL stored in the QR code
            return "redirect:" + qrCode.get().getQrcodeURL();
        } else {
            // If the QR code does not exist, show an error message
            model.addAttribute("error", "Invalid QR Code.");
            return "error";
        }
    }
}