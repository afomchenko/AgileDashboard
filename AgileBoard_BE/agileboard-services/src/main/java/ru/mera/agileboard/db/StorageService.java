package ru.mera.agileboard.db;

import com.j256.ormlite.support.ConnectionSource;

/**
 * Created by antfom on 09.02.2015.
 */
public interface StorageService {
    ConnectionSource getConnection();
}
