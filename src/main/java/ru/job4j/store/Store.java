package ru.job4j.store;

import org.hibernate.Session;

import java.util.Properties;
import java.util.function.Function;

public interface Store extends AutoCloseable {
    <T> T tx(final Function<Session, T> command);
    Properties getAppCfg();
}
