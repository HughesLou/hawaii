package com.hughes.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

/**
 * @author hugheslou
 * Created on 2018/9/16.
 */
public class QrCode {

    private static final Logger LOGGER = LoggerFactory.getLogger(QrCode.class);

    public static void main(String[] args) {
        String fileName = args[0];
        try {
            BufferedImage image = ImageIO.read(new File(fileName));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            HashMap<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
            MultiFormatReader formatReader = new MultiFormatReader();
            Result result = formatReader.decode(binaryBitmap, hints);
            LOGGER.info("file={} has QR_code text={}", fileName, result.toString());
        } catch (Exception e) {
            LOGGER.info("file={} has no QR_Code", fileName);
        }
    }
}