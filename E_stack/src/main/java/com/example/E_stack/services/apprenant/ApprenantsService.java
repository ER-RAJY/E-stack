package com.example.E_stack.services.apprenant;


import com.example.E_stack.dtos.ApprenantDTO;

import java.util.List;
import java.util.Optional;

public interface ApprenantsService {


    ApprenantDTO createApprenant(ApprenantDTO apprenantDTO);

    ApprenantDTO getApprenantById(Long id);

    List<ApprenantDTO> getAllApprenants();

    ApprenantDTO updateApprenant(Long id, ApprenantDTO apprenantDTO);

    void deleteApprenant(Long id);

    Optional<ApprenantDTO> findById(Long id);
//
//    List<ApprenantDTO> findAllByClasseId(Long classId);
//
//    Optional<ApprenantDTO> findById(Long id);
}