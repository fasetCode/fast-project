package com.fastproject.module.file;

import com.fastproject.file.api.FileHandle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileHandle fileHandle;

    @GetMapping("/getUrl/{id}")
    public ResponseEntity<Void> getUrl(@PathVariable String id) {
        String url;
        try {
            url = fileHandle.getUrl(id);
        } catch (Exception e) {
            url = null;
        }

        if (!StringUtils.hasText(url) || url.startsWith("null")) {
            return ResponseEntity.notFound().build();
        }

        try {
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
