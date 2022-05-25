package sk.stuba.fei.uim.oop.assignment3.Carts.Supplemental;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Embeddable
public class Snippet {
    private Long productId;
    private Integer amount=0;

    public Snippet(){
        productId = 0l;
        amount=0;
    }

    public Integer addAmount(Integer amount){
        return this.amount +=amount;
    }
}
