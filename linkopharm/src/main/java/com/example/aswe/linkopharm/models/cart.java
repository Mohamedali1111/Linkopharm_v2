package com.example.aswe.linkopharm.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;



import java.util.Objects;


@Entity
@Table(name = "cart")
public class cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
   
  @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private int productId;
    private String product_name;
    @Column(length = 1000)
    private String product_image;
    private int product_price;
    private String product_description;
    private int quantity;


    public cart() {
    }

    public cart(int id, User user, String product_name, String product_image, int product_price, String product_description, int quantity) {
        this.id = id;
        this.user = user;
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


    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
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


    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
  
      @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof cart)) {
            return false;
        }
        cart cart = (cart) o;
        return Objects.equals(id, cart.id) && Objects.equals(user, cart.user) && Objects.equals(product_name, cart.product_name) && Objects.equals(product_image, cart.product_image) && Objects.equals(product_price, cart.product_price) && Objects.equals(product_description, cart.product_description) && Objects.equals(quantity, cart.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,user, product_name, product_image, product_price, product_description, quantity);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", user_id='" +  getUser() + "'" +
            ", product_name='" + getProduct_name() + "'" +
            ", product_image='" + getProduct_image() + "'" +
            ", product_price='" + getProduct_price() + "'" +
            ", product_description='" + getProduct_description() + "'" +
            ", quantity='" + getQuantity() + "'" +
            "}";
    }

}





