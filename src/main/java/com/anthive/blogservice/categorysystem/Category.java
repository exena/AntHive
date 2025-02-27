package com.anthive.blogservice.categorysystem;

import com.anthive.blogservice.accountsystem.base.model.Account;
import com.anthive.blogservice.postsystem.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Post> boards;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    @JsonIgnore
    private Category parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Category> children;
}
