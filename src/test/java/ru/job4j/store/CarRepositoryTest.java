package ru.job4j.store;

import org.junit.After;
import org.junit.Test;
import ru.job4j.models.Body;
import ru.job4j.models.Brand;
import ru.job4j.models.Car;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CarRepositoryTest {
    @After
    public void clearTables() {
        Store store = HbnStore.instOf();
        store.tx(session -> session.createNativeQuery(
                "TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT").executeUpdate());
    }

    @Test
    public void whenAddCarAndFindByGeneratedIdThenMustBeTheSame() {
        CarRepository repository = CarRepository.instOf();
        Brand brand = Brand.of("Brand");
        Body body = Body.of("Body");
        Car car = Car.of("model", brand, body, 2022, "color", 10);
        repository.addCar(car);
        assertThat(repository.findCarById(car.getId()), is(car));
    }

    @Test
    public void whenFindAllBrandsThenGetBrandsList() {
        CarRepository repository = CarRepository.instOf();
        Brand brand = Brand.of("Brand");
        Brand brand2 = Brand.of("Brand2");
        Body body = Body.of("Body");
        Body body2 = Body.of("Body2");
        Car car = Car.of("model", brand, body, 2022, "color", 10);
        Car car2 = Car.of("model", brand2, body2, 2022, "color", 10);
        repository.addCar(car);
        repository.addCar(car2);
        List<Brand> brands = repository.findAllBrands();
        assertThat(brands.size(), is(2));
    }

    @Test
    public void whenFindAllBodiesThenGetBodiesList() {
        CarRepository repository = CarRepository.instOf();
        Brand brand = Brand.of("Brand");
        Brand brand2 = Brand.of("Brand2");
        Body body = Body.of("Body");
        Body body2 = Body.of("Body2");
        Car car = Car.of("model", brand, body, 2022, "color", 10);
        Car car2 = Car.of("model", brand2, body2, 2022, "color", 10);
        repository.addCar(car);
        repository.addCar(car2);
        List<Body> bodies = repository.findAllBodies();
        assertThat(bodies.size(), is(2));
    }

    @Test
    public void whenFindBrandByGeneratedId() {
        CarRepository repository = CarRepository.instOf();
        Brand brand = Brand.of("Brand");
        Body body = Body.of("Body");
        Car car = Car.of("model", brand, body, 2022, "color", 10);
        repository.addCar(car);
        assertThat(repository.findBrandById(1), is(car.getBrand()));
    }

    @Test
    public void whenFindBodyByGeneratedId() {
        CarRepository repository = CarRepository.instOf();
        Brand brand = Brand.of("Brand");
        Body body = Body.of("Body");
        Car car = Car.of("model", brand, body, 2022, "color", 10);
        repository.addCar(car);
        assertThat(repository.findBodyById(1), is(car.getBody()));
    }
}