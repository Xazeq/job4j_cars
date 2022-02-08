package ru.job4j.store;

import org.junit.After;
import org.junit.Test;
import ru.job4j.models.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class AdsRepositoryTest {
    @After
    public void clearTables() {
        Store store = HbnStore.instOf();
        store.tx(session -> session.createNativeQuery(
                "TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT").executeUpdate());
    }

    @Test
    public void whenAddAdAndFindByGeneratedIdThenMustBeTheSame() {
        AdsRepository repository = AdsRepository.instOf();
        Ad ad = Ad.of("Ad1");
        repository.addAd(ad);
        assertThat(repository.findAdById(ad.getId()), is(ad));
    }

    @Test
    public void whenFindAllAdsThenGetAllAdsList() {
        AdsRepository repository = AdsRepository.instOf();
        User user = User.of("user", "pass", "user@gmail.com");
        Brand brand = Brand.of("Brand");
        Body body = Body.of("Body");
        Car car1 = Car.of("model1", brand, body, 2022, "color", 10);
        Car car2 = Car.of("model2", brand, body, 2022, "color", 10);
        Car car3 = Car.of("model3", brand, body, 2022, "color", 10);
        Ad ad1 = Ad.of("Ad1", 1, car1, user);
        Ad ad2 = Ad.of("Ad2", 1, car2, user);
        Ad ad3 = Ad.of("Ad3", 1, car3, user);
        repository.addAd(ad1);
        repository.addAd(ad2);
        repository.addAd(ad3);
        List<Ad> ads = repository.findAll();
        assertThat(ads.size(), is(3));
    }

    @Test
    public void whenFindAllActiveAdsThenGetAllActiveAdsList() {
        AdsRepository repository = AdsRepository.instOf();
        User user = User.of("user", "pass", "user@gmail.com");
        Brand brand = Brand.of("Brand");
        Body body = Body.of("Body");
        Car car1 = Car.of("model1", brand, body, 2022, "color", 10);
        Car car2 = Car.of("model2", brand, body, 2022, "color", 10);
        Car car3 = Car.of("model3", brand, body, 2022, "color", 10);
        Ad ad1 = Ad.of("Ad1", 1, car1, user);
        Ad ad2 = Ad.of("Ad2", 1, car2, user);
        Ad ad3 = Ad.of("Ad3", 1, car3, user);
        ad2.setActive(false);
        repository.addAd(ad1);
        repository.addAd(ad2);
        repository.addAd(ad3);
        List<Ad> ads = repository.findAllActive();
        assertThat(ads.size(), is(2));
    }

    @Test
    public void whenCloseAdsThenAdNotActive() {
        AdsRepository repository = AdsRepository.instOf();
        User user = User.of("user", "pass", "user@gmail.com");
        Brand brand = Brand.of("Brand");
        Body body = Body.of("Body");
        Car car = Car.of("model", brand, body, 2022, "color", 10);
        Ad ad = Ad.of("Ad", 1, car, user);
        repository.addAd(ad);
        Ad closedAd = repository.closeAd(ad.getId());
        assertFalse(closedAd.isActive());
    }

    @Test
    public void whenFindLastDayAdsThenGetAdsList() {
        AdsRepository repository = AdsRepository.instOf();
        User user = User.of("user", "pass", "user@gmail.com");
        Brand brand = Brand.of("Brand");
        Body body = Body.of("Body");
        Car car1 = Car.of("model1", brand, body, 2022, "color", 10);
        Car car2 = Car.of("model2", brand, body, 2022, "color", 10);
        Car car3 = Car.of("model3", brand, body, 2022, "color", 10);
        Ad ad1 = Ad.of("Ad1", 1, car1, user);
        Ad ad2 = Ad.of("Ad2", 1, car2, user);
        Ad ad3 = Ad.of("Ad3", 1, car3, user);
        ad2.setCreated(Date.from(LocalDateTime.now().minusDays(5).atZone(ZoneId.systemDefault()).toInstant()));
        repository.addAd(ad1);
        repository.addAd(ad2);
        repository.addAd(ad3);
        List<Ad> ads = repository.findLastDayAds();
        assertThat(ads.size(), is(2));
    }

    @Test
    public void whenAdsByBrandThenGetAdsList() {
        AdsRepository repository = AdsRepository.instOf();
        User user = User.of("user", "pass", "user@gmail.com");
        Brand brand = Brand.of("Brand");
        Brand brand2 = Brand.of("Brand2");
        Body body = Body.of("Body");
        Car car1 = Car.of("model1", brand, body, 2022, "color", 10);
        Car car2 = Car.of("model2", brand2, body, 2022, "color", 10);
        Car car3 = Car.of("model3", brand2, body, 2022, "color", 10);
        Ad ad1 = Ad.of("Ad1", 1, car1, user);
        Ad ad2 = Ad.of("Ad2", 1, car2, user);
        Ad ad3 = Ad.of("Ad3", 1, car3, user);
        repository.addAd(ad1);
        repository.addAd(ad2);
        repository.addAd(ad3);
        List<Ad> ads = repository.findAdsByBrand("Brand2");
        assertThat(ads.size(), is(2));
    }

    @Test
    public void whenAdsByUserThenGetAdsList() {
        AdsRepository repository = AdsRepository.instOf();
        User user = User.of("user", "pass", "user@gmail.com");
        User user2 = User.of("user2", "pass", "user2@gmail.com");
        Brand brand = Brand.of("Brand");
        Body body = Body.of("Body");
        Car car1 = Car.of("model1", brand, body, 2022, "color", 10);
        Car car2 = Car.of("model2", brand, body, 2022, "color", 10);
        Car car3 = Car.of("model3", brand, body, 2022, "color", 10);
        Ad ad1 = Ad.of("Ad1", 1, car1, user);
        Ad ad2 = Ad.of("Ad2", 1, car2, user2);
        Ad ad3 = Ad.of("Ad3", 1, car3, user);
        repository.addAd(ad1);
        repository.addAd(ad2);
        repository.addAd(ad3);
        List<Ad> ads = repository.findAdsByUser("user2");
        assertThat(ads.size(), is(1));
    }
}