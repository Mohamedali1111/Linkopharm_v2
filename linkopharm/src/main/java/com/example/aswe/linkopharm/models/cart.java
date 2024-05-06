package com.example.aswe.linkopharm.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



import java.util.Objects;


@Entity
@Table(name = "cart")
public class cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int user_id;
    private String product_name;
    private String product_image;
    private int product_price;
    private String product_description;
    private String quantity;


    public cart() {
    }

    public cart(int id, int user_id, String product_name, String product_image, int product_price, String product_description, String quantity) {
        this.id = id;
        this.user_id = user_id;
        this.product_name = product_name;
        this.product_image = product_image;
        this.product_price = product_price;
        this.product_description = product_description;
        this.quantity = quantity;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getProduct_name() {
        return this.product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image() {
        return this.product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public int getProduct_price() {
        return this.product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public String getProduct_description() {
        return this.product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

      @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof cart)) {
            return false;
        }
        cart cart = (cart) o;
        return Objects.equals(id, cart.id) && Objects.equals(user_id, cart.user_id) && Objects.equals(product_name, cart.product_name) && Objects.equals(product_image, cart.product_image) && Objects.equals(product_price, cart.product_price) && Objects.equals(product_description, cart.product_description) && Objects.equals(quantity, cart.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, product_name, product_image, product_price, product_description, quantity);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", user_id='" + getUser_id() + "'" +
            ", product_name='" + getProduct_name() + "'" +
            ", product_image='" + getProduct_image() + "'" +
            ", product_price='" + getProduct_price() + "'" +
            ", product_description='" + getProduct_description() + "'" +
            ", quantity='" + getQuantity() + "'" +
            "}";
    }

}
