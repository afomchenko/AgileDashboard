package ru.mera.agileboard.model.impl;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import ru.mera.agileboard.db.StorageService;
import ru.mera.agileboard.model.Project;
import ru.mera.agileboard.model.Task;
import ru.mera.agileboard.model.TaskBuilder;
import ru.mera.agileboard.model.TaskPriority;
import ru.mera.agileboard.model.TaskStatus;
import ru.mera.agileboard.model.TaskType;
import ru.mera.agileboard.model.User;
import ru.mera.agileboard.service.UserSessionService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@DatabaseTable(tableName = "ab_tasks")
public class TaskImpl extends AbstractABEntity implements Task {

    @DatabaseField(columnName = "task_id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "project_id", foreign = true, foreignAutoRefresh = true)
    private ProjectImpl project;
    @DatabaseField(columnName = "task_name")
    private String name;
    @DatabaseField(columnName = "task_desc", dataType = DataType.LONG_STRING)
    private String description;
    @DatabaseField(columnName = "task_teststeps", dataType = DataType.LONG_STRING)
    private String testSteps;
    @DatabaseField(columnName = "task_created")
    private long created;
    @DatabaseField(columnName = "task_updated")
    private long updated;
    @DatabaseField(columnName = "task_status_id", foreign = true, foreignAutoRefresh = true)
    private TaskStatusImpl status;
    @DatabaseField(columnName = "task_type_id", foreign = true, foreignAutoRefresh = true)
    private TaskTypeImpl type;
    @DatabaseField(columnName = "task_priority_id", foreign = true, foreignAutoRefresh = true)
    private TaskPriorityImpl priority;
    @DatabaseField(columnName = "task_estimated")
    private int estimated;
    @DatabaseField(columnName = "user_created_id", foreign = true, foreignColumnName = "user_id", foreignAutoRefresh = true)
    private UserImpl creator;
    @DatabaseField(columnName = "user_assignee_id", foreign = true, foreignColumnName = "user_id", foreignAutoRefresh = true)
    private UserImpl assignee;
    @ForeignCollectionField(columnName = "ab_tasks_tags", eager = true)
    private Collection<TaskTagImpl> tags;


    private TaskImpl(int id) {
        this.setId(id);
    }


    public TaskImpl() {
    }

//    @Override
//    protected int update() {
//        updated = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
//        return super.update();
//    }
//
//    @Override
//    protected int insert() {
//        created = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
//        updated = created;
//        return super.insert();
//    }

    public static Dao<TaskImpl, Integer> getDao() {
        StorageService storage = StorageSingleton.getStorage();
        Dao<TaskImpl, Integer> dao = DaoManager.lookupDao(storage.getConnection(), TaskImpl.class);
        if (dao == null) {
            try {
                dao = DaoManager.createDao(storage.getConnection(), TaskImpl.class);
                DaoManager.registerDao(storage.getConnection(), dao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return dao;
    }

    public int getCreatorID() {
        return creator.getId();
    }

    public int getAssigneeID() {
        return assignee.getId();
    }

    public int getProjectID() {
        return project.getId();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTestSteps() {
        return testSteps;
    }

    @Override
    public void setTestSteps(String testSteps) {
        this.testSteps = testSteps;
    }

    @Override
    public long getUpdated() {
        return updated;
    }

    @Override
    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(TaskStatus status) {
        this.status = (TaskStatusImpl) status;
    }

    @Override
    public TaskType getType() {
        return type;
    }

    @Override
    public void setType(TaskType type) {
        this.type = (TaskTypeImpl) type;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = (TaskPriorityImpl) priority;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public long getCreated() {
        return created;
    }

    @Override
    public void setCreated(long created) {
        this.created = created;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = (ProjectImpl) project;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = (UserImpl) creator;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = (UserImpl) assignee;
    }

    public int getEstimated() {
        return estimated;
    }

    public void setEstimated(int estimated) {
        this.estimated = estimated;
    }

    public void addTag(String tagString) {
        TaskTagImpl tag = new TaskTagImpl(tagString);
        tag.setTask(this);
        tag.store();
//        tags.add(tag);
    }

    public int getStatusID() {
        return status.getId();
    }

    public int getTypeID() {
        return type.getId();
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean delete() {
        try {
            getDao().delete(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


//    @Override
//    public Dao<TaskImpl, Integer> getDao() {
//        Dao <TaskImpl, Integer> dao = DaoManager.lookupDao(storage.getConnection(), TaskImpl.class);
//        if (dao==null){
//            try {
//                dao = DaoManager.createDao(storage.getConnection(), TaskImpl.class);
//                DaoManager.registerDao(storage.getConnection(), dao);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return dao;
//    }

    @Override
    public int hashCode() {
        if (getId() < 1) {
            throw new IllegalStateException("id is not assigned");
        }
        int result = getId();
        result = 31 * result + (int) created;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaskImpl task = (TaskImpl) o;

        if (getId() != task.getId()) {
            return false;
        }
        return created == task.created;

    }

    protected int update() {
        try {
            setUpdated(Instant.now().toEpochMilli());
            getDao().update(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getId();
    }

    protected int insert() {
        try {
            getDao().create(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getId();
    }

    /**
     * Created by antfom on 17.02.2015.
     */
    public static class TaskBuilderImpl implements TaskBuilder {

        private UserSessionService session;
        private Task task;

        public TaskBuilderImpl(UserSessionService session) {
            this.session = session;
            task = new TaskImpl();
        }


        public TaskBuilder name(String name) {
            task.setName(name);
            return this;
        }

        public TaskBuilder description(String description) {
            task.setDescription(description);
            return this;
        }

        public TaskBuilder testSteps(String testSteps) {
            task.setTestSteps(testSteps);
            return this;
        }

        public TaskBuilder created(long created) {
            task.setCreated(created);
            return this;
        }

        public TaskBuilder updated(long updated) {
            task.setUpdated(updated);
            return this;
        }

        public TaskBuilder type(TaskType type) {
            task.setType(type);
            return this;
        }

        public TaskBuilder status(TaskStatus status) {
            task.setStatus(status);
            return this;
        }

        public TaskBuilder priority(TaskPriority priority) {
            task.setPriority(priority);
            return this;
        }


        public TaskBuilder project(Project project) {
            task.setProject(project);
            return this;
        }

        public TaskBuilder creator(User user) {
            task.setCreator(user);
            return this;
        }

        public TaskBuilder assignee(User user) {
            task.setAssignee(user);
            return this;
        }

        public TaskBuilder estimated(int estimated) {
            task.setEstimated(estimated);
            return this;
        }

        public Task build() {
//            User creator = session.getUserSession();
//            if(creator!=null){
//                task.setCreator(creator);
//            }
//            else{
//                throw new NotAuthorizedException("You Don't Have Permission");
//            }

            if (task.getStatus() == null) {
                Dao<TaskStatusImpl, Integer> stDao = TaskStatusImpl.getDao();

                try {
                    task.setStatus(stDao.queryForId(1));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (task.getCreated() == 0) {
                task.setCreated(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
                task.setUpdated(task.getCreated());
            }

            if (task.getAssignee() == null) {
                task.setAssignee(task.getCreator());
            }
            return task;
        }


    }
}
