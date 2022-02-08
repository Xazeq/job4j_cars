package ru.job4j.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private int price;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    private boolean isActive;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public static Ad of(String description, int price, Car car, User user) {
        Ad ad = new Ad();
        ad.description = description;
        ad.price = price;
        ad.created = new Date(System.currentTimeMillis());
        ad.isActive = true;
        ad.car = car;
        ad.user = user;
        return ad;
    }

    public static Ad of(String description) {
        Ad ad = new Ad();
        ad.description = description;
        ad.created = new Date(System.currentTimeMillis());
        ad.isActive = true;
        return ad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ad ad = (Ad) o;
        return id == ad.id && Objects.equals(description, ad.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
}
