package com.sqre.app.repository;

import java.util.List;

import com.sqre.app.model.Cart;
import com.sqre.app.vo.ProductVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
