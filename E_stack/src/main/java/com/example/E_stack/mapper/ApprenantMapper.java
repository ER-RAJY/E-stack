package com.example.E_stack.mapper;


import com.example.E_stack.dtos.ApprenantDTO;
import com.example.E_stack.entities.Apprenant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApprenantMapper {

    ApprenantMapper INSTANCE = Mappers.getMapper(ApprenantMapper.class);


    ApprenantDTO toDto(Apprenant apprenant);


    Apprenant toEntity(ApprenantDTO apprenantDTO);

//    ApprenantDTO toDto(Apprenant apprenant);
//    Apprenant toEntity(ApprenantDTO apprenantDTO);
}