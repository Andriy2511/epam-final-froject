package com.example.finalproject.dao.mysql;

import com.example.finalproject.command.admin.AddProductCommand;
import com.example.finalproject.dao.GenericDAO;
import com.example.finalproject.dao.IRoleDAO;
import com.example.finalproject.dao.query.DBQuery;
import com.example.finalproject.models.Role;
import com.example.finalproject.utils.JDBCUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAO extends GenericDAO<Role> implements IRoleDAO {
    private static final Logger logger = LogManager.getLogger(RoleDAO.class);
    private RoleDAO(){}
    private static RoleDAO instance;
    public static IRoleDAO getInstance() {
        if (instance == null)
            instance = new RoleDAO();
        return instance;
    }
    @Override
    public int getAdminId() {
        int id = 0;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_ROLE_ADMIN_ID)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                id = rs.getInt("id");
            }
        } catch (SQLException | ClassNotFoundException | NamingException e){
            logger.error(e);
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public int getUserId() {
        int id = 0;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_ROLE_USER_ID)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                id = rs.getInt("id");
            }
        } catch (SQLException | ClassNotFoundException | NamingException e){
            logger.error(e);
            e.printStackTrace();
        }
        return id;
    }

    @Override
    protected Role mapToEntity(ResultSet rs) throws SQLException {
        return new Role(rs.getInt("id"), rs.getString("name"));
    }

    @Override
    protected PreparedStatement mapFromEntity(PreparedStatement preparedStatement, Role role) throws SQLException {
        preparedStatement.setInt(1, role.getId());
        preparedStatement.setString(2, role.getName());
        return preparedStatement;
    }
}
