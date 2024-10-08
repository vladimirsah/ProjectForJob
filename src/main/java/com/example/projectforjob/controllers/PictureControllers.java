package com.example.projectforjob.controllers;

import com.example.projectforjob.models.Picture;
import com.example.projectforjob.services.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
public class PictureControllers {

    private final PictureService pictureService;

    @Autowired
    public PictureControllers(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @GetMapping("/upload/{id}")
    public ResponseEntity<?> uploadAvatar(@PathVariable int id) {
        Picture picture = pictureService.findById(id);
        Picture def = pictureService.getDefault();
        if (picture == null) {
            return ResponseEntity.ok()
                    .header("file", def.getOriginalFileName())
                    .contentType(MediaType.valueOf(def.getContentType()))
                .contentLength(def.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(def.getBytes())));
        } else {
            return ResponseEntity.ok()
                .header("file", picture.getOriginalFileName())
                .contentType(MediaType.valueOf(picture.getContentType()))
                .contentLength(picture.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(picture.getBytes())));
        }
    }

    @GetMapping("/uploads")
    public ResponseEntity<?> uploads(@RequestParam("url") String url) throws IOException, InterruptedException {

        InputStream inputStream = pictureService.getPicture(url);
        return ResponseEntity.ok()
            .header("file")
            .contentType(MediaType.IMAGE_PNG)
            .body(new InputStreamResource(inputStream));

    }
}
