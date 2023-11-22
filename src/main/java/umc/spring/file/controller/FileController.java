package umc.spring.file.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.spring.file.domain.S3FileDto;
import umc.spring.file.service.AmazonS3Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {

    private final AmazonS3Service amazon3SService;

    @PostMapping("/uploads")
    public List<S3FileDto> uploadFiles(
            @RequestParam(value = "uploadFilePath") String uploadFilePath,
            @RequestPart(value = "files") List<MultipartFile> multipartFiles) {
        return amazon3SService.uploadFiles(uploadFilePath, multipartFiles);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteFile(
            @RequestParam(value = "uploadFilePath") String uploadFilePath,
            @RequestParam(value = "uuidFileName") String uuidFileName) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(amazon3SService.deleteFile(uploadFilePath, uuidFileName));
    }

    @GetMapping("/get")
    public void getFile(HttpServletResponse response,
                        @RequestParam String uploadFilePath,
                        @RequestParam String uuidFileName) {
        try {
            InputStream inputStream = amazon3SService.getFile(uploadFilePath, uuidFileName);
            if (inputStream != null) {
                byte[] buffer = new byte[4048];
                int bytesRead;
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

                try (OutputStream outStream = response.getOutputStream()) {
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outStream.write(buffer, 0, bytesRead);
                    }
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
