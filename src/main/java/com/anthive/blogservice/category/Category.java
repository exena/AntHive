package com.anthive.blogservice.category;

import com.anthive.blogservice.accountsystem.base.model.Account;
import com.anthive.blogservice.postsystem.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=2,max=30)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Post> boards = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Category> children;
}
