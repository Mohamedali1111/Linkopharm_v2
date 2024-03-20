package com.example.aswe.linkopharm.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Objects;

@Entity
public class products {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters long")
    private String name;

    @NotBlank(message = "Availability is required")
    private String availability;

    @NotBlank(message = "Price is required")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]{1,2})?$", message = "Invalid price format")
    private String price;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @Column(length = 1000)
    private String imagePath;

    public products() {
    }

    public products(Integer id, String name, String availability, String price, String description, String category, String imagePath) {
        this.id = id;
        this.name = name;
        this.availability = availability;
        this.price = price;
        this.description = description;
        this.category = category;
        this.imagePath = imagePath;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public products id(Integer id) {
        setId(id);
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

    public products imagePath(String imagePath) {
        setImagePath(imagePath);
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
        return Objects.equals(id, products.id) && Objects.equals(name, products.name) && Objects.equals(availability, products.availability) && Objects.equals(price, products.price) && Objects.equals(description, products.description) && Objects.equals(category, products.category) && Objects.equals(imagePath, products.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, availability, price, description, category, imagePath);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", availability='" + getAvailability() + "'" +
            ", price='" + getPrice() + "'" +
            ", description='" + getDescription() + "'" +
            ", category='" + getCategory() + "'" +
            ", imagePath='" + getImagePath() + "'" +
            "}";
    }
    


}

