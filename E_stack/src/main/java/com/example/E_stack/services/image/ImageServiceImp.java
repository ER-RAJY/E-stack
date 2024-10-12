package com.example.E_stack.services.image;

import com.example.E_stack.entities.Answer;
import com.example.E_stack.entities.Image;


import com.example.E_stack.reposeitories.AnswerRepository;
import com.example.E_stack.reposeitories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImp implements ImageService {

    private final ImageRepository imageRepository; // Use constructor injection for better practice
    private final AnswerRepository answerRepository;

    @Override
    public void storeFile(MultipartFile multipartFile, Long answerId) throws IOException {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        if (optionalAnswer.isPresent()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            Image image = new Image();
            image.setName(fileName);
            image.setAnswer(optionalAnswer.get());
            image.setType(multipartFile.getContentType());
            image.setData(multipartFile.getBytes());
            imageRepository.save(image);
        } else {
            throw new IOException("Answer not found");
        }
    }
}