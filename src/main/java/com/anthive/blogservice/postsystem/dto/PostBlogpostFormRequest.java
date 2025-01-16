package com.anthive.blogservice.postsystem.dto;

import com.anthive.blogservice.categorysystem.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostBlogpostFormRequest {
    private Long postId;
    @NotBlank
    @Size(min=1, max=50)
    private String title;
    private String content;
    private Category category;
}
