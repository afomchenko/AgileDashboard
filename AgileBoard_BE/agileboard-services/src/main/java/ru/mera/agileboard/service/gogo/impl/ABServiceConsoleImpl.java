package ru.mera.agileboard.service.gogo.impl;

import org.apache.felix.scr.annotations.*;
import org.apache.felix.service.command.Descriptor;
import ru.mera.agileboard.model.Task;
import ru.mera.agileboard.model.User;
import ru.mera.agileboard.service.TaskService;
import ru.mera.agileboard.service.UserService;
import ru.mera.agileboard.service.gogo.ABServiceConsole;

import java.util.List;

/**
 * Created by antfom on 10.02.2015.
 */
@Component(name = "ru.mera.agileboard.service.gogo.BTServiceProxyComponent", immediate = true)
@Service(value = ru.mera.agileboard.service.gogo.ABServiceConsole.class)
@Properties({
        @Property(name = "osgi.command.scope", value = "bt"),
        @Property(name = "osgi.command.function", value = {"adduser", "addtask", "gettasks", "finduser", "assigntask"})
})
public class ABServiceConsoleImpl implements ABServiceConsole {

    @Reference(cardinality = ReferenceCardinality.OPTIONAL_UNARY, policy = ReferencePolicy.DYNAMIC, name = "UserServiceRef")
    private volatile UserService userService;

    @Reference(cardinality = ReferenceCardinality.OPTIONAL_UNARY, policy = ReferencePolicy.DYNAMIC, name = "TasksServiceRef")
    private volatile TaskService taskService;


    @Activate
    public void start() {
        System.err.println("service gogo sarted");
    }

    @Deactivate
    public void stop() {
        System.err.println("service gogo stopped");
    }

    @Override
    @Descriptor("Add a new user")
    public final String adduser(@Descriptor("user name") String name, @Descriptor("user email") String email) {
        if (userService != null) {
            User user = userService.createUser(name, email);
            return "User " + name + " created with id " + user.getId();
        }
        return "user service not found";
    }

    @Override
    @Descriptor("Add a new task")
    public final String addtask(@Descriptor("tsk description") String taskDesc) {
        if (taskService != null) {

        }
        return "task service not found";
    }

    @Override
    @Descriptor("Find tasks by user name or id")
    public List<Task> gettasks(@Descriptor("find by: ALL, USERID, TASKID, USERNAME, USEREMAIL, TASKDESC, TASKSTATUS, GROUPID, ROLEID") String findBy,
                               @Descriptor("name or id") String attr) {
        if (taskService != null) {
            return taskService.getTasks(TaskService.Filter.valueOf(findBy.toUpperCase()), attr);
        }
        throw new IllegalStateException();
    }

    @Override
    @Descriptor("Find tasks by user name or id")
    public List<Task> gettasks(String filter) {
        if (taskService != null) {
            return taskService.getTasks(TaskService.Filter.ALL, " ");
        }
        throw new IllegalStateException();
    }

    @Override
    @Descriptor("Find user by  name")
    public User finduser(@Descriptor("name") String name) {
//        if(userService!=null) {
//            return userService.findUserByName(name);
//        }
        throw new IllegalStateException("user service not found");
    }


    @Override
    @Descriptor("Find user by id")
    public User finduser(@Descriptor("id") int id) {
        if (userService != null) {

            return userService.findUserByID(id).get();
        }
        throw new IllegalStateException("user service not found");
    }

    @Override
    public String assigntask(int taskID, String groupMember, int assignedID) {
//        if(taskService!=null) {
//                GroupMemberEnum groupMemberEnum = GroupMemberEnum.valueOf(groupMember);
//                Task task = taskService.getTaskByID(taskID);
//                switch (groupMemberEnum){
//                    case GROUP:
//                        task.assignTask(groupService.findGroupById(assignedID));
//                        break;
//                    case USER:
//                        task.assignTask(groupService.createGroup(userService.findUserByID(assignedID)));
//                        break;
//                    case ROLE:
//                        task.assignTask(groupService.createGroup(roleService.findRoleByID(assignedID)));
//                        break;
//                }
//                task.store();
//                return "task " + taskID + " assigned to " + assignedID;
//        }
        throw new IllegalStateException("user service not found");
    }
}
