/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderDAO;

import dao.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Order;
import userDAO.UserDAO;

/**
 *
 * @author DELL
 */
public class OrderDAO implements IOrderDAO {
    private UserDAO userDAO = new UserDAO();
    private static final String SELECT_ALL_ORDERS = "SELECT * FROM Orders";
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM Orders WHERE OrderID = ?";
    private static final String INSERT_ORDER = "INSERT INTO Orders (CustomerID, TotalAmount, Status) VALUES (?, ?, ?)";
    private static final String UPDATE_ORDER = "UPDATE Orders SET user_id = ?, TotalAmount = ?, Status = ? WHERE OrderID = ?";
    private static final String DELETE_ORDER = "DELETE Orders WHERE id = ?";
    private static final String INSERT_OrderDetails = "INSERT INTO OrderDetails (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

    public void insertOrder(Order order) throws SQLException {
        String sql = INSERT_ORDER;
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setInt(1, order.getUser().getUserID());
            ptm.setDouble(2, order.getTotalAmount());
            ptm.setString(3, order.getStatus());
            ptm.executeQuery();
        } catch (SQLException e) {
            throw new SQLException("Failed to insert order");
        }
    }

    public Order getOrderById(int id) {
        String sql = SELECT_ORDER_BY_ID;
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setInt(1, id);
            ResultSet rs = ptm.executeQuery();
            if (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("id"));
                order.setUser(userDAO.selectUser(rs.getInt("CustomerID")));
                order.setTotalAmount(rs.getDouble("TotalAmount"));
                order.setStatus(rs.getString("Status"));
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Order> selectAllOrders() {
        String sql = SELECT_ALL_ORDERS;
        List<Order> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ptm = con.prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("OrderID"));
                order.setUser(userDAO.selectUser(rs.getInt("CustomerID")));
                order.setTotalAmount(rs.getDouble("TotalAmount"));
                order.setStatus(rs.getString("Status"));
                list.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateOrder(Order order) throws SQLException {
//        String sql = UPDATE_ORDER;
//        try(Connection con = DBConnection.getConnection()){
//            PreparedStatement ptm = con.prepareStatement(sql);
//            ptm.set
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
        boolean afftedRows = false;
        return afftedRows;
    }

    public boolean deleteOrder(int id) throws SQLException {
        String sql = DELETE_ORDER;
        boolean affectedRows = false;
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setInt(1, id);
            affectedRows = ptm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    public void addOrderDetails(int orderId, int productId, int quantity, Double price) {
        String sql = INSERT_OrderDetails;
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setInt(1, orderId);
            ptm.setInt(2, productId);
            ptm.setInt(3, quantity);
            ptm.setDouble(4, price);
            ptm.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int createOrder(Order order) {
        String sql = INSERT_ORDER;
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setInt(1, order.getUser().getUserID());
            ptm.setDouble(2, order.getTotalAmount());
            ptm.setString(3, order.getStatus());
            int affectedRows = ptm.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKey = ptm.getGeneratedKeys()) {
                    if (generatedKey.next()) {
                        return generatedKey.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
