package com.comptablealamanapi.controller;

import com.comptablealamanapi.dto.request.DocumentRequest;
import com.comptablealamanapi.dto.response.DocumentResponse;
import com.comptablealamanapi.enums.TypeDocument;
import com.comptablealamanapi.service.FileUploadService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/document")
public class DocumentController {
    private final FileUploadService fileUploadService;

    @PreAuthorize("hasAuthority('SOCIETE')")
    @PostMapping("/upload")
    public ResponseEntity<DocumentResponse> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute DocumentRequest request,
            Authentication authentication) throws IOException {
        
        DocumentResponse uploadedDocument = fileUploadService.uploadDocument(file, request, authentication);
        if (uploadedDocument == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(uploadedDocument);
    }

    @PreAuthorize("hasAuthority('COMPTABLE')")
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @PreAuthorize("hasAnyAuthority('SOCIETE', 'COMPTABLE')")
    @GetMapping("/file/{numeroPiece}")
    public ResponseEntity<Resource> viewDocumentByNumeroPiece(@PathVariable String numeroPiece) throws Exception {
        String fileName = fileUploadService.getDocumentFilePath(numeroPiece);
        
        if (fileName == null) {
            return ResponseEntity.notFound().build();
        }
        
        Path filePath = Paths.get(System.getProperty("user.dir"), uploadDir, fileName);
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .body(resource);
    }
}