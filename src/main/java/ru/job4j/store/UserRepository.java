package ru.job4j.store;

import ru.job4j.models.User;

public class UserRepository {
    Store store = HbnStore.instOf();

    private final static class Lazy {
        private final static UserRepository INST = new UserRepository();
    }

    public static UserRepository instOf() {
        return Lazy.INST;
    }

    private UserRepository() {
    }

    public User addUser(User user) {
        return store.tx(session -> {
            session.save(user);
            return user;
        });
    }

    public User findByUsername(String username) {
        return store.tx(session -> session.createQuery(
                "select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .uniqueResult());
    }

    public User findByEmail(String email) {
        return store.tx(session -> session.createQuery(
                        "select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .uniqueResult());
    }

    public User findUserById(int id) {
        return store.tx(session -> session.get(User.class, id));
    }
}
