package ru.job4j.store;

import ru.job4j.models.Body;
import ru.job4j.models.Brand;
import ru.job4j.models.Car;

import java.util.List;

public class CarRepository {
    private Store store = HbnStore.instOf();

    private final static class Lazy {
        private final static CarRepository INST = new CarRepository();
    }

    public static CarRepository instOf() {
        return Lazy.INST;
    }

    private CarRepository() {
    }

    public Car findCarById(int id) {
        return store.tx(session -> session.get(Car.class, id));
    }

    public Car addCar(Car car) {
        return store.tx(session -> {
            session.save(car);
            return car;
        });
    }

    public List<Brand> findAllBrands() {
        return store.tx(session -> session.createQuery("from Brand b order by b.name asc").list());
    }

    public List<Body> findAllBodies() {
        return store.tx(session -> session.createQuery("from Body").list());
    }

    public Brand findBrandById(int id) {
        return store.tx(session -> session.get(Brand.class, id));
    }

    public Body findBodyById(int id) {
        return store.tx(session -> session.get(Body.class, id));
    }
}
