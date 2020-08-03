package com.hughes.google.cloud;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Block;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageContext;
import com.google.cloud.vision.v1.Page;
import com.google.cloud.vision.v1.Paragraph;
import com.google.cloud.vision.v1.Symbol;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.cloud.vision.v1.Word;
import com.google.protobuf.ByteString;

/**
 * @author hugheslou
 * Created on 2018/9/18.
 */
public class HindiImageDetect {

    private static final Logger LOGGER = LoggerFactory.getLogger(HindiImageDetect.class);

    public static void main(String[] args) throws Exception {
        String inputFolder = args[0];
        String outputFile = args[1];

        Stream.of(new File(inputFolder).listFiles());

        HindiImageDetect hindiImageDetect = new HindiImageDetect();
        String fileName = "hindi-1.jpg";
        File file = new File(
                hindiImageDetect.getClass().getClassLoader().getResource(fileName).getFile());
        Image image = hindiImageDetect.getImage(file);
        String ocrText = hindiImageDetect.getOcrText(image);
        String text = hindiImageDetect.translate(ocrText);
        LOGGER.info("done");
        System.exit(0);
    }

    private Image getImage(File file) throws Exception {
        // Reads the image file into memory
        ByteString imgBytes = ByteString.copyFrom(Files.readAllBytes(file.toPath()));
        return Image.newBuilder().setContent(imgBytes).build();
    }

    public String getOcrText(Image img) throws Exception {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
        // Set the parameters for the image
        ImageContext imageContext = ImageContext.newBuilder().addLanguageHints("hi-t-i0-handwrit")
                .build();

        AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat)
                .setImage(img).setImageContext(imageContext).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            List<AnnotateImageResponse> responses = client.batchAnnotateImages(requests)
                    .getResponsesList();

            for (AnnotateImageResponse response : responses) {
                if (response.hasError()) {
                    LOGGER.error("error {}", response.getError().getMessage());
                    continue;
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                TextAnnotation annotation = response.getFullTextAnnotation();
                for (Page page : annotation.getPagesList()) {
                    String pageText = "";
                    for (Block block : page.getBlocksList()) {
                        String blockText = "";
                        for (Paragraph para : block.getParagraphsList()) {
                            String paraText = "";
                            for (Word word : para.getWordsList()) {
                                String wordText = "";
                                for (Symbol symbol : word.getSymbolsList()) {
                                    wordText += symbol.getText();
                                }
                                LOGGER.debug("word text={}, confidence={}", wordText,
                                        word.getConfidence());
                                paraText = String.format("%s %s", paraText, wordText);
                            }
                            LOGGER.debug("paragraph={}, confidence={}", paraText,
                                    para.getConfidence());
                            blockText += paraText;
                        }
                        LOGGER.debug("block text={}", blockText);
                        pageText += (blockText + "\n");
                    }
                    LOGGER.debug("page text={}", pageText);
                }
                LOGGER.debug("get text={}", annotation.getText());
            }
            return responses.stream().filter(response -> !response.hasError())
                    .map(AnnotateImageResponse::getFullTextAnnotation).map(TextAnnotation::getText)
                    .collect(Collectors.joining("\n"));
        }
    }

    private String translate(String input) {
        String result = "";
        if (StringUtils.isNotBlank(input)) {
            Translate translate = TranslateOptions.getDefaultInstance().getService();

            // Translates some text into Chinese
            Translation translation = translate.translate(input,
                    TranslateOption.sourceLanguage("hi"), TranslateOption.targetLanguage("zh-CN"));

            result = translation.getTranslatedText();
        }
        LOGGER.info("text={} translated to {}", input, result);
        return result;
    }
}