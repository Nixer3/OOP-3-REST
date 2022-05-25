package sk.stuba.fei.uim.oop.assignment3.Storage;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private IProductService service;
    @Autowired
    public void setService(IProductService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Product> getAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long productID){
        try {
            return new ResponseEntity<>(service.fetchProduct(productID),HttpStatus.OK);

        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{id}/amount")
    public ResponseEntity<ProductAmountResponse> getProductAmountById(@PathVariable("id") Long productID){
        try{
            var product  = service.findProductAmountById(productID);
            return new ResponseEntity<>(product,HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,@RequestBody ProductUpdateRequest update){
        try {
            return new ResponseEntity<>(service.updateProduct(id,update),HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product request){
        return new ResponseEntity<>(service.create(request),HttpStatus.CREATED);
    }

    @PostMapping("/{id}/amount")
    public ResponseEntity<ProductAmountResponse> incProductAmountById(@PathVariable("id") Long productID,@RequestBody ProductAmountRequest request){
        try{
            var product = service.incProductAmountById(productID,request.getAmount());
            return new ResponseEntity<>(product,HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable("id") Long productID){
        try { service.deleteById(productID);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }




    }






}
