package com.example.E_stack.services.apprenant;

import com.example.E_stack.dtos.ApprenantDTO;
import com.example.E_stack.dtos.ScoreDto;
import com.example.E_stack.entities.Achievement;
import com.example.E_stack.entities.Apprenant;
import com.example.E_stack.enums.Role;
import com.example.E_stack.mapper.ApprenantMapper;
import com.example.E_stack.reposeitories.ApprenantRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for managing learners (Apprenants).
 */
@Service
public class ApprenantsServiceImpl implements ApprenantsService {

    private final ApprenantRepository apprenantRepository;
    private final ApprenantMapper apprenantMapper;
    private final PasswordEncoder passwordEncoder;

    public ApprenantsServiceImpl(ApprenantRepository apprenantRepository, ApprenantMapper apprenantMapper, PasswordEncoder passwordEncoder) {
        this.apprenantRepository = apprenantRepository;
        this.apprenantMapper = apprenantMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a new learner (Apprenant).
     *
     * @param apprenantDTO the DTO of the learner to create
     * @return the created learner DTO
     */
    @Override
    public ApprenantDTO createApprenant(ApprenantDTO apprenantDTO) {
        Apprenant apprenant = apprenantMapper.toEntity(apprenantDTO);
        apprenant.setRole(Role.APPRENANT); // Set role to APPRENANT
        return apprenantMapper.toDto(apprenantRepository.save(apprenant)); // Save and return DTO
    }

    /**
     * Retrieves a learner (Apprenant) by ID.
     *
     * @param id the ID of the learner to retrieve
     * @return the learner DTO
     */
    @Override
    public ApprenantDTO getApprenantById(Long id) {
        Apprenant apprenant = apprenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apprenant not found with id: " + id));
        return apprenantMapper.toDto(apprenant);
    }

    /**
     * Retrieves all learners (Apprenants).
     *
     * @return a list of learner DTOs
     */
    @Override
    public List<ApprenantDTO> getAllApprenants() {
        return apprenantRepository.findAll()
                .stream()
                .map(apprenantMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing learner (Apprenant).
     *
     * @param id the ID of the learner to update
     * @param apprenantDTO the updated learner DTO
     * @return the updated learner DTO
     */
    @Override
    public ApprenantDTO updateApprenant(Long id, ApprenantDTO apprenantDTO) {
        Apprenant existingApprenant = apprenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apprenant not found with id: " + id));
        Apprenant apprenant = apprenantMapper.toEntity(apprenantDTO);
        apprenant.setPassword(passwordEncoder.encode(apprenantDTO.getPassword()));
        apprenant.setId(existingApprenant.getId()); // Maintain existing ID
        return apprenantMapper.toDto(apprenantRepository.save(apprenant));
    }
    /**
     * Counts the total number of learners (Apprenants).
     *
     * @return the total number of learners
     */
    @Override
    public long countApprenants() {
        return apprenantRepository.count();
    }


    /**
     * Deletes a learner (Apprenant) by ID.
     *
     * @param id the ID of the learner to delete
     */
    @Override
    public void deleteApprenant(Long id) {
        if (!apprenantRepository.existsById(id)) {
            throw new RuntimeException("Apprenant not found with id: " + id);
        }
        apprenantRepository.deleteById(id);
    }

    /**
     * Retrieves the score for a learner (Apprenant) by ID.
     *
     * @param apprenantId the ID of the learner
     * @return the score DTO for the learner
     */
    @Override
    public ScoreDto getScoreForApprenant(Long apprenantId) {
        Apprenant apprenant = apprenantRepository.findById(apprenantId)
                .orElseThrow(() -> new RuntimeException("Apprenant not found with ID: " + apprenantId));

        // Calculate total points from achievements
        int totalPoints = apprenant.getAchievements().stream()
                .mapToInt(Achievement::getPoints)
                .sum();

        ScoreDto scoreDto = new ScoreDto();
        scoreDto.setApprenantId(apprenantId);
        scoreDto.setTotalPoints(totalPoints);

        return scoreDto;
    }

    /**
     * Finds a learner (Apprenant) by ID, returning an Optional DTO.
     *
     * @param id the ID of the learner
     * @return an Optional containing the learner DTO, or empty if not found
     */
    @Override
    public Optional<ApprenantDTO> findById(Long id) {
        return apprenantRepository.findById(id)
                .map(apprenantMapper::toDto);
    }
}
