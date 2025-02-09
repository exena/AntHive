package com.anthive.blogservice.imagesystem;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUtils {

    public static List<UploadFile> storeFiles(List<MultipartFile> multipartFiles, String fileDir) throws IOException
    {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile, fileDir));
            }
        }
        return storeFileResult;
    }
    public static UploadFile storeFile(MultipartFile multipartFile, String fileDir) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storedFileName = createStoredFileName(originalFilename);
        multipartFile.transferTo(new File(fileDir + storedFileName));
        return new UploadFile(originalFilename, storedFileName);
    }
    public static Boolean deleteFile(String storedFileName, String fileDir) {
        try {
            Path path = Paths.get(fileDir + storedFileName);
            Files.delete(path);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    private static String createStoredFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }
    private static String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
