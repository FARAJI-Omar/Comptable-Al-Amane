package com.comptablealamanapi.controller;

import com.comptablealamanapi.dto.response.DocumentResponse;
import com.comptablealamanapi.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/societe")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SOCIETE')")
public class SocieteController {
    private final DocumentService documentService;

    @GetMapping("/documents/{SocieteId}/{exerciseComptable}")
    public ResponseEntity<List<DocumentResponse>> getDocumentsBySocieteByYear(@PathVariable Long SocieteId,
                                                                        @PathVariable int exerciseComptable) {
        List<DocumentResponse> documents = documentService.getDocumentsBySocieteByYear(SocieteId, exerciseComptable);
        return ResponseEntity.ok(documents);
    }
}
