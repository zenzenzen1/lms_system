package utils;

import com.google.cloud.storage.*;
import dto.StorageDto;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class GoogleStorageUtils {

    private static final String projectId = "dev-access-419704";
    private static final String bucketName = "lms_bucket_swp";

    private static final Storage storage;

    static {
        storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
    }

    public String uploadToBucket(String fileName, byte[] data) {
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, data);
        return blobId.getName();
    }

    public String getSignedUrl(StorageDto storageVo) {
        BlobInfo blobInfo = BlobInfo.newBuilder(storageVo.getBucketName(), storageVo.getFileName()).build();

        URL url = storage.signUrl(blobInfo, 15, TimeUnit.DAYS, Storage.SignUrlOption.httpMethod(HttpMethod.GET));
        return url.toString();
    }
}
