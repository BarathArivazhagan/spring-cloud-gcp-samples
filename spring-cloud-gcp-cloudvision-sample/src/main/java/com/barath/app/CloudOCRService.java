package com.barath.app;


import com.google.cloud.vision.v1.Feature;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CloudOCRService {

     List<String> extractTextLabels(MultipartFile file, Feature.Type type);

     default List<String> extractTextLabelsByDocumentType(MultipartFile file){
        return extractTextLabels(file, Feature.Type.DOCUMENT_TEXT_DETECTION);
     }

    default List<String> extractTextLabelsByTextType(MultipartFile file){
        return extractTextLabels(file, Feature.Type.TEXT_DETECTION);
    }

     List<String> extractLabels(MultipartFile file);



}
