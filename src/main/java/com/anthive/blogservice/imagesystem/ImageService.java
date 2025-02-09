package com.anthive.blogservice.imagesystem;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${image-folder-path}")
    private String imgFolderPath;

    @Value("${image-url}")
    private String imgUrl;

    private final ImageRepository imageRepository;

    public String uploadImageFile(MultipartFile multipartFile) throws IOException{
        UploadFile uploadFile = FileUtils.storeFile(multipartFile, imgFolderPath);
        assert uploadFile != null;
        String url = imgUrl + "/" + uploadFile.getStoredName();
        Image image = new Image(null, uploadFile, null);
        imageRepository.save(image);
        return url;
    }

    public String getFullDir(String filename){
        return imgFolderPath + filename;
    }
}
