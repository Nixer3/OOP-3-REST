package sk.stuba.fei.uim.oop.assignment3.Carts;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.Carts.Supplemental.Snippet;
import sk.stuba.fei.uim.oop.assignment3.Exceptions.CartAlreadyPayedException;
import sk.stuba.fei.uim.oop.assignment3.Exceptions.NotEnoughItemsException;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    ICartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable("id") Long cartId) {
        try {
            return new ResponseEntity<>(new CartResponse(cartService.fetchCart(cartId)), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{id}/pay")
    public ResponseEntity<String> payCartById(@PathVariable("id") Long cartId) {
        try {
            return new ResponseEntity<>((cartService.payCart(cartId)).toString(), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CartAlreadyPayedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping
    public ResponseEntity<CartResponse> create() {
        return new ResponseEntity<CartResponse>(new CartResponse(cartService.create()), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/add")
    public ResponseEntity<CartResponse> addProductToCart(@PathVariable("id") Long cartId,
                                                         @RequestBody Snippet snippetRequest) {
        try {
            return new ResponseEntity<>(new CartResponse(cartService.addProductToCart(cartId, snippetRequest)), HttpStatus.OK);
        } catch (NotEnoughItemsException | CartAlreadyPayedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //return new ResponseEntity<CartResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCart(@PathVariable("id") Long cartId) {
        try {
            cartService.deleteCart(cartId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}