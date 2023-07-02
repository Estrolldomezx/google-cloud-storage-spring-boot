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

  @GetMapping("download")
  public ResponseEntity<Resource> downloadFile(
          @RequestParam String fileName)  {

    ByteArrayResource resource = gcsService.downloadFile(fileName);
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + fileName + "\"");
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).headers(headers).body(resource);
  }

  @DeleteMapping("delete")
  public ResponseEntity<String> deleteFile(
          @RequestParam String fileName) {

    gcsService.deleteFile(fileName);
    return ResponseEntity.ok(fileName + " deleted.");
  }
}
