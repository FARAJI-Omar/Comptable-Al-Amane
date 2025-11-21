package com.comptablealamanapi.service.impl;

import com.comptablealamanapi.dto.response.DocumentResponse;
import com.comptablealamanapi.entity.Document;
import com.comptablealamanapi.enums.StatutDocument;
import com.comptablealamanapi.exception.ResourceNotFoundException;
import com.comptablealamanapi.mapper.DocumentMapper;
import com.comptablealamanapi.repository.DocumentRepository;
import com.comptablealamanapi.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    
    @Override
    public List<DocumentResponse> getDocumentsBySocieteByYear(Long societeId, int exerciseComptable) {
        return documentMapper.toResponseList(documentRepository.societeDocumentsbyYear(societeId, exerciseComptable));
    }
    
    @Override
    public List<DocumentResponse> getPendingDocuments() {
        return documentMapper.toResponseList(documentRepository.findByStatutOrderByCreatedAtDesc(StatutDocument.EN_ATTENTE));
    }
    
    @Override
    public List<DocumentResponse> getPendingDocumentsBySociete(Long societeId) {
        return documentMapper.toResponseList(documentRepository.findBySocieteIdAndStatutOrderByCreatedAtDesc(societeId, StatutDocument.EN_ATTENTE));
    }
    
    @Override
    public DocumentResponse validateDocument(Long documentId, String commentaire) {
        Document document = documentRepository.findById(documentId)
            .orElseThrow(() -> new ResourceNotFoundException("Document non trouvé"));
        document.setStatut(StatutDocument.VALIDE);
        document.setCommentaireComptable(commentaire);
        document.setDateValidation(LocalDateTime.now());
        return documentMapper.toResponse(documentRepository.save(document));
    }
    
    @Override
    public DocumentResponse rejectDocument(Long documentId, String motif) {
        Document document = documentRepository.findById(documentId)
            .orElseThrow(() -> new ResourceNotFoundException("Document non trouvé"));
        document.setStatut(StatutDocument.REJETE);
        document.setCommentaireComptable(motif);
        document.setDateValidation(LocalDateTime.now());
        return documentMapper.toResponse(documentRepository.save(document));
    }
}
