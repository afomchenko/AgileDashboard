package ru.mera.agileboard.model;

import com.j256.ormlite.table.TableUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.mera.agileboard.db.StorageService;
import ru.mera.agileboard.db.impl.StorageServiceImpl;
import ru.mera.agileboard.model.impl.*;
import ru.mera.agileboard.service.LoggingService;
import ru.mera.agileboard.service.ProjectService;
import ru.mera.agileboard.service.TaskService;
import ru.mera.agileboard.service.UserService;
import ru.mera.agileboard.service.impl.LoggingServiceImpl;
import ru.mera.agileboard.service.impl.ProjectServiceImpl;
import ru.mera.agileboard.service.impl.TaskServiceImpl;
import ru.mera.agileboard.service.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.UUID;

public class ABEntityTest {

    @BeforeClass
    public static void initDB() throws Exception {

    }

    @Test
    public void tst() {

    }


}