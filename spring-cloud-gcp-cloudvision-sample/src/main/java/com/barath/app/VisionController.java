package com.barath.app;


import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.ImageAnnotatorClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;

@RestController
public class VisionController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CloudVisionTemplate cloudVisionTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public VisionController(CloudVisionTemplate cloudVisionTemplate) {
        this.cloudVisionTemplate = cloudVisionTemplate;
    }


    @PostMapping("/extractLabels")
    public  List<String> extractLabels(@RequestBody @NotNull  MultipartFile file) throws Exception {

        logger.info("extractLables is called with file name {}",file.getOriginalFilename());
        AnnotateImageResponse response = this.cloudVisionTemplate.analyzeImage(
               file.getResource(), Type.DOCUMENT_TEXT_DETECTION);

       List<String> textLabels =
                response.getTextAnnotationsList()
                        .stream()
                        .map(EntityAnnotation::getDescription).collect(Collectors.toList());


        logger.info("annotations "+textLabels);

        return textLabels;
    }



}