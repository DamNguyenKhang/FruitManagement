/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userDAO;

import dao.DBConnection;
import jakarta.servlet.jsp.jstl.sql.Result;
import java.sql.SQLException;
import java.util.List;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import model.User;
import java.sql.Statement;

/**
 *
 * @author DELL
 */
public class UserDAO implements IUserDAO {

    private static final String LOGIN = "SELECT id, name, role FROM [Users] WHERE name = ? AND password = ?";
    private static final String INSERT_USER = "INSERT INTO Users (userName, password, email) VALUES (?, ?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM Users WHERE userID = ?";
    private static final String UPDATE_USER = "UPDATE Users SET userName = ?, password = ?, email = ?, phone = ?, address = ?, role = ?, registrationDate = ? WHERE userID = ?";
    private static final String DELETE_BY_ID = "DELETE FROM Users WHERE UserID = ?";

    @Override
    public void insertUser(User user) throws SQLException {
        String sql = INSERT_USER;
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1, user.getUserName());
            ptm.setString(2, user.getPassword());
            ptm.setString(3, user.getEmail());
            ptm.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to insert user: " + e.getMessage());
        }
    }

    @Override
    public User selectUser(int id) {
        String sql = SELECT_USER_BY_ID;
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setInt(1, id);
            ResultSet rs = ptm.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("userID"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setRole(rs.getString("role"));
                user.setRegistrationDate(rs.getDate("registrationDate").toLocalDate());
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> selectAllUser() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ptm = con.prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("userID"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setRole(rs.getString("role"));
                user.setRegistrationDate(rs.getDate("registrationDate").toLocalDate());
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted = false;
        String deleteQuery = "DELETE FROM users WHERE id = ?";
        String resetIdentity = "DBCC CHECKIDENT ('users', RESEED, 0)"; // Reset ID sau khi xóa

        try (Connection con = DBConnection.getConnection(); PreparedStatement ptm = con.prepareStatement(deleteQuery)) {

            ptm.setInt(1, id);
            rowDeleted = ptm.executeUpdate() > 0;

            // 2️⃣ Nếu xóa thành công, reset lại ID (nếu bảng vẫn còn dữ liệu)
            if (rowDeleted) {
                try (Statement stmt = con.createStatement()) {
                    stmt.executeUpdate(resetIdentity);
                }
            }
        }
        return rowDeleted;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        String sql = UPDATE_USER;
        boolean rowUpdated = false;
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1, user.getUserName());
            ptm.setString(2, user.getPassword());
            ptm.setString(3, user.getEmail());
            ptm.setString(4, user.getPhone());
            ptm.setString(5, user.getAddress());
            ptm.setString(6, user.getRole());
            ptm.setDate(7, Date.valueOf(user.getRegistrationDate()));
            ptm.setInt(8, user.getUserID());
            rowUpdated = ptm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("Failed to update user: " + e.getMessage());
        }
        return rowUpdated;
    }
    public User checkLogin(String userName, String password){
        String sql = "SELECT * FROM Users WHERE userName = ? AND password = ?";
        try(Connection con = DBConnection.getConnection()){
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1, userName);
            ptm.setString(2, password);
            ResultSet rs = ptm.executeQuery();
            if(rs.next()){
                User user = new User();
                user.setUserID(rs.getInt("userID"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setRole(rs.getString("role"));
                return user;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
