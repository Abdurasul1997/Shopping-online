package com.company.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.company.domains.Product;
import com.company.domains.Uploads;
import com.company.dto.ProductCreateDTO;
import com.company.enums.Currency;
import com.company.enums.Gender;
import com.company.enums.ProductType;
import com.company.repository.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository rep;
    private final UploadsService service;

    public void save(ProductCreateDTO dto) {
        Uploads cover= service.upload(dto.getCover());

            Product product = Product
                .builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .type(ProductType.findByName(dto.getType()))
                .gender(Gender.findByName(dto.getGender()))
                .currency(Currency.findByName(dto.getCurrency()))
                .cover(cover)
                .build();
        rep.save(product);
    }

    public List<Product> findAll() {
        return rep.findAll();
    }

    public void delete(Long id) {
     rep.deleteById(id);
    }

    public Optional<Product> get(Long id) {
        Optional<Product> product = rep.findById(id);
        return product;
    }

//    public List<Product> swatchAll(ProductType watch) {
//        int page=0;
//        int size=10;
//        Pageable pageable=PageRequest.of(page,size,Sort.by("id").descending());
//        return rep.swatchAll(watch);
//    }

//    public Page<Product> getAll(Pageable pageable, String search) {
//        List<Product> products = rep.findAll(search);
//        List<Product> productList;
//
//        int pageSize = pageable.getPageSize();
//        int currentPage = pageable.getPageNumber();
//        int startItem = currentPage * pageSize;
//        if (products.size() < startItem) {
//            productList = Collections.emptyList();
//        } else {
//            int toIndex = Math.min(startItem + pageSize, products.size());
//            productList = products.subList(startItem, toIndex);
//        }
//        return new PageImpl<>(productList, PageRequest.of(currentPage, pageSize), products.size());
//    }

    public List<Product> shoesAll(ProductType shoes) {
        int page=0;
        int size=4;
        Pageable pageable=PageRequest.of(page,size,Sort.by("id").descending());
        return rep.shoesAll(shoes,pageable);
    }

    public Page<Product> productAll(@NonNull String searchQuery, Optional<Integer> pageOptional, Optional<Integer> limitOptional) {
        int page = pageOptional.orElse(0);
        int size = limitOptional.orElse(8);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return rep.findAll(searchQuery.toLowerCase(),pageable);
    }

    public Optional<Product> finById(Long id) {
        return rep.findById(id);
    }

    public void update(Long id, ProductCreateDTO dto) {
        Uploads upload = service.upload(dto.getCover());

//        Optional<Product> productid = rep.findById(id);
//
//        Product product1 = productid.get();
//        product1.setName(dto.getName());
//        product1.setCurrency(Currency.findByName(dto.getCurrency()));
//        product1.setType(ProductType.findByName(dto.getType()));
//        product1.setCover(upload);
//        product1.setGender(Gender.findByName(dto.getGender()));
//        product1.setPrice(dto.getPrice());

        Product product= Product
                .builder()
                .id(id)
                .name(dto.getName())
                .currency(Currency.findByName(dto.getCurrency()))
                .type(ProductType.findByName(dto.getType()))
                .gender(Gender.findByName(dto.getGender()))
                .price(dto.getPrice())
                .cover(upload)
                .build();
        rep.save(product);
    }
}
