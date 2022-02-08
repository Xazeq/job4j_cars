package ru.job4j.store;

import org.junit.After;
import org.junit.Test;
import ru.job4j.models.User;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserRepositoryTest {
    @After
    public void clearTables() {
        Store store = HbnStore.instOf();
        store.tx(session -> session.createNativeQuery(
                "TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT").executeUpdate());
    }

    @Test
    public void whenAddUserAndFindByGeneratedIdThenMustBeTheSame() {
        UserRepository repository = UserRepository.instOf();
        User user = User.of("user", "pass", "user@gmail.com");
        repository.addUser(user);
        assertThat(repository.findUserById(user.getId()), is(user));
    }

    @Test
    public void whenFindUserByUsernameThenGetUSer() {
        UserRepository repository = UserRepository.instOf();
        User user = User.of("user", "pass", "user@gmail.com");
        repository.addUser(user);
        assertThat(repository.findByUsername("user"), is(user));
    }

    @Test
    public void whenFindUserByEmailThenGetUSer() {
        UserRepository repository = UserRepository.instOf();
        User user = User.of("user", "pass", "user@gmail.com");
        repository.addUser(user);
        assertThat(repository.findByEmail("user@gmail.com"), is(user));
    }
}