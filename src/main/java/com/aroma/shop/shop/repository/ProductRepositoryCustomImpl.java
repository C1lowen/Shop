package com.aroma.shop.shop.repository;

import com.aroma.shop.shop.dto.ConditionProducts;
import com.aroma.shop.shop.dto.ProductDTO;
import com.aroma.shop.shop.model.Products;
import com.aroma.shop.shop.service.BrandService;
import com.aroma.shop.shop.service.ColorService;
import com.aroma.shop.shop.service.GenderService;
import com.aroma.shop.shop.service.ProductsService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final EntityManager entityManager;
    private final BrandService brandService;
    private final GenderService genderService;
    private final ColorService colorService;
    private final ProductsRepository productsRepository;

    public ProductRepositoryCustomImpl(EntityManager entityManager, BrandService brandService, GenderService genderService, ColorService colorService, ProductsRepository productsRepository) {
        this.entityManager = entityManager;
        this.brandService = brandService;
        this.genderService = genderService;
        this.colorService = colorService;
        this.productsRepository = productsRepository;
    }

    @Override
    public Page<ProductDTO> findProductByFilter(ConditionProducts filter, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Products> query = buildQuery(filter, cb);

        TypedQuery<Products> typedQuery = entityManager.createQuery(query);
        int totalPages = typedQuery.getResultList().size();

        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Products> products = typedQuery.getResultList();

        List<ProductDTO> productDTOList = products.stream()
                .map(product -> new ProductDTO(
                        product.getId(), product.getName(), product.getImages(),
                        brandService.findById(product.getCategory()), genderService.findById(product.getGender()),
                        colorService.findById(product.getColor()), product.getPrice(), product.getSpecification()))
                .collect(Collectors.toList());

        return new PageImpl<>(productDTOList, pageable, totalPages);
    }

    private CriteriaQuery<Products> buildQuery(ConditionProducts filter, CriteriaBuilder cb) {
        CriteriaQuery<Products> query = cb.createQuery(Products.class);
        Root<Products> root = query.from(Products.class);

        List<Predicate> predicates = buildPredicates(filter, root, cb);

        query.where(predicates.toArray(new Predicate[0]));

        if (filter.getSorted() != null) {
            if ("cheap".equals(filter.getSorted())) {
                query.orderBy(cb.asc(root.get("price")));
            } else if ("expensive".equals(filter.getSorted())) {
                query.orderBy(cb.desc(root.get("price")));
            }
        }

        return query;
    }

    private List<Predicate> buildPredicates(ConditionProducts filter, Root<Products> root, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        addInCondition(filter.getGender(), root.get("gender"), genderService::findIdByName, predicates);
        addInCondition(filter.getColor(), root.get("color"), colorService::findIdByName, predicates);
        addInCondition(filter.getBrands(), root.get("category"), brandService::findIdByName, predicates);

        addRangeCondition(filter.getPriceStart(), root.get("price"), cb::greaterThanOrEqualTo, predicates);
        addRangeCondition(filter.getPriceEnd(), root.get("price"), cb::lessThanOrEqualTo, predicates);

        return predicates;
    }
    private <T> void addInCondition(String[] values, Path<T> path, Function<String, Long> idFinder, List<Predicate> predicates) {
        if (values != null && values.length > 0) {
            Long[] ids = Arrays.stream(values)
                    .map(idFinder)
                    .toArray(Long[]::new);
            predicates.add(path.in((Object[]) ids));
        }
    }

    private <T> void addRangeCondition(T value, Path<T> path, BiFunction<Expression<T>, T, Predicate> condition, List<Predicate> predicates) {
        if (value != null) {
            predicates.add(condition.apply(path, value));
        }
    }
}
