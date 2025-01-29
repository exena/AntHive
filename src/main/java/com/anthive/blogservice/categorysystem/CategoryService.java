package com.anthive.blogservice.categorysystem;

import com.anthive.blogservice.accountsystem.base.AccountRepository;
import com.anthive.blogservice.accountsystem.base.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryQuerydslRepository categoryQuerydslRepository;

    private final AccountRepository accountRepository;

    public List<CategoryDto> getAccountsCategories(String username) {
        Account account = accountRepository.findByLoginId(username);
        List<Category> categoryList = categoryQuerydslRepository.findByAccountOrderByParentIdAscNullsFirstCategoryIdAsc(account.getId());
        return CategoryDto.toDtoList(categoryList);
    }
}