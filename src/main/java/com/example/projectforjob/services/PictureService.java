package com.example.projectforjob.services;

import com.example.projectforjob.models.Person;
import com.example.projectforjob.models.Picture;
import com.example.projectforjob.repositories.PictureRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PictureService {

    private final PictureRepositories pictureRepositories;

    @Autowired
    public PictureService(PictureRepositories pictureRepositories) {

        this.pictureRepositories = pictureRepositories;
    }

    public Picture findById(int id) {

        Optional<Picture> picture = pictureRepositories.findByPerson_Id(id);

        return picture.orElse(null);
    }

    public void savePicture(Person person, Picture picture, MultipartFile file) throws IOException {

        if (picture == null || picture.getSize() == null || picture.getSize() == 0) {
            Picture picture1 = new Picture();
            picture1.setPerson(person);
            person.setPictures(Collections.singletonList(picture1));
            picture1.setName(file.getName());
            picture1.setOriginalFileName(file.getOriginalFilename());
            picture1.setSize(file.getSize());
            picture1.setContentType(file.getContentType());
            picture1.setBytes(file.getBytes());
            person.setPictures(Collections.singletonList(picture1));
            pictureRepositories.save(picture1);

        } else {
            person.setPictures(Collections.singletonList(picture));
            picture.setPerson(person);
            picture.setName(file.getName());
            picture.setOriginalFileName(file.getOriginalFilename());
            picture.setSize(file.getSize());
            picture.setContentType(file.getContentType());
            picture.setBytes(file.getBytes());
            person.setPictures(Collections.singletonList(picture));
            pictureRepositories.save(picture);
        }
    }
}
