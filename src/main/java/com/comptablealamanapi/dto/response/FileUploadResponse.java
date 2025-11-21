package com.comptablealamanapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponse {
    private Long id;
    private String filename;
    private String fichierPiece;
    private LocalDateTime createdAt;
}