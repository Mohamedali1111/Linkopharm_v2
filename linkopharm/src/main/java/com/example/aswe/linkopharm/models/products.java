package com.example.aswe.linkopharm.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class products {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String image;

    private String name;
    
    private String availability;
    
    private String price;
    
    private String description;
    
    private String category;

    public products() {
    }

    public products(Integer id, String image, String name, String availability, String price, String description, String category) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.availability = availability;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailability() {
        return this.availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public products id(Integer id) {
        setId(id);
        return this;
    }

    public products image(String image) {
        setImage(image);
        return this;
    }

    public products name(String name) {
        setName(name);
        return this;
    }

    public products availability(String availability) {
        setAvailability(availability);
        return this;
    }

    public products price(String price) {
        setPrice(price);
        return this;
    }

    public products description(String description) {
        setDescription(description);
        return this;
    }

    public products category(String category) {
        setCategory(category);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof products)) {
            return false;
        }
        products products = (products) o;
        return Objects.equals(id, products.id) && Objects.equals(image, products.image) && Objects.equals(name, products.name) && Objects.equals(availability, products.availability) && Objects.equals(price, products.price) && Objects.equals(description, products.description) && Objects.equals(category, products.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image, name, availability, price, description, category);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", image='" + getImage() + "'" +
            ", name='" + getName() + "'" +
            ", availability='" + getAvailability() + "'" +
            ", price='" + getPrice() + "'" +
            ", description='" + getDescription() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }


   

}