package com.example.E_stack.services.image;

import com.example.E_stack.entities.Answer;
import com.example.E_stack.entities.Image;

import com.example.E_stack.exeption.AnswerNotFoundException;
import com.example.E_stack.reposeitories.AnswerRepository;
import com.example.E_stack.reposeitories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger; // Import logger
import org.slf4j.LoggerFactory; // Import logger factory
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImp implements ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImp.class); // Logger initialization

    private final ImageRepository imageRepository; // Use constructor injection for better practice
    private final AnswerRepository answerRepository;


    @Override
    public void storeFile(MultipartFile multipartFile, Long answerId) throws IOException {
        // Validate the file
        validateFile(multipartFile);

        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        if (optionalAnswer.isPresent()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            Image image = new Image();
            image.setName(fileName);
            image.setAnswer(optionalAnswer.get());
            image.setType(multipartFile.getContentType());
            image.setData(multipartFile.getBytes());
            imageRepository.save(image);

            logger.info("Stored file: {} for answer ID: {}", fileName, answerId);
        } else {
            logger.warn("Attempted to store file for non-existing answer ID: {}", answerId);
            throw new AnswerNotFoundException("Answer not found with ID: " + answerId); // Use custom exception
        }
    }

    private void validateFile(MultipartFile file) throws IOException {
        // Check if the file is empty
        if (file.isEmpty()) {
            throw new IOException("Cannot store empty file");
        }
        // Check file type (you can customize this as per your requirements)
        String contentType = file.getContentType();
        if (!contentType.startsWith("image/")) {
            throw new IOException("File type not supported: " + contentType);
        }
        // Check file size (e.g., limit to 5 MB)
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IOException("File size exceeds limit of 5MB");
        }
    }
}
