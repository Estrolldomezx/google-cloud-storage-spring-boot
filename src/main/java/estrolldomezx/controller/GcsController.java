package estrolldomezx.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import estrolldomezx.service.GcsService;

@RestController
@RequestMapping("/api/v1/files")
public class GcsController {

  @Autowired
  public GcsService gcsService;

  @GetMapping
  public ResponseEntity<List<String>> listOfFiles() {

    List<String> files = gcsService.listOfFiles();

    return ResponseEntity.ok(files);
  }

  @PostMapping("upload")
  public ResponseEntity<String> uploadFile(
          @RequestParam MultipartFile file) throws IOException {
    System.out.println("name : " + file.getName());
    gcsService.uploadFile(file);

    return ResponseEntity.ok(file.getName() + " added.");
  }
}
