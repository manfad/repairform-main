package jans.repairform.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import jans.repairform.model.QrCode;
import jans.repairform.repository.QrCodeRepository;

@Service
public class QrCodeService {

    private String qrCodeImagePath = "src/main/resources/qrcodes";
    
    @Autowired 
    private QrCodeRepository qrcodeRepo;

    public QrCode qrGenerate(String formId, String baseUrl) throws Exception {
        // Create the full URL with the ID
        String url = baseUrl + formId;
        
        // Check if QR code already exists for this form ID
        QrCode existingQrCode = qrcodeRepo.findByQrcodeURL(url);
        if (existingQrCode != null) {
            // Check if the file exists using the full path
            File existingFile = new File(existingQrCode.getQrcodePath());
            if (existingFile.exists()) {
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
        String fileName = "qr_" + formId + ".png";
        Path path = Paths.get(qrCodeImagePath, fileName);
        String fullPath = path.toString(); // Get the complete path
        
        // Save QR code image to file
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        
        // Make the file readable by the web server
        File qrFile = path.toFile();
        qrFile.setReadable(true, false);
        
        if (existingQrCode != null) {
            // Update existing record
            existingQrCode.setQrcodePath(fullPath);
            return qrcodeRepo.save(existingQrCode);
        } else {
            // Create new record
            QrCode qrCode = new QrCode();
            qrCode.setQrcodeURL(url);
            qrCode.setQrcodePath(fullPath);
            return qrcodeRepo.save(qrCode);
        }
    }

    public QrCode generatePageQrCode(String pageUrl) throws Exception {
        // First check if QR code already exists for this URL
        QrCode existingQrCode = qrcodeRepo.findByQrcodeURL(pageUrl);
        if (existingQrCode != null) {
            // Check if file exists using the full path
            File existingFile = new File(existingQrCode.getQrcodePath());
            if (existingFile.exists()) {
                return existingQrCode;
            }
        }
        
        // Generate new QR code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(pageUrl, BarcodeFormat.QR_CODE, 250, 250);
        
        // Create directory if it doesn't exist
        File directory = new File(qrCodeImagePath);
        if (!directory.exists()) {
            directory.mkdirs();
            directory.setReadable(true, false);
            directory.setExecutable(true, false);
        }
        
        // Generate filename using URL hash to ensure same URL gets same filename
        String fileName = "qr_" + Math.abs(pageUrl.hashCode()) + ".png";
        Path path = Paths.get(qrCodeImagePath, fileName);
        String fullPath = path.toString(); // Get the complete path
        
        // Save QR code image to file
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        
        // Make the file readable by the web server
        File qrFile = path.toFile();
        qrFile.setReadable(true, false);
        
        if (existingQrCode != null) {
            // Update existing record with new file path
            existingQrCode.setQrcodePath(fullPath);
            return qrcodeRepo.save(existingQrCode);
        } else {
            // Create new record
            QrCode qrCode = new QrCode();
            qrCode.setQrcodeURL(pageUrl);
            qrCode.setQrcodePath(fullPath);
            return qrcodeRepo.save(qrCode);
        }
    }
}
