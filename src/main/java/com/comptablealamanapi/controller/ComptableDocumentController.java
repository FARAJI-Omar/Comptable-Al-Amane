package com.comptablealamanapi.controller;

import com.comptablealamanapi.dto.response.ApiResponse;
import com.comptablealamanapi.dto.response.DocumentResponse;
import com.comptablealamanapi.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comptable")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('COMPTABLE')")
public class ComptableDocumentController {
    private final DocumentService documentService;
    
    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<DocumentResponse>>> getAllPendingDocuments() {
        return ResponseEntity.ok(ApiResponse.success(documentService.getPendingDocuments(), "Documents en attente récupérés"));
    }
    
    @GetMapping("/pending/societe/{societeId}")
    public ResponseEntity<ApiResponse<List<DocumentResponse>>> getPendingDocumentsBySociete(@PathVariable Long societeId) {
        return ResponseEntity.ok(ApiResponse.success(documentService.getPendingDocumentsBySociete(societeId), "Documents en attente récupérés"));
    }
    
    @PutMapping("/{documentId}/validate")
    public ResponseEntity<ApiResponse<DocumentResponse>> validateDocument(
            @PathVariable Long documentId,
            @RequestBody(required = false) String commentaire) {
        return ResponseEntity.ok(ApiResponse.success(documentService.validateDocument(documentId, commentaire), "Document validé"));
    }
    
    @PutMapping("/{documentId}/reject")
    public ResponseEntity<ApiResponse<DocumentResponse>> rejectDocument(
            @PathVariable Long documentId,
            @RequestBody String motif) {
        return ResponseEntity.ok(ApiResponse.success(documentService.rejectDocument(documentId, motif), "Document rejeté"));
    }
    
    @GetMapping("/documents/societe/{societeId}")
    public ResponseEntity<ApiResponse<List<DocumentResponse>>> getDocumentsBySociete(@PathVariable Long societeId) {
        return ResponseEntity.ok(ApiResponse.success(documentService.getDocumentsBySociete(societeId), "Documents récupérés"));
    }
}
