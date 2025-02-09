package com.anthive.blogservice.postsystem;

import com.anthive.blogservice.accountsystem.base.AccountRepository;
import com.anthive.blogservice.accountsystem.base.model.Account;
import com.anthive.blogservice.postsystem.dto.GetBlogpostFormResponse;
import com.anthive.blogservice.postsystem.dto.PostBlogpostFormRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/anthive")
@RequiredArgsConstructor
public class PostController {

    @Value("${image-url}")
    private String imgUrl;

    private final PostService postService;

    @GetMapping("/{userId}/catalog-list")
    public String list_catalog(Model model, @PageableDefault(size = 20) Pageable pageable, @PathVariable("userId") String userId) {
        Page<Post> posts = postService.getUsersPosts(userId, pageable);
        int startPage = Math.max(1, posts.getPageable().getPageNumber() / 5 * 5 + 1);
        int endPage = Math.min(posts.getTotalPages(), startPage + 4);
        if(endPage == 0)
            endPage = 1;
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("posts", posts);
        model.addAttribute("blogger", userId);
        return "anthive/list_catalog";
    }

    @GetMapping(value = "/form", params = {})
    public String form(Model model) {
        model.addAttribute("postForm", new GetBlogpostFormResponse());
        model.addAttribute("imageUrl", imgUrl);
        return "anthive/form";
    }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping(value = "/form", params = {"postId"})
    public String form(Model model, @RequestParam("postId") Long postId, Authentication auth) {
        try {
            postService.checkAuthorPermission(postId, auth);
            model.addAttribute("postForm", GetBlogpostFormResponse.of(postService.getPost(postId)));
            model.addAttribute("imageUrl", imgUrl);
        } catch (Exception e){
            return "redirect:/anthive/form";
        }
        return "anthive/form";
    }

    @PostMapping("/form")
    public String postForm(@Validated PostBlogpostFormRequest request, BindingResult bindingResult, Authentication auth) {
        if(bindingResult.hasErrors()){
            return "anthive/form";
        }
        //새로 만들때
        if(request.getPostId() == null) {
            postService.publishPost(auth.getName(),request);
        }
        //수정할때
        else{
            try {
                postService.checkAuthorPermission(request.getPostId(), auth);
                postService.editPost(auth.getName(), request);
            }catch (Exception e){
                return "anthive/form";
            }
        }
        return "redirect:/anthive/"+ auth.getName() +"/catalog-list";
    }

    @GetMapping(value = "/{userId}/post/{postId}")
    public String view(Model model, @PathVariable("postId") Long postId){
        Post post = postService.getPost(postId);
        model.addAttribute("postForm", GetBlogpostFormResponse.of(post));
        return "anthive/view";
    }

}
