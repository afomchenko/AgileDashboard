package ru.mera.agileboard.model.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import ru.mera.agileboard.db.StorageService;

/**
 * Created by antfom on 04.03.2015.
 */
@Component(name = "ru.mera.agileboard.model.Storage", immediate = true)
public class StorageSingleton {

    private static StorageSingleton instance;

    @Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, name = "storage")
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
