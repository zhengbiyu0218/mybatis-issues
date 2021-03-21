package service;

import test.User;

import java.util.List;

public interface UserService {
    boolean batchInsert(List<User> userList) throws Exception;
}
