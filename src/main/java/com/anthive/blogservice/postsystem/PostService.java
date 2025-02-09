package com.anthive.blogservice.postsystem;

import com.anthive.blogservice.accountsystem.base.AccountRepository;
import com.anthive.blogservice.accountsystem.base.model.Account;
import com.anthive.blogservice.postsystem.dto.PostBlogpostFormRequest;
import com.anthive.blogservice.postsystem.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final AccountRepository accountRepository;

    private final PostRepository postRepository;

    public Page<Post> getUsersPosts(String userId, Pageable pageable){
        Account account = accountRepository.findByLoginId(userId);
        return postRepository.findByAccount(account, pageable);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    public void publishPost(String userId, PostBlogpostFormRequest request){
        Post post = Post.of(request, accountRepository.findByLoginId(userId));
        postRepository.save(post);
    }

    public void editPost(String userId, PostBlogpostFormRequest request){
        Post post = getPost(request.getPostId());
        post.setTitle(request.getTitle());
        post.setContent(Post.getDecodedContent(request.getContent()));
        postRepository.save(post);
    }

    public void checkAuthorPermission(Long postId, Authentication auth) {
        Post post = getPost(postId);
        if (!post.getAccount().getLoginId().equals(auth.getName())) {
            throw new SecurityException("You are not the author of this post");
        }
    }

    public void deletePost(Long postId, Authentication auth){
        checkAuthorPermission(postId, auth);
        postRepository.deleteById(postId);
    }
}
