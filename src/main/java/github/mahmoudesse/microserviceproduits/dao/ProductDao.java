package github.mahmoudesse.microserviceproduits.dao;

import github.mahmoudesse.microserviceproduits.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
}
