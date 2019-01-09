package com.barath.app;


import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.ImageAnnotatorClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;

@RestController(value="/gcp")
public class GCPCloudOCRController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CloudOCRService cloudOCRService;


    public GCPCloudOCRController(CloudOCRService cloudOCRService) {
        this.cloudOCRService= cloudOCRService;
    }


    @PostMapping(value="/documentType/extractText")
    public  List<String> extractTextByDocumentType(@RequestBody @NotNull  MultipartFile file) throws Exception {

        logger.info("extract text by document type is invoked with file name {}",file.getOriginalFilename());
        return this.cloudOCRService.extractTextLabelsByDocumentType(file);
    }

    @PostMapping("/textType/extractText")
    public  List<String> extractTextByTextType(@RequestBody @NotNull  MultipartFile file) throws Exception {

        logger.info("extract text by text type is invoked with file name {}",file.getOriginalFilename());
        return this.cloudOCRService.extractTextLabelsByTextType(file);
    }

    @PostMapping("/extractLabels")
    public  List<String> extractLabels(@RequestBody @NotNull  MultipartFile file) throws Exception {

        logger.info("extract labels is invoked with file name {}",file.getOriginalFilename());
        return this.cloudOCRService.extractLabels(file);
    }

    @PostMapping("/extractText/{type}")
    public  List<String> extractLabels(@RequestBody @NotNull  MultipartFile file, @PathVariable String detectionType) throws Exception {

        logger.info("extract text with type {} and file name {}",detectionType,file.getOriginalFilename());
        return this.cloudOCRService.extractTextLabels(file, Feature.Type.valueOf(detectionType));
    }



}