package sk.stuba.fei.uim.oop.assignment3.Storage;

import javassist.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<Product> findAll();
    Product create(Product product);
    Optional<Product> findById(Long id);
    Product updateProduct(Long id,ProductUpdateRequest update) throws NotFoundException;

    ProductAmountResponse incProductAmountById(Long id, Integer amount) throws NotFoundException;
    ProductAmountResponse findProductAmountById(Long id) throws NotFoundException;
    void deleteById(Long id) throws NotFoundException;
    Product fetchProduct(Long id) throws NotFoundException;
    Product save(Product p);
}
