package github.mahmoudesse.microserviceproduits.web.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import github.mahmoudesse.microserviceproduits.dao.ProductDao;
import github.mahmoudesse.microserviceproduits.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@EnableCircuitBreaker
@EnableHystrixDashboard
@RequestMapping("/products")
public class ProductController implements HealthIndicator {

  private static final Logger log = LoggerFactory.getLogger(ProductController.class);

  @Autowired
  private ProductDao productDao;

  @GetMapping("/getAll")
  @HystrixCommand(fallbackMethod = "getProductsFallback", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
  }, threadPoolKey = "productThreadPool")
  public List<Product> getProducts() {
    log.info("Actuator: ProductController.getProducts()");
    return productDao.findAll();
  }

  @GetMapping("/getById/{id}")
  public Product getProduct(@PathVariable int id) {
    log.info("Actuator: ProductController.getProduct()");
    return productDao.findById(id).orElse(null);
  }

  @PostMapping("/create")
  public Product createProduct(@RequestBody Product product) {
    log.info("Actuator: ProductController.createProduct()");
    return productDao.save(product);
  }

  @PutMapping("/update")
  public Product updateProduct(@RequestBody Product product) {
    log.info("Actuator: ProductController.updateProduct()");
    return productDao.save(product);
  }

  @DeleteMapping("/delete/{id}")
  public void deleteProduct(@PathVariable int id) {
    log.info("Actuator: ProductController.deleteProduct()");
    productDao.deleteById(id);
  }

  public List<Product> getProductsFallback() {
    log.warn("Actuator: ProductController.getProductsFallback()");
    return new ArrayList<>();
  }

  @Override
  public org.springframework.boot.actuate.health.Health health() {
    log.info("Actuator: ProductController.health()");

    List<Product> products = productDao.findAll();
    if (products.isEmpty()) {
      return Health.down().withDetail("count", 0).build();
    }

    return Health.up().withDetail("count", products.size()).build();
  }
}
