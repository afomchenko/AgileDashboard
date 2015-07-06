package ru.mera.agileboard.service.impl;

import com.j256.ormlite.dao.Dao;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Service;
import ru.mera.agileboard.model.User;
import ru.mera.agileboard.model.impl.UserImpl;
import ru.mera.agileboard.service.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by antfom on 10.02.2015.
 */
@Component(name = "ru.mera.agileboard.service.UserServiceComponent", immediate = true)
@Service(value = ru.mera.agileboard.service.UserService.class)
public class UserServiceImpl implements UserService {

    @Activate
    public void start() {
    }

    @Deactivate
    public void stop() {
    }

    @Override
    public User createUser(String name, String email) {
        User user = new UserImpl(name, email);
        user.store();
        return user;
    }


    @Override
    public Optional<User> findUserByName(String name) {

        User user = null;
        Dao<UserImpl, Integer> dao = UserImpl.getDao();

        try {
            user = dao.queryForFirst(dao.queryBuilder().where().eq("user_name", name).prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(user);
    }


    @Override
    public Optional<User> findUserByID(int id) {

        User user = null;
        Dao<UserImpl, Integer> dao = UserImpl.getDao();

        try {
            user = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAllUsers() {

        Dao<UserImpl, Integer> dao = UserImpl.getDao();

        try {
            List<User> users = new ArrayList<>(dao.queryForAll());
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

}
