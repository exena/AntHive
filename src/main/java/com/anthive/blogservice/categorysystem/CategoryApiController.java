package com.anthive.blogservice.categorysystem;

import com.anthive.blogservice.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryApiController {

    private final CategoryService categoryService;

    //조회
    @GetMapping(value = "/categories", params = {"username"})
    public ApiResponse<List<CategoryDto>> getTotalCategories(@RequestParam("username") String username) {
        return ApiResponse.ok(categoryService.getAccountsCategories(username));
    }
}