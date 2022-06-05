package com.epam.service;

import com.epam.models.User;

public class UserCreator {
    private static final String TEST_DATA_USERNAME = "testdata.username";
    private static final String TEST_DATA_PASSWORD = "testdata.password";

    public static User withCredentialsFromProperty(String environment) {
        return new User(DataReader.getData(environment, TEST_DATA_USERNAME),
                DataReader.getData(environment, TEST_DATA_PASSWORD));
    }
}
