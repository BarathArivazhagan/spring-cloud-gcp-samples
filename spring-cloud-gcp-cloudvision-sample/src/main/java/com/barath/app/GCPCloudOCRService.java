package com.barath.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GCPCloudOCRService implements CloudOCRService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CloudVisionTemplate cloudVisionTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public GCPCloudOCRService(CloudVisionTemplate cloudVisionTemplate) {
        this.cloudVisionTemplate = cloudVisionTemplate;
    }


    @Override
    public List<String> extractTextLabels(MultipartFile file, Feature.Type type) {

        if(logger.isInfoEnabled()) {
            logger.info(" gcp service to analyze image with type detection {}",type);
        }
        AnnotateImageResponse response = this.cloudVisionTemplate.analyzeImage(
                file.getResource(), type);

        List<String> textLabels =
                response.getTextAnnotationsList()
                        .stream()
                        .map(EntityAnnotation::getDescription).collect(Collectors.toList());


        logger.info("annotations {}",textLabels);
        return textLabels;
    }

    @Override
    public List<String> extractLabels(MultipartFile file) {


        if(logger.isInfoEnabled()) {
            logger.info(" gcp service to analyze image with label type detection");
        }
        AnnotateImageResponse response = this.cloudVisionTemplate.analyzeImage(
                file.getResource(), Feature.Type.LABEL_DETECTION);

        List<String> labels =
                response.getLabelAnnotationsList()
                        .stream()
                        .map(EntityAnnotation::getDescription).collect(Collectors.toList());


        logger.info("labels {}",labels);
        return labels;

    }
}
