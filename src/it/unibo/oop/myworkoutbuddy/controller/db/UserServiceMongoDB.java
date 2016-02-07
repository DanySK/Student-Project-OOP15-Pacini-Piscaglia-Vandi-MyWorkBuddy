package it.unibo.oop.myworkoutbuddy.controller.db;

import it.unibo.oop.myworkoutbuddy.model.User;

public class UserServiceMongoDB extends AbstractGenericService<User> {

    public UserServiceMongoDB(Class<? extends User> clazz) {
        super(clazz);
    }

    @Override
    protected String getCollectionName() {
        return "users";
    }

}
