package it.aleph.omega.qrcode;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import it.aleph.omega.model.Book;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Data
@Component
public class QrCodeBuilder {

    public byte[] produceQRCode(Book book) throws WriterException, IOException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode("www.google.it", BarcodeFormat.QR_CODE, 300, 300);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
