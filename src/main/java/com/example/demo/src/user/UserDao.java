package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers(){
        String getUsersQuery = "select * from User_Table";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getInt("Id"),
                        rs.getString("UserName"),
                        rs.getString("UserId"),
                        rs.getString("Password"),
                        rs.getString("Email"))
        );
    }

    public List<GetUserRes> getUsersByEmail(String Email){
        String getUsersByEmailQuery = "select * from User_Table where Email =?";
        String getUsersByEmailParams = Email;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("Id"),
                        rs.getString("UserName"),
                        rs.getString("UserId"),
                        rs.getString("Password"),
                        rs.getString("Email")),
                getUsersByEmailParams);
    }

    public GetUserRes getUser(int Id){
        String getUserQuery = "select * from User_Table where Id = ?";
        int getUserParams = Id;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("Id"),
                        rs.getString("UserName"),
                        rs.getString("UserId"),
                        rs.getString("Password"),
                        rs.getString("Email")),
                getUserParams);
    }


    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into User_Table (UserName, UserId, PassWord, Email) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getUserName(), postUserReq.getUserId(), postUserReq.getPassWord(), postUserReq.getEmail()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int checkEmail(String Email){
        String checkEmailQuery = "select exists(select Email from User_Table where Email = ?)";
        String checkEmailParams = Email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update User_Table set UserName = ? where Id = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getId()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select Id, PassWord,Email,UserName,UserId from User_Table where UserId = ?";
        String getPwdParams = postLoginReq.getUserId();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("Id"),
                        rs.getString("UserName"),
                        rs.getString("UserId"),
                        rs.getString("PassWord"),
                        rs.getString("Email")
                ),
                getPwdParams
        );

    }



}
