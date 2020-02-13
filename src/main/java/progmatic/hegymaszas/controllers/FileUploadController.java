package progmatic.hegymaszas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import progmatic.hegymaszas.exceptions.RouteNotFoundException;
import progmatic.hegymaszas.services.FileUploadService;

import java.io.IOException;

@RestController
public class FileUploadController {
    private final FileUploadService fileUploadService;


    @Autowired
    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }


    @GetMapping("/uploadfile/{fileName}")
    public ResponseEntity<byte[]> getUploadedFile(@PathVariable String fileName) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(fileUploadService.loadAsResource(fileName), headers, HttpStatus.OK);
    }


    @PostMapping("/users/image")
    public boolean uploadProfilePicture(
            @RequestBody MultipartFile image) throws IOException {
        fileUploadService.storeProfilePicture(image);
        return true;
    }


    @PostMapping("/route/{routeId}")
    public void uploadPictureForRoute(
            @PathVariable("routeId") long routeId,
            @RequestBody MultipartFile image) throws IOException, RouteNotFoundException {
        fileUploadService.storePictureForRoute(routeId, image);
    }
}
