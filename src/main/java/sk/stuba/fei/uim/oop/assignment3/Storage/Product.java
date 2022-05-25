package sk.stuba.fei.uim.oop.assignment3.Storage;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Integer amount; //available amount
    private String unit;    // unit (kg, l, piece ...)
    private Float price;    // unit price


    public Integer addAmount(Integer amount){
        return this.amount += amount;
    }
    public Integer takeAmount(Integer amount){
        return this.amount -= amount;
    }
}
