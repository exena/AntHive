package com.anthive.blogservice.postsystem.dto;

import com.anthive.blogservice.postsystem.Post;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access= AccessLevel.PROTECTED)
public class GetBlogpostFormResponse {
    private Long postId;
    private String title;
    private String content;

    public static GetBlogpostFormResponse of(Post post){
        return new GetBlogpostFormResponse(post.getId(), post.getTitle(), post.getContent());
    }
}
