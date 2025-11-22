package com.comptablealamanapi.service.impl;

import com.comptablealamanapi.config.UserPrincipal;
import com.comptablealamanapi.dto.request.DocumentRequest;
import com.comptablealamanapi.dto.response.DocumentResponse;
import com.comptablealamanapi.entity.Document;
import com.comptablealamanapi.entity.Societe;
import com.comptablealamanapi.enums.StatutDocument;
import com.comptablealamanapi.exception.FileSizeExceededException;
import com.comptablealamanapi.exception.InvalidFileTypeException;
import com.comptablealamanapi.mapper.DocumentMapper;
import com.comptablealamanapi.repository.DocumentRepository;
import com.comptablealamanapi.service.FileUploadService;
import jakarta.annotation.PreDestroy;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private final String baseUploadPath;
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    public FileUploadServiceImpl(
        @Value("${app.upload.dir:uploads}") String uploadDir,
        DocumentRepository documentRepository,
        DocumentMapper documentMapper
    ) {
        this.baseUploadPath = uploadDir;
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
    }



    public DocumentResponse uploadDocument(MultipartFile file, DocumentRequest request, Authentication authentication) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }
        
        // Validate file type
        String contentType = file.getContentType();
        if (contentType == null || (!contentType.equals("application/pdf") && 
            !contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
            throw new InvalidFileTypeException("Invalid file type. Only PDF, JPG, PNG allowed");
        }
        
        // Validate file size (10MB)
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new FileSizeExceededException("File size exceeds 10MB limit");
        }

        Path uploadRoot = Paths.get(System.getProperty("user.dir"), baseUploadPath);
        Files.createDirectories(uploadRoot);
    
        String originalName = Paths.get(file.getOriginalFilename()).getFileName().toString();
        String fileExtension = "";
        int lastDot = originalName.lastIndexOf('.');
        if (lastDot > 0) {
            fileExtension = originalName.substring(lastDot);
        }
        
        String safeName = UUID.randomUUID().toString() + fileExtension;
    
        Path target = uploadRoot.resolve(safeName);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
    
        Document document = new Document();
        document.setNumeroPiece(request.getNumeroPiece());
        document.setType(request.getType());
        document.setDatePiece(request.getDatePiece());
        document.setMontant(request.getMontant());
        document.setFournisseur(request.getFournisseur());
        document.setFilename(safeName);
        document.setFichierPiece(safeName);
        document.setStatut(StatutDocument.EN_ATTENTE);
        
        // Set societe from logged user
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal.getSocieteId() != null) {
            Societe societe = new Societe();
            societe.setId(userPrincipal.getSocieteId());
            document.setSociete(societe);
        }
        
        Document saved = documentRepository.save(document);
        return documentMapper.toResponse(saved);
    }
    
    public String getDocumentFilePath(String numeroPiece) {
        DocumentResponse document = documentMapper.toResponse(documentRepository.findByNumeroPiece(numeroPiece));
        return document != null ? document.getFichierPiece() : null;
    }
    
    @PreDestroy
    public void cleanup() {
        try {
            Path uploadRoot = Paths.get(System.getProperty("user.dir"), baseUploadPath);
            if (Files.exists(uploadRoot)) {
                try (Stream<Path> files = Files.walk(uploadRoot)) {
                    files.filter(Files::isRegularFile).forEach(file -> {
                        try {
                            Files.delete(file);
                        } catch (IOException e) {
                            System.err.println("Failed to delete: " + file);
                        }
                    });
                }
            }
        } catch (IOException e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }
}