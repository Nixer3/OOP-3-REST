package sk.stuba.fei.uim.oop.assignment3.Carts;

import javassist.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.Carts.Supplemental.Snippet;
import sk.stuba.fei.uim.oop.assignment3.Exceptions.CartAlreadyPayedException;
import sk.stuba.fei.uim.oop.assignment3.Exceptions.NotEnoughItemsException;

import java.util.Optional;

public interface ICartService {
    Cart fetchCart(Long id) throws NotFoundException;
    Float payCart(Long id)throws NotFoundException, CartAlreadyPayedException;
    Cart addProductToCart(Long id, Snippet product) throws NotFoundException, NotEnoughItemsException, CartAlreadyPayedException;
    Cart removeSnippetFromCart(Long id, Snippet product) throws NotFoundException, NotEnoughItemsException;
    void deleteCart(Long id)throws NotFoundException;
    Cart emptyCart(Long id) throws NotFoundException;
    Optional<Cart> findById(Long id);
    Cart create();
}
