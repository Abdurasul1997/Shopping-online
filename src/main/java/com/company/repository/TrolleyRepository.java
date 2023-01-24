package com.company.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.company.domains.TrolleyProduct;
import com.company.enums.ProductSize;

import java.util.List;

public interface TrolleyRepository extends JpaRepository<TrolleyProduct, Long> {
    @Query(value = " from TrolleyProduct t where t.isPurchased=false and t.user.id = :id")
    List<TrolleyProduct> trolleyAll(@Param("id") Long id);

    @Query(value = "from TrolleyProduct t where  t.product.id= :product and t.user.id= :user and t.size= :size")
    TrolleyProduct findByProductUserId(@Param("product") Long product, @Param("user") Long user, @Param("size") ProductSize size);


    @Query(value = "from TrolleyProduct t where t.isPurchased=true and lower(t.product.name) like %:query% or t.isPurchased=true and lower(t.product.type) like %:query% or  t.isPurchased=true and lower(t.product.type) like %:query%")
    Page<TrolleyProduct> findAll(@Param("query")String toLowerCase, Pageable pageable);


    @Query(value = "from TrolleyProduct t where t.isPurchased=true and t.user.id= :id and lower(t.product.name) like %:query% or t.isPurchased=true and t.user.id= :id  and lower(t.product.type) like %:query%" +
            " or  t.isPurchased=true and t.user.id= :id  and lower(t.product.type) like %:query%")
    Page<TrolleyProduct> userFindAll(@Param("query") String toLowerCase, Pageable pageable, @Param("id") Long id);

//    @Query("UPDATE TrolleyProduct t SET t.theNumber=:theNumber WHERE t.id=:id")
//    void updateProduct( @Param("theNumber") Integer theNumber,@Param("id") Long id);

}
