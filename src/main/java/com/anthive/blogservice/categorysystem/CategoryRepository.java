package com.anthive.blogservice.categorysystem;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Category> findAllOrderByParentIdAscNullsFirstCategoryIdAsc() {
        QCategory c1 = new QCategory("c1");
        QCategory c2 = new QCategory("c2");


        List<Category> result = queryFactory
                .select(Projections.fields(Category.class,
                                c1.id,
                                c2.as("parent"),
                                c1.name
                        )
                )
                .from(c1)
                .leftJoin(c2).on(c2.eq(c1.parent),c1.id.ne(c1.parent.id))
                .orderBy(c2.id.asc(),c1.id.asc())
                .fetch();

        if (result == null){
            return new ArrayList<>();
        }

        return result;
    }
}