package com.anthive.blogservice.imagesystem;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("${image-url}")
public class ImageApiController {

    private final ImageService imageService;

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadImageFile(@RequestParam("image") MultipartFile multipartFile) {
        try {
            String url = imageService.uploadImageFile(multipartFile);  // 서비스 호출
            return ResponseEntity.ok(url);  // 성공 시 URL 반환
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null); // 예외 발생 시 BAD_REQUEST 반환
        }
    }

    @GetMapping("/{filename}")
    public Resource downloadImage(@PathVariable("filename") String filename) throws MalformedURLException {
        return new UrlResource("file:" + imageService.getFullDir(filename));
    }

//    @GetMapping("/attach/{itemId}")
//    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
//        Item item = itemRepository.findById(itemId);
//        String storeFileName = item.getAttachFile().getStoreFileName();
//        String uploadFileName = item.getAttachFile().getUploadFileName();
//        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));
//        log.info("uploadFileName={}", uploadFileName);
//        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
//        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
//                .body(resource);
//    }
}
