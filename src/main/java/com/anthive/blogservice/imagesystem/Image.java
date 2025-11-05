package com.anthive.blogservice.imagesystem;

import com.anthive.blogservice.postsystem.Post;
import com.anthive.blogservice.utils.UploadFile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private UploadFile file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", referencedColumnName = "id")
    @JsonIgnore
    private Post post;
}
