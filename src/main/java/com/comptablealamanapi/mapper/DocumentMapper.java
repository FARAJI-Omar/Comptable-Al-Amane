package com.comptablealamanapi.mapper;

import com.comptablealamanapi.dto.response.DocumentResponse;
import com.comptablealamanapi.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    
    @Mapping(source = "societe.id", target = "societeId")
    DocumentResponse toResponse(Document document);
    List<DocumentResponse> toResponseList(List<Document> documents);
}