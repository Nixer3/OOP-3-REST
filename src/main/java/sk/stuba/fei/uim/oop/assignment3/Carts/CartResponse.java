package sk.stuba.fei.uim.oop.assignment3.Carts;


import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.Carts.Supplemental.Snippet;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class CartResponse {

    Long id;
    List<Snippet> shoppingList;
    boolean payed;


    CartResponse(Cart cart){
        this.id           = cart.getId();
        this.payed        = cart.getPayed();
        shoppingList = cart.getShoppingList();

    }


}
