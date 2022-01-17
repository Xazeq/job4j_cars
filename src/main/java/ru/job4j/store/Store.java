package ru.job4j.store;

import ru.job4j.models.Ad;

import java.util.List;

public interface Store {
    List<Ad> findLastDayAds();
    List<Ad> findAdsWithPhoto();
    List<Ad> findAdsByBrand(String brandName);
}
