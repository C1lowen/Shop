package com.aroma.shop.shop.service;

import com.aroma.shop.shop.dto.ProductDTO;
import com.aroma.shop.shop.dto.ProductShortDTO;
import com.aroma.shop.shop.dto.StatisticsStar;
import com.aroma.shop.shop.model.Products;
import com.aroma.shop.shop.model.Review;
import com.aroma.shop.shop.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ProductsService productsService;
    private final BrandService brandService;
    private final GenderService genderService;
    private final ColorService colorService;

    public ReviewService(ReviewRepository reviewRepository, UserService userService,
                         ProductsService productsService, BrandService brandService,
                         GenderService genderService, ColorService colorService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.productsService = productsService;
        this.brandService = brandService;
        this.genderService = genderService;
        this.colorService = colorService;
    }

    @Transactional
    public void save(Review review) {
        if(review.getName().isEmpty()) {
            String email = userService.getEmailUser();
            userService.findUserByEmail(email).ifPresent(user -> {
                review.setEmail(user.getEmail());
                review.setName(user.getUsername());
            });
        }
        reviewRepository.save(review);
    }

    @Transactional
    public List<ProductDTO> getPopularProducts(){
        List<Long> listIdPopular = reviewRepository.ratingPopularProducts();
        if(listIdPopular.size() < 8) {
            return productsService.findAllLimit();
        }
        return listIdPopular.stream()
                .map(idProduct -> productsService.findProductById(idProduct).orElseThrow(() ->
                        new RuntimeException("Такого товара не найдено")))
                .map(products -> new ProductDTO(products.getId(), products.getName(), products.getImages(),
                        brandService.findById(products.getCategory()), genderService.findById(products.getGender()),
                        colorService.findById(products.getColor()), products.getPrice(), products.getSpecification()))
                .toList();
    }

    @Transactional
    public List<Review> findByIdProduct(Long id) {
        return reviewRepository.findByIdProduct(id);
    }

    @Transactional
    public List<Review> findByIdProductAll(Long id) {
        return reviewRepository.findByIdProductAll(id);
    }

    @Transactional
    public List<Review> findByIdProductStart(Long id) {
        return reviewRepository.findByIdProductStart(id);
    }
    @Transactional
    public Double findCountIdProduct(Long id) {
        Double number = reviewRepository.findCountIdProduct(id).orElse(0.0);
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String formattedNumber = decimalFormat.format(number);
        formattedNumber = formattedNumber.replace(",", ".");
        return Double.parseDouble(formattedNumber);
    }

    public StatisticsStar findStarsCount(Long id) {
        List<Integer> stars = reviewRepository.findStars(id);
        return new StatisticsStar(Collections.frequency(stars, 5), Collections.frequency(stars, 4),
                Collections.frequency(stars, 3), Collections.frequency(stars, 2),
                Collections.frequency(stars, 1));
    }
}
