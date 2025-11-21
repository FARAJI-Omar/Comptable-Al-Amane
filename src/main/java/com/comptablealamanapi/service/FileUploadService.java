package com.comptablealamanapi.service;

import com.comptablealamanapi.dto.request.DocumentRequest;
import com.comptablealamanapi.dto.response.DocumentResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    DocumentResponse uploadDocument(MultipartFile file, DocumentRequest request, Authentication authentication) throws IOException;
    String getDocumentFilePath(String numeroPiece);
}
