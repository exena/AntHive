package com.anthive.blogservice.categorysystem;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getTotalCategories() {
        List<Category> categoryList = categoryRepository.findAllOrderByParentIdAscNullsFirstCategoryIdAsc();
        return CategoryDto.toDtoList(categoryList);
    }
}