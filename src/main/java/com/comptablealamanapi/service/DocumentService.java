package com.comptablealamanapi.service;

import com.comptablealamanapi.dto.request.DocumentRequest;
import com.comptablealamanapi.dto.response.DocumentResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {
    List<DocumentResponse> getDocumentsBySocieteByYear(Long societeId, int exerciseComptable);
    
    List<DocumentResponse> getPendingDocuments();
    
    List<DocumentResponse> getPendingDocumentsBySociete(Long societeId);
    
    DocumentResponse validateDocument(Long documentId, String commentaire);
    
    DocumentResponse rejectDocument(Long documentId, String motif);
    
    List<DocumentResponse> getDocumentsBySociete(Long societeId);
}
