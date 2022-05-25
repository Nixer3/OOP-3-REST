package sk.stuba.fei.uim.oop.assignment3.Storage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAmountResponse {
    private Integer amount; //available amount

    public ProductAmountResponse(Product p){
        this.amount       = p.getAmount()       ;
    }

}