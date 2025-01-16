package com.anthive.blogservice.categorysystem;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private final CategoryService categoryService;

    //조회
    @Secured("ROLE_USER")
    @GetMapping("/total")
    public ApiResponse<List<CategoryDto>> getTotalCategories() {
        return ApiResponse.ok(categoryService.getTotalCategories());
    }
}