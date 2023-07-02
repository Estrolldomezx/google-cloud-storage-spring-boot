package estrolldomezx.service;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GcsService {
    
    @Value("${gcp.bucket.name}")
    private String bucketName;

    @Autowired
    Storage storage;

    public List<String> listOfFiles() {

        List<String> list = new ArrayList<>();
        Page<Blob> blobs = storage.list(bucketName);
        System.out.println("List of files : ");
        for (Blob blob : blobs.iterateAll()) {
            System.out.println(blob.getName());
            list.add(blob.getName());
        }
        return list;
    }

    public void uploadFile(MultipartFile file) throws IOException {

        BlobId blobId = BlobId.of(bucketName, file.getOriginalFilename());
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).
                setContentType(file.getContentType()).build();
        Blob blob = storage.create(blobInfo,file.getBytes());
    }

    public ByteArrayResource downloadFile(String fileName) {

        Blob blob = storage.get(bucketName, fileName);
        ByteArrayResource resource = new ByteArrayResource(
                blob.getContent());

        return resource;
    }

    public boolean deleteFile(String fileName) {
        Blob blob = storage.get(bucketName, fileName);
        return blob.delete();
    }
}
