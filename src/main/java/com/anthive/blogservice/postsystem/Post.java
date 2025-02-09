package com.anthive.blogservice.postsystem;

import com.anthive.blogservice.accountsystem.base.model.Account;
import com.anthive.blogservice.categorysystem.Category;
import com.anthive.blogservice.imagesystem.Image;
import com.anthive.blogservice.postsystem.dto.PostBlogpostFormRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor(access= AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;
    @Setter
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id")
    @JsonIgnore
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany(mappedBy = "post")
    private List<Image> images;

    public static Post of(PostBlogpostFormRequest request, Account account){
        return new Post(request.getPostId(), request.getTitle(), getDecodedContent(request.getContent()), account, request.getCategory(), null);
    }

    public static String getDecodedContent(String encodedContent){
        return URLDecoder.decode(encodedContent, StandardCharsets.UTF_8);
    }
}