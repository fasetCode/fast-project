package com.fastproject.db;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.util.TriConsumer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

@Component
public class QueryHelp<T> {

    /**
     * pageQuery: 用于通用分页参数
     * conditionFunc: 用户自定义条件函数
     *      (root, cb) -> List<Predicate>
     */
    public Specification<T> getWhere(PageQuery pageQuery,
                                     BiFunction<Root<T>, CriteriaBuilder, List<Predicate>> conditionFunc) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 逻辑删除

            // 用户自定义条件
            if (conditionFunc != null) {
                List<Predicate> customPredicates = conditionFunc.apply(root, cb);
                if (customPredicates != null && !customPredicates.isEmpty()) {
                    predicates.addAll(customPredicates);
                }
            }

            // 如果没有条件，返回 null 表示无过滤
            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
