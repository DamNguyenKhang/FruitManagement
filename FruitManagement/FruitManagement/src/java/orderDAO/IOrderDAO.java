/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package orderDAO;

import java.sql.SQLException;
import java.util.List;
import model.Order;

/**
 *
 * @author DELL
 */
public interface IOrderDAO {
    public void insertOrder(Order order) throws SQLException;
    public Order getOrderById(int id);
    public List<Order> selectAllOrders();
    public boolean updateOrder(Order order) throws SQLException;
    public boolean deleteOrder(int id) throws SQLException;
}
