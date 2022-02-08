package ru.job4j.services;

import ru.job4j.models.*;
import ru.job4j.store.AdsRepository;
import ru.job4j.store.CarRepository;
import ru.job4j.store.UserRepository;

import java.util.List;
import java.util.Properties;

public class AdService {
    private final UserRepository userRepository = UserRepository.instOf();
    private final AdsRepository adsRepository = AdsRepository.instOf();
    private final CarRepository carRepository = CarRepository.instOf();

    private final static class Lazy {
        private final static AdService INST = new AdService();
    }

    public static AdService instOf() {
        return Lazy.INST;
    }

    private AdService() {
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User addUser(User user) {
        return userRepository.addUser(user);
    }

    public List<Ad> findAllAds() {
        return adsRepository.findAll();
    }

    public List<Ad> findActiveAds() {
        return adsRepository.findAllActive();
    }

    public List<Brand> findAllBrands() {
        return carRepository.findAllBrands();
    }

    public List<Body> findAllBodies() {
        return carRepository.findAllBodies();
    }

    public Ad addAd(String model, int year, String color, int mileage,
                     String desc, int price, int brandId, int bodyId, User user) {
        Brand brand = carRepository.findBrandById(brandId);
        Body body = carRepository.findBodyById(bodyId);
        Car car = Car.of(model, brand, body, year, color, mileage);
        Ad ad = Ad.of(desc, price, car, user);
        adsRepository.addAd(ad);
        return ad;
    }

    public List<Ad> findAdsByUser(String username) {
        return adsRepository.findAdsByUser(username);
    }

    public List<Ad> findLastDayAds() {
        return adsRepository.findLastDayAds();
    }

    public List<Ad> findAdsByBrand(String brandName) {
        return adsRepository.findAdsByBrand(brandName);
    }

    public Brand findBrandById(int id) {
        return carRepository.findBrandById(id);
    }

    public Properties getAppCfg() {
        return adsRepository.getAppCfg();
    }

    public Ad closeAd(int id) {
        return adsRepository.closeAd(id);
    }
}
