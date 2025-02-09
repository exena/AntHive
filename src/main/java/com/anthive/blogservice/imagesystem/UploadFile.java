package com.anthive.blogservice.imagesystem;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

    @Column(name = "file_original_name")
    private String originalName;

    @Column(name = "file_stored_name")
    private String storedName;

    public UploadFile(String originalName, String storedName) {
        this.originalName = originalName;
        this.storedName = storedName;
    }
}
