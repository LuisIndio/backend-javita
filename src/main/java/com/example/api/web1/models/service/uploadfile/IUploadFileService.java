package com.example.api.web1.models.service.uploadfile;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;

public interface IUploadFileService {

    public Resource load(String filename) throws MalformedURLException;

    public String copy(MultipartFile file) throws Exception;

    public boolean delete(String filename);

    public Path getPath(String filename);

    public Resource loadAsResource (String filename) throws MalformedURLException;
}
