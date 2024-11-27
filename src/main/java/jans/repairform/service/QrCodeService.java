package jans.repairform.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import jans.repairform.model.QrCode;
import jans.repairform.repository.QrCodeRepository;

@Service
public class QrCodeService {

    @Value("${qr.code.image.path}")
    private String qrCodeImagePath;
    
    @Autowired 
    private QrCodeRepository qrcodeRepo;

    public QrCode qrGenerate(String formId, String baseUrl) throws Exception {
        // Create the full URL with the ID
        String url = baseUrl + formId;
        
        // Check if QR code already exists for this form ID
        QrCode existingQrCode = qrcodeRepo.findByQrcodeURL(url);
        if (existingQrCode != null) {
            // Check if the file exists
            Path existingPath = Paths.get(qrCodeImagePath, existingQrCode.getQrcodePath());
            if (existingPath.toFile().exists()) {
                return existingQrCode;
            }
            // If file doesn't exist, we'll regenerate it with the same filename
        }
        
        // Generate new QR code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 250, 250);
        
        // Create directory if it doesn't exist
        File directory = new File(qrCodeImagePath);
        if (!directory.exists()) {
            directory.mkdirs();
            directory.setReadable(true, false);
            directory.setExecutable(true, false);
        }
        
        // Generate filename based on form ID for consistency
        String fileName = "qr_form_" + formId + ".png";
        Path path = Paths.get(qrCodeImagePath, fileName);
        
        // Save QR code image to file
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        
        // Make the file readable by the web server
        File qrFile = path.toFile();
        qrFile.setReadable(true, false);
        
        if (existingQrCode != null) {
            // Update existing record
            existingQrCode.setQrcodePath(fileName);
            return qrcodeRepo.save(existingQrCode);
        } else {
            // Create new record
            QrCode qrCode = new QrCode();
            qrCode.setQrcodeURL(url);
            qrCode.setQrcodePath(fileName);
            return qrcodeRepo.save(qrCode);
        }
    }
}
