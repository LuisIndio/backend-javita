package com.example.api.web1.models.service.uploadfile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements IUploadFileService{

    private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);

    private final static String UPLOADS_FOLDER = "uploads";
    @Override
    public Resource load(String filename) throws MalformedURLException {
        Path pathFile = getPath(filename);
        log.info(pathFile.toString());
        Resource resource = new UrlResource(pathFile.toUri());

        if(!resource.exists() && !resource.isReadable()){
            pathFile = Paths.get("src/main/resources/static/images").resolve("no-user.png").toAbsolutePath();

            resource = new UrlResource(pathFile.toUri());

            log.error("Error to load image: " + filename);
        }
        return resource;
    }

    @Override
    public String copy(MultipartFile file) throws Exception {
        String fileName = UUID.randomUUID().toString() + "_" + Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "");
        Path pathFile = getPath(fileName);

        log.info(pathFile.toString());

        Files.copy(file.getInputStream(), pathFile);

        return fileName;
    }

    @Override
    public boolean delete(String filename) {
        if(filename != null && filename.length() > 0){
            Path pathPhotoBeforeDelete = getPath(filename);
            File filePhotoBeforeDelete = pathPhotoBeforeDelete.toFile();
            if(filePhotoBeforeDelete.exists() && filePhotoBeforeDelete.canRead()){
                filePhotoBeforeDelete.delete();
                return true;
            }
        }
        return false;
    }

    @Override
    public Path getPath(String filename) {
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }

    @Override
    public Resource loadAsResource(String filename) {
        return null;
    }
}
