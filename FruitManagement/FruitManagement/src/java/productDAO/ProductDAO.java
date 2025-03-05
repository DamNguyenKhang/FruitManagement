/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productDAO;

import dao.DBConnection;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DELL
 */
public class ProductDAO implements IProductDAO {

    public static final String SELECT_PRODUCT_BY_ID =
            "SELECT * "
            + "FROM Products "
            + "WHERE ProductID = ?";
    public static final String SELECT_ALL_PRODUCT =
            "SELECT * " +
            "FROM Products";
    public static final String INSERT_PRODUCT =
            "INSERT INTO Products (Name, Price, Description, ImageURL) " +
            "VALUES (?, ?, ?, ?)";
    public static final String DELETE_PRODUCT_BY_ID =
            "DELETE " +
            "FROM Products " +
            "WHERE ProductID = ?";
    public static final String UPDATE_PRODUCT =
            "UPDATE Products " +
            "SET Name = ?, Price = ?, Description = ?, ImageURL = ?, ImportDate = ? " +
            "WHERE ProductID = ?";
    public static final String SELECT_PRODUCT_BY_NAME =
            "SELECT * " +
            "FROM Products " +
            "WHERE Name = ?";
    public static final String SELECT_PRODUCT_ID_BY_CATEGORY =
            "SELECT p.ProductID, p.ProductName" +
                    "FROM Products p " +
                    "INNER JOIN ProductsCategories pc " +
                    "ON p.ProductID = pc.ProductID" +
                    "INNER JOIN Categories c " +
                    "ON c.CategoryID = pc.CategoryID" +
                    "WHERE pc.CategoryName = ?" +
                    "GROUP BY p.ProductID, p.ProductName";

    public void insertProduct(Product pro) throws SQLException {
        String sql = INSERT_PRODUCT;
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1, pro.getProductName());
            ptm.setDouble(2, pro.getPrice());
            ptm.setString(3, pro.getDescription());
            ptm.setString(4, pro.getImageURL());
            ptm.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public Product selectProduct(int id) {
        String sql = SELECT_PRODUCT_BY_ID;
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setInt(1, id);
            ResultSet rs = ptm.executeQuery();
            if (rs.next()) {
                Product pro = new Product();
                pro.setProductID(rs.getInt("ProductID"));
                pro.setProductName(rs.getString("Name"));
                pro.setDescription(rs.getString("Description"));
                pro.setPrice(rs.getDouble("Price"));
                pro.setImageURL(rs.getString("ImageURL"));
                pro.setImportDate(rs.getTimestamp("ImportDate").toLocalDateTime());
                return pro;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> selectAllProducts() {
        String sql = SELECT_ALL_PRODUCT;
        List<Product> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ptm = con.prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                Product pro = new Product();
                pro.setProductID(rs.getInt("ProductID"));
                pro.setProductName(rs.getString("Name"));
                pro.setDescription(rs.getString("Description"));
                pro.setPrice(rs.getDouble("Price"));
                pro.setImageURL(rs.getString("ImageURL"));

                // Xử lý ImportDate có thể null
                Timestamp importTimestamp = rs.getTimestamp("ImportDate");
                if (importTimestamp != null) {
                    pro.setImportDate(importTimestamp.toLocalDateTime());
                } else {
                    pro.setImportDate(null); // hoặc có thể đặt giá trị mặc định
                }

                list.add(pro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteProduct(int id) {
        String sql = DELETE_PRODUCT_BY_ID;
        boolean affectedRows = false;
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setInt(1, id);
            affectedRows = ptm.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to update product: " + e.getMessage());
        }
        return affectedRows;
    }

    public boolean updateProduct(Product pro) throws SQLException {
        String sql = UPDATE_PRODUCT;
        boolean affectedRows = false;
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1, pro.getProductName());
            ptm.setDouble(2, pro.getPrice());
            ptm.setString(3, pro.getDescription());
            ptm.setString(4, pro.getImageURL());
            if (pro.getImportDate() != null) {
                ptm.setTimestamp(5, Timestamp.valueOf(pro.getImportDate()));
            } else {
                ptm.setNull(5, java.sql.Types.TIMESTAMP);
            }

            ptm.setInt(6, pro.getProductID());
            affectedRows = ptm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("Fail to update product: " + e.getMessage());
        }
        return affectedRows;
    }

    @Override
    public Product selectProduct(String name) {
        Product product;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ptm = conn.prepareStatement(SELECT_PRODUCT_BY_NAME)) {
            ptm.setString(1, name);
            ResultSet rs = ptm.executeQuery();
            if (rs.next()) {
                product = new Product(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getString("Description"), rs.getDouble("Price"), rs.getString("ImageURL"), rs.getTimestamp("ImportDate").toLocalDateTime());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Product> selectProductsByCategory(String category) {
        List<Product> list = null;
        try(Connection conn = DBConnection.getConnection(); PreparedStatement ptm = conn.prepareStatement(SELECT_PRODUCT_ID_BY_CATEGORY)){
            ptm.setString(1, category);
            ResultSet rs = ptm.executeQuery();
            if (rs.next()) {
                list = new ArrayList<>();
                Product pro = selectProduct(rs.getInt("ProductID"));
                list.add(pro);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void main(String[] args) {

    }
}
