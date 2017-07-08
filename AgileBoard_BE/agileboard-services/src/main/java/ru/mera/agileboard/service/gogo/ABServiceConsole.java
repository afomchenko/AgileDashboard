package ru.mera.agileboard.service.gogo;


import ru.mera.agileboard.model.Task;
import ru.mera.agileboard.model.User;

import java.util.List;

/**
 * Created by antfom on 10.02.2015.
 */
public interface ABServiceConsole {
    String SCOPE = "bt";

    String adduser(String name, String email);

    String addtask(String taskDesc);

    String init();

    String initAll();

    public List<Task> gettasks(String filter, String attr);


    public List<Task> gettasks(String filter);

    User finduser(String name);

    User finduser(int id);

    String assigntask(int taskID, String groupMember, int assignedID);

    public enum GroupMemberEnum {
        GROUP, USER, ROLE;
    }
}
