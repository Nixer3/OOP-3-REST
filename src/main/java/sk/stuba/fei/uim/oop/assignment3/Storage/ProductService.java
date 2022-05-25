package sk.stuba.fei.uim.oop.assignment3.Storage;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{
    @Autowired
    private ProductRepository storage;

    @Override
    public List<Product> findAll() {
        return storage.findAll();
    }

    @Override
    public Product create(Product product) {
        return storage.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return storage.findById(id);
    }

    @Override
    public Product updateProduct(Long id, ProductUpdateRequest update) throws NotFoundException {
        Product product = fetchProduct(id);
        if(update.getName()!=null)
            product.setName(update.getName());

        if(update.getDescription()!=null)
            product.setDescription(update.getDescription());
        storage.save(product);
        return product;
    }

    @Override
    public ProductAmountResponse incProductAmountById(Long Id, Integer amount) throws NotFoundException {
        Product product = fetchProduct(Id);

        product.addAmount(amount);
        storage.save(product);
        return new ProductAmountResponse(product);

    }

    @Override
    public ProductAmountResponse findProductAmountById(Long Id) throws NotFoundException {
        Product product = this.fetchProduct(Id);
        return new ProductAmountResponse(product);

    }

    @Override
    public void deleteById(Long productID) throws NotFoundException {
        fetchProduct(productID);
        storage.deleteById(productID);
    }
    public Product fetchProduct(Long id) throws NotFoundException {
        Optional<Product> popt = storage.findById(id);
        if(popt.isEmpty()) throw new NotFoundException("Product with ID("+id+") doesn't exist");
        return popt.get();


    }
    @Override
    public Product save(Product p) {
        return storage.save(p);
    }

}
