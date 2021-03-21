package service;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    /**
     * batchExecutorType
     * @param userList
     * @return
     */
    @Override
    public boolean batchInsert(List<User> userList) throws Exception {
        boolean isSuccess = true;
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            sqlSession.getConnection().setAutoCommit(false);
            for (User user : userList) {
                sqlSession.insert("mapper.Mapper.insertUser", user);
            }
        } catch (Exception e) {
            isSuccess = false;
            sqlSession.rollback();
            throw e;
        } finally {
            if (sqlSession != null) {
                if (isSuccess) {
                    sqlSession.commit();
                }
                sqlSession.close();
            }
        }
        return false;
    }
}
