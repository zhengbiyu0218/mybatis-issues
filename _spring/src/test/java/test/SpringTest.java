package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import mapper.Mapper;
import service.UserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SpringTest {

    @Autowired
    private Mapper mapper;

    @Test
    public void shouldGetAUser() {
        User user = mapper.getUser(1);
        assertEquals("User1", user.getName());
    }

    @Test
    public void shouldInsertAUser() {
        User user = new User();
        user.setId(2);
        user.setName("User2");
        mapper.insertUser(user);
    }


    @Resource
    UserService userService;

    @Test
    public void batchInsertUser() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "user1"));
        userList.add(new User(2, "user2"));
        userList.add(new User(3, "user3"));
        userList.add(new User(4, "user4"));
        try {
            userService.batchInsert(userList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Prove that the transaction has not been committed
        User user = mapper.getUser(3);
        assertEquals("User3", user.getName());
    }

}
