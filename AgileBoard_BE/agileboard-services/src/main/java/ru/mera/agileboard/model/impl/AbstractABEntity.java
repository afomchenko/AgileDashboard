package ru.mera.agileboard.model.impl;

import ru.mera.agileboard.model.ABEntity;

/**
 * Created by antfom on 03.02.2015.
 */
public abstract class AbstractABEntity implements ABEntity {

    @Override
    public int store() {
        if (getId() > 0) {
            update();
        } else {
            insert();
        }
        return getId();
    }

    protected abstract int update();

    protected abstract int insert();


    //
//    protected int update() {
//
//        StringBuilder updateSQL = new StringBuilder("UPDATE ");
//
//        Class cl = this.getClass();
//        if (cl.isAnnotationPresent(Table.class)) {
//            String table = ((Table) cl.getAnnotation(Table.class)).name();
//            updateSQL = updateSQL.append(table).append(" SET ");
//
//            Field[] fields = cl.getDeclaredFields();
//            for (Field field : fields) {
//                if (field.isAnnotationPresent(Cell.class)) {
//                    try {
//                        if (field.getAnnotation(Cell.class).key()) continue;
//                        field.setAccessible(true);
//                        if (field.getType() == boolean.class) {
//                            updateSQL = updateSQL.append(field.getAnnotation(Cell.class).name())
//                                    .append(" = \'").append((boolean) field.get(this) ? "1" : "0").append("\',");
//                        } else {
//                            updateSQL = updateSQL.append(field.getAnnotation(Cell.class).name())
//                                    .append(" = \'").append(String.valueOf(field.get(this))).append("\',");
//                        }
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            updateSQL.setCharAt(updateSQL.length() - 1, ' ');
//            updateSQL = updateSQL.append(" WHERE ");
//
//            for (Field field : fields) {
//                if (field.isAnnotationPresent(Cell.class) && field.getAnnotation(Cell.class).key()) {
//                    try {
//                        field.setAccessible(true);
//                        updateSQL = updateSQL.append(field.getAnnotation(Cell.class).name()).append(" = ")
//                                .append(String.valueOf(field.get(this))).append(";");
//                        break;
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            System.err.println(updateSQL);
//
//            try (Connection dbConnection = getStorage().getDB().getDBConnection();
//                 PreparedStatement preparedStatement = dbConnection.prepareStatement(updateSQL.toString())) {
//                preparedStatement.executeUpdate();
//
//                manyToManyUpdate();
//                return getId();
//
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//        return -1;
//    }
//
//    protected int insert() {
//
//        String insertTableSQL = "INSERT INTO ";
//
//        Class cl = this.getClass();
//        if (cl.isAnnotationPresent(Table.class)) {
//            String table = ((Table) cl.getAnnotation(Table.class)).name();
//            insertTableSQL = insertTableSQL.concat(table).concat(" (");
//
//            int fieldCount = 0;
//
//            Field[] fields = cl.getDeclaredFields();
//            for (Field field : fields) {
//                if (field.isAnnotationPresent(Cell.class)) {
//
//                    if (field.getAnnotation(Cell.class).key()) {
//                        continue;
//                    }
//
//                    insertTableSQL = insertTableSQL.concat(field.getAnnotation(Cell.class).name()).concat(",");
//                    fieldCount++;
//                }
//            }
//
//            if (fieldCount > 0)
//                insertTableSQL = insertTableSQL.substring(0, insertTableSQL.length() - 1).concat(") VALUES (");
//
//
//            for (Field field : fields) {
//                if (field.isAnnotationPresent(Cell.class)) {
//
//                    if (field.getAnnotation(Cell.class).key()) {
//                        continue;
//                    }
//                    try {
//                        field.setAccessible(true);
//                        if (field.getType() == boolean.class) {
//                            insertTableSQL = insertTableSQL.concat("\'").concat((boolean) field.get(this) ? "1" : "0").concat("\',");
//                        } else {
//                            insertTableSQL = insertTableSQL.concat("\'").concat(String.valueOf(field.get(this))).concat("\',");
//                        }
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                    fieldCount++;
//                }
//            }
//
//
//            if (fieldCount > 0)
//                insertTableSQL = insertTableSQL.substring(0, insertTableSQL.length() - 1).concat(");");
//
//
//            System.err.println(insertTableSQL.toString());
//
//            try (Connection dbConnection = getStorage().getDB().getDBConnection();
//                 PreparedStatement preparedStatement = dbConnection.prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS)) {
//                preparedStatement.executeUpdate();
//
//                ResultSet rs = preparedStatement.getGeneratedKeys();
//                if (rs != null && rs.next()) {
//                    int returnedID = rs.getInt(1);
//                    setId(returnedID);
//                    return returnedID;
//                }
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        System.err.println("insertion failed");
//        return -1;
//
//    }
//
//    public boolean delete() {
//
//        clean();
//
//        String delSQL = "DELETE " +
//                "FROM $TABLENAME " +
//                "WHERE $KEYFIELD = $KEYVALUE;";
//
//        Class cl = this.getClass();
//        if (cl.isAnnotationPresent(Table.class)) {
//
//            if (((Table) cl.getAnnotation(Table.class)).nullable()) {
//                try {
//                    Field obsolete = cl.getDeclaredField("obsolete");
//                    obsolete.setAccessible(true);
//                    obsolete.set(this, true);
//                    store();
//                    return true;
//                } catch (NoSuchFieldException | IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            String table = ((Table) cl.getAnnotation(Table.class)).name();
//            delSQL = delSQL.replace("$TABLENAME", table);
//
//
//            String key = "";
//            String keyval = "";
//
//
//            Field[] fields = cl.getDeclaredFields();
//            for (Field field : fields) {
//                if (field.isAnnotationPresent(Cell.class) && field.getAnnotation(Cell.class).key()) {
//
//                    try {
//                        field.setAccessible(true);
//                        key = field.getAnnotation(Cell.class).name();
//                        delSQL = delSQL.replace("$KEYFIELD", key);
//                        keyval = String.valueOf(field.get(this));
//                        delSQL = delSQL.replace("$KEYVALUE", keyval);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                } else if (field.isAnnotationPresent(ManyToMany.class)) {
//                    String delExtSQL = "DELETE FROM " + field.getAnnotation(ManyToMany.class).name() + " WHERE " + key + " = " + keyval + ";";
//                    getStorage().getDB().execSQL(delExtSQL);
//                }
//            }
//        }
//
//        try (Connection dbConnection = getStorage().getDB().getDBConnection();
//             PreparedStatement preparedStatement = dbConnection.prepareStatement(delSQL)) {
//            preparedStatement.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//
//        }
//        return false;
//    }
//
//    protected void clean() {
//        //do nothing
//    }
//
//    public StorageService getStorage() {
//        return storage;
//    }
//
//    protected void manyToManyUpdate() {
//        String delSQL = "DELETE " +
//                "FROM $TABLE " +
//                "WHERE $KEYFIELD = $KEYVALUE;";
//
//
//        String updSQL = "INSERT INTO " +
//                "$TABLE " +
//                "($KEY1,$KEY2) " +
//                "VALUES ($VAL1,$VAL2);";
//
//        Class cl = this.getClass();
//        Field[] fields = cl.getDeclaredFields();
//
//
//        for (Field field : fields) {
//            if (field.isAnnotationPresent(Cell.class) && field.getAnnotation(Cell.class).key()) {
//                field.setAccessible(true);
//                try {
//                    updSQL = updSQL.replace("$KEY1", field.getAnnotation(Cell.class).name())
//                            .replace("$VAL1", String.valueOf(field.get(this)));
//
//                    delSQL = delSQL.replace("$KEYFIELD", field.getAnnotation(Cell.class).name())
//                            .replace("$KEYVALUE", String.valueOf(field.get(this)));
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        for (Field field : fields) {
//            if (field.isAnnotationPresent(ManyToMany.class)) {
//                System.err.println("found many to many");
//                try {
//                    field.setAccessible(true);
//                    String table = field.getAnnotation(ManyToMany.class).name();
//                    Collection entities = (Collection) field.get(this);
//                    updSQL = updSQL.replace("$TABLE", table);
//
//                    delSQL = delSQL.replace("$TABLE", table);
//                    storage.getDB().execSQL(delSQL);
//
//
//                    for (Object entity : entities) {
//                        Class ce = entity.getClass();
//                        Field[] innerFields = ce.getDeclaredFields();
//
//                        for (Field innerField : innerFields) {
//                            if (innerField.isAnnotationPresent(Cell.class) && innerField.getAnnotation(Cell.class).key()) {
//                                innerField.setAccessible(true);
//                                String updSQLEntity = updSQL;
//                                updSQLEntity = updSQL.replace("$KEY2", innerField.getAnnotation(Cell.class).name())
//                                        .replace("$VAL2", String.valueOf(innerField.get(entity)));
//
//                                storage.getDB().execSQL(updSQLEntity);
//                            }
//                        }
//                    }
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
