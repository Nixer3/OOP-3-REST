package sk.stuba.fei.uim.oop.assignment3.Carts;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.stuba.fei.uim.oop.assignment3.Storage.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart,Long> {
    Optional<Cart> findById(Long Id);
}
