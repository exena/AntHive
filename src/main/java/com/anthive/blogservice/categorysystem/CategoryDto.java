package com.anthive.blogservice.categorysystem;

import com.anthive.blogservice.utils.NestedConvertHelper;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class CategoryDto {

    private Long categoryId;
    private Long parentId;
    @NotNull
    @Size(min=1,max=30)
    private String name;
    private List<CategoryDto> children;

    public static CategoryDto of(Category category){
        if (category.getParent() == null)
            return new CategoryDto(category.getId(), null, category.getName(), new ArrayList<>());
        return new CategoryDto(category.getId(), category.getParent().getId(), category.getName(), new ArrayList<>());
    }

//    public void addChildCategory(CategoryDto categoryDto){
//        childCategories.add(categoryDto);
//    }
//
//    public void addChildCategories(List<CategoryDto> categoryDto){
//        childCategories.addAll(categoryDto);
//    }

    public static List<CategoryDto> toDtoList(List<Category> categories){

        NestedConvertHelper<Long, Category, CategoryDto> helper = NestedConvertHelper.newInstance(
                categories,
                CategoryDto::of,
                Category::getParent,
                Category::getId,
                CategoryDto::getChildren
        );
        return helper.convert();
    }
}
