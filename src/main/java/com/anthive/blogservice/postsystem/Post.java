package com.anthive.blogservice.postsystem;

import com.anthive.blogservice.accountsystem.base.model.Account;
import com.anthive.blogservice.postsystem.dto.GetBlogpostFormResponse;
import com.anthive.blogservice.postsystem.dto.PostBlogpostFormRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    public static Post of(PostBlogpostFormRequest request, Account account){
        return new Post(request.getPostId(), request.getTitle(), request.getContent(), account);
    }
}