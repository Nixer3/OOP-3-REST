package sk.stuba.fei.uim.oop.assignment3.Carts;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.Carts.Supplemental.Snippet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection
    private List<Snippet> shoppingList = new ArrayList<>();

    private Boolean payed = false;

    public Cart(){
    }
}
