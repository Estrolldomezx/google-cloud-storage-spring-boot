package estrolldomezx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
