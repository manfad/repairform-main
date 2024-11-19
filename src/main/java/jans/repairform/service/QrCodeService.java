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
    
    @Autowired QrCodeRepository qrcodeRepo;

      public QrCode qrGenerate(String url) throws Exception {
        // Check if QR code already exists for this URL
        QrCode existingQrCode = qrcodeRepo.findByQrcodeURL(url);
        if (existingQrCode != null) {
            return existingQrCode;
        }
        
        // Generate new QR code if it doesn't exist
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 250, 250);
        
        // Create directory if it doesn't exist
        File directory = new File(qrCodeImagePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // Generate unique filename
        String fileName = "qr_" + System.currentTimeMillis() + ".png";
        String filePath = qrCodeImagePath + File.separator + fileName;
        
        // Save QR code image to file
        Path path = Paths.get(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        
        // Save to database
        QrCode qrCode = new QrCode();
        qrCode.setQrcodeURL(url);
        qrCode.setQrcodePath(fileName);
        
        return qrcodeRepo.save(qrCode);
    }
}
