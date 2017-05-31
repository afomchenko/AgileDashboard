package ru.mera.agileboard.model.impl;

import org.apache.felix.scr.annotations.*;
import ru.mera.agileboard.db.StorageService;

import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by antfom on 04.03.2015.
 */
@Component(name = "ru.mera.agileboard.model.Storage", immediate = true)
public class StorageSingleton {


    private  static StorageSingleton instance;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY, policy = ReferencePolicy.STATIC, name = "storage")
    private StorageService storageService;

    public StorageSingleton() {
    }

    private StorageSingleton(StorageService storageService) {
        this.storageService = storageService;
    }

    public static void init(StorageService storageService) {
        instance = new StorageSingleton(storageService);
    }

    public static StorageService getStorage() {
        return instance.getStorageService();
    }

    @Activate
    private void init() {
        instance = new StorageSingleton(storageService);
    }

    public StorageService getStorageService() {
        return storageService;
    }
}
