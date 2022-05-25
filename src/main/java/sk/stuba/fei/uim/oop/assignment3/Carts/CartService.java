package sk.stuba.fei.uim.oop.assignment3.Carts;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.Carts.Supplemental.Snippet;
import sk.stuba.fei.uim.oop.assignment3.Exceptions.CartAlreadyPayedException;
import sk.stuba.fei.uim.oop.assignment3.Exceptions.NotEnoughItemsException;
import sk.stuba.fei.uim.oop.assignment3.Storage.IProductService;
import sk.stuba.fei.uim.oop.assignment3.Storage.Product;

import java.util.Optional;

@Service
public class CartService implements ICartService {
    private IProductService storage;
    private CartRepository garage;

    @Autowired
    public void setGarage(CartRepository garage) {
        this.garage = garage;
    }

    @Autowired
    public void setStorage(IProductService storage) {
        this.storage = storage;
    }

    @Override
    public Float payCart(Long id) throws NotFoundException, CartAlreadyPayedException {
        Cart cart = fetchCart(id);
        if (cart.getPayed()) throw new CartAlreadyPayedException("Cart " + id + " is already payed.");//simplest float
        float sum = 0f;

        for (var snip : cart.getShoppingList()) {
            if (snip.getProductId() == 0) continue;
            var prod = fetchProduct(snip.getProductId());
            sum += prod.getPrice() * snip.getAmount();
        }
        cart.setPayed(true);
        cart.getShoppingList().clear();
        garage.save(cart);

        return sum;
    }

    @Override
    public Cart addProductToCart(Long id, Snippet snippet) throws NotFoundException, NotEnoughItemsException, CartAlreadyPayedException {
        var cart = fetchCart(id);
        if(cart.getPayed()) throw new CartAlreadyPayedException("Cart with ID(" + id + ") is already payed");
        var prod = fetchProduct(snippet.getProductId());

        int amount = snippet.getAmount();//transferring amount
        if(amount > prod.getAmount()) throw new NotEnoughItemsException("Cart requesting more than it is in storage");

        //remove from storage anyway
        prod.takeAmount(amount);

        Optional<Snippet> existingSnipp = cart.getShoppingList().stream().filter(s -> s.getProductId().equals(prod.getId())).findAny();
        if(existingSnipp.isPresent())
            existingSnipp.get().addAmount(amount);
        else
            cart.getShoppingList().add(snippet);

        cart.setPayed(false);
        storage.save(prod);
        garage.save(cart);
        return cart;
    }

    /**
     * removes snippet from Cart's.shoppingList and returning product to storage
     * throws {@link NotFoundException} if cart is not found, or snippet is not from Cart's.shoppingList
     * for emptying cart use emptyCart()
     */
    @Override
    public Cart removeSnippetFromCart(Long id, Snippet snippet) throws NotFoundException {

        var cart = fetchCart(id);
        var prod = fetchProduct(id);

        if (!cart.getShoppingList().contains(snippet))
            throw new NotFoundException("Removing snippet must be from Cart");//.stream().filter(s -> s.getProductId()==prod.getId()).findAny();

        int amount = snippet.getAmount();//transferring amount
        prod.addAmount(amount);

        cart.getShoppingList().remove(snippet);
        storage.save(prod);
        garage.save(cart);
        return cart;
    }

    @Override
    public void deleteCart(Long id) throws NotFoundException {
        emptyCart(id);
        garage.deleteById(id);
    }

    /**
     * emptying cart back to storage
     */
    @Override
    public Cart emptyCart(Long id) throws NotFoundException {
        var cart = fetchCart(id);

        cart.getShoppingList().forEach(snip -> {
            Product p;
            try {
                p = fetchProduct(snip.getProductId());
                p.addAmount(snip.getAmount());
                snip.setAmount(0);
                storage.save(p);
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }
        });

        cart.getShoppingList().clear();
        garage.save(cart);
        return cart;
    }

    public Cart fetchCart(Long id) throws NotFoundException {
        Optional<Cart> copt = garage.findById(id);
        if (copt.isEmpty()) throw new NotFoundException("Cart with ID(" + id + ") doesn't exist");
        return copt.get();
    }

    public Product fetchProduct(Long id) throws NotFoundException {
        Optional<Product> popt = storage.findById(id);
        if (popt.isEmpty()) throw new NotFoundException("Product with ID(" + id + ") doesn't exist");
        return popt.get();
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return garage.findById(id);
    }

    @Override
    public Cart create() {
        return garage.save(new Cart());
    }
}
