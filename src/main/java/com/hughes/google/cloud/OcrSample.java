package com.hughes.google.cloud;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Block;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageContext;
import com.google.cloud.vision.v1.ImageSource;
import com.google.cloud.vision.v1.Page;
import com.google.cloud.vision.v1.Paragraph;
import com.google.cloud.vision.v1.Symbol;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.cloud.vision.v1.Word;
import com.google.protobuf.ByteString;

/**
 * @author hugheslou
 * Created on 2018/9/17.
 */
public class OcrSample {

    public static void main(String... args) throws Exception {
        OcrSample ocrSample = new OcrSample();
        String fileName = "hindi.jpg";
        File file = new File(ocrSample.getClass().getClassLoader().getResource(fileName).getFile());
//        ocrSample.process(file);
        System.out.println("next...");

        URL url = new URL("http://www.yinduabc.com/uploads/allimg/161021/113I110E-0.jpg");
        ocrSample.detectHandwrittenOcr(url.openStream(), System.out);
    }

    private void process(File file) {
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

            // Reads the image file into memory
            ByteString imgBytes = ByteString.copyFrom(Files.readAllBytes(file.toPath()));

            // Builds the image annotation request
            List<AnnotateImageRequest> requests = new ArrayList<>();
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat)
                    .setImage(img).build();
            requests.add(request);

            // Performs label detection on the image file
            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.printf("Error: %s\n", res.getError().getMessage());
                    return;
                }

                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    annotation.getAllFields()
                            .forEach((k, v) -> System.out.printf("%s : %s\n", k, v.toString()));
                }
            }
        } catch (Exception e) {
            System.out.println("exception " + e);
        }
    }

    /**
     * Performs handwritten text detection on a local image file.
     *
     * @param inputStream The {@link InputStream} to image to detect handwritten text on.
     * @param out A {@link PrintStream} to write the results to.
     * @throws Exception on errors while closing the client.
     * @throws IOException on Input/Output errors.
     */
    public void detectHandwrittenOcr(InputStream inputStream, PrintStream out) throws Exception {
        detectHandwrittenOcr(
                Image.newBuilder().setContent(ByteString.readFrom(inputStream)).build(), out);
    }

    /**
     * Performs handwritten text detection on a remote image on Google Cloud Storage.
     *
     * @param gcsPath The path to the remote file on Google Cloud Storage to detect handwritten text
     *     on.
     * @param out A {@link PrintStream} to write the results to.
     * @throws Exception on errors while closing the client.
     * @throws IOException on Input/Output errors.
     */
    public void detectHandwrittenOcrGcs(String gcsPath, PrintStream out) throws Exception {
        detectHandwrittenOcr(Image.newBuilder()
                .setSource(ImageSource.newBuilder().setGcsImageUri(gcsPath).build()).build(), out);
    }

    public void detectHandwrittenOcr(Image img, PrintStream out) throws Exception {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
        // Set the parameters for the image
        ImageContext imageContext = ImageContext.newBuilder().addLanguageHints("hi-t-i0-handwrit")
                .build();

        AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat)
                .setImage(img).setImageContext(imageContext).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    out.printf("Error: %s\n", res.getError().getMessage());
                    return;
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                TextAnnotation annotation = res.getFullTextAnnotation();
                for (Page page : annotation.getPagesList()) {
                    String pageText = "";
                    for (Block block : page.getBlocksList()) {
                        String blockText = "";
                        for (Paragraph para : block.getParagraphsList()) {
                            String paraText = "";
                            for (Word word : para.getWordsList()) {
                                String wordText = "";
                                for (Symbol symbol : word.getSymbolsList()) {
                                    wordText = wordText + symbol.getText();
                                    out.format("Symbol text: %s (confidence: %f)\n",
                                            symbol.getText(), symbol.getConfidence());
                                }
                                out.format("Word text: %s (confidence: %f)\n\n", wordText,
                                        word.getConfidence());
                                paraText = String.format("%s %s", paraText, wordText);
                            }
                            // Output Example using Paragraph:
                            out.println("\nParagraph: \n" + paraText);
                            out.format("Paragraph Confidence: %f\n", para.getConfidence());
                            blockText = blockText + paraText;
                        }
                        pageText = pageText + blockText;
                    }
                }
                out.println("\nComplete annotation:");
                out.println(annotation.getText());
            }
        }
    }
}