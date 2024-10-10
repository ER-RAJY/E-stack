package com.example.E_stack.services.apprenant;


import com.example.E_stack.dtos.ApprenantDTO;
import com.example.E_stack.dtos.ScoreDto;

import java.util.List;
import java.util.Optional;

public interface ApprenantsService {


    ApprenantDTO createApprenant(ApprenantDTO apprenantDTO);

    ApprenantDTO getApprenantById(Long id);

    List<ApprenantDTO> getAllApprenants();

    ApprenantDTO updateApprenant(Long id, ApprenantDTO apprenantDTO);

    void deleteApprenant(Long id);

    Optional<ApprenantDTO> findById(Long id);
    ScoreDto getScoreForApprenant(Long  apprenantId);
}