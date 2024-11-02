package jans.repairform.service;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import jans.repairform.model.QrCode;
import jans.repairform.repository.QrCodeRepository;

@Service
public class QrCodeService {


    @Value("${qr.code.image.path}")
    private String qrCodeImagePath;
    
    @Autowired QrCodeRepository qrcodeRepo;

    public String generateQRCode(String url) throws Exception {
        String fileName = "QRCode_" + System.currentTimeMillis() + ".png";
        String filePath = qrCodeImagePath + fileName;

        Path directoryPath = Paths.get(qrCodeImagePath);
        if (!directoryPath.toFile().exists()) {
            directoryPath.toFile().mkdirs();  // Create directory including parent directories
        }
        BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 250, 250);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(matrix, "PNG", path);

        // Save QRCode info in database
        QrCode qrCode = new QrCode();
        qrCode.setQrcodeURL(url);
        qrCode.setQrcodePath(filePath);
        qrcodeRepo.save(qrCode);

        return filePath;
    }
    
    public QrCode generateQRCodePDF(String url) throws Exception {
        String fileName = "QRCode_" + System.currentTimeMillis() + ".png";
        String filePath = qrCodeImagePath + fileName;

        // Create QR code directory if not exists
        Path directoryPath = Paths.get(qrCodeImagePath);
        if (!directoryPath.toFile().exists()) {
            directoryPath.toFile().mkdirs();
        }

        // Generate QR code
        BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 250, 250);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(matrix, "PNG", path);

        // Save to database
        QrCode qrCode = new QrCode();
        qrCode.setQrcodeURL(url);
        qrCode.setQrcodePath(filePath);
        return qrcodeRepo.save(qrCode);
    }
}
