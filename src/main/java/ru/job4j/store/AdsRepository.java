package ru.job4j.store;

import ru.job4j.models.Ad;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class AdsRepository {
    Store store = HbnStore.instOf();

    private final static class Lazy {
        private final static AdsRepository INST = new AdsRepository();
    }

    public static AdsRepository instOf() {
        return Lazy.INST;
    }

    private AdsRepository() {
    }

    public Ad addAd(Ad ad) {
        return store.tx(session -> {
            session.save(ad);
            return ad;
        });
    }

    public List<Ad> findAll() {
        return store.tx(session -> session.createQuery(
                "select distinct a from Ad a "
                        + "join fetch a.car c "
                        + "join fetch c.brand "
                        + "join fetch c.body "
                        + "join fetch a.user "
                        + "order by a.created desc").list());
    }

    public List<Ad> findAllActive() {
        return store.tx(session -> session.createQuery(
                "select distinct a from Ad a "
                        + "join fetch a.car c "
                        + "join fetch c.brand "
                        + "join fetch c.body "
                        + "join fetch a.user "
                        + "where a.isActive = true "
                        + "order by a.created desc").list());
    }

    public List<Ad> findLastDayAds() {
        Date date = Date.from(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        return store.tx(session -> session.createQuery(
                "select distinct a from Ad a "
                        + "join fetch a.car c "
                        + "join fetch c.brand "
                        + "join fetch c.body "
                        + "join fetch a.user "
                        + "where a.isActive = true "
                        + "and a.created between :date and current_timestamp "
                        + "order by a.created desc")
                .setParameter("date", date)
                .list());
    }

    public List<Ad> findAdsByBrand(String brandName) {
        return store.tx(session -> session.createQuery(
                "select distinct a from Ad a "
                        + "join fetch a.car c "
                        + "join fetch c.brand "
                        + "join fetch c.body "
                        + "join fetch a.user "
                        + "where a.isActive = true "
                        + "and c.brand.name = :brand "
                        + "order by a.created desc")
                .setParameter("brand", brandName)
                .list());
    }

    public List<Ad> findAdsByUser(String username) {
        return store.tx(session -> session.createQuery(
                "select distinct a from Ad a "
                        + "join fetch a.car c "
                        + "join fetch c.brand "
                        + "join fetch c.body "
                        + "join fetch a.user "
                        + "where a.user.username = :username "
                        + "order by a.created desc")
                .setParameter("username", username)
                .list());
    }

    public Properties getAppCfg() {
        return HbnStore.instOf().getAppCfg();
    }

    public Ad closeAd(int id) {
        return store.tx(session -> {
            session.createQuery("update Ad set isActive = false where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            return session.get(Ad.class, id);
        });
    }

    public Ad findAdById(int id) {
        return store.tx(session -> session.get(Ad.class, id));
    }
}
