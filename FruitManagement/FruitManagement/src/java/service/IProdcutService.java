package service;

import model.Product;

import java.sql.SQLException;
import java.util.List;

public interface IProdcutService {
    List<Product> getAllProducts();
    Product getProductById(int id);
    Product getProductByName(String name);
    boolean deleteProduct(int id);
    void addProduct(Product product) throws SQLException;
    boolean updateProduct(Product product) throws SQLException;
    List<Product> getProductsByCategory(String categoryName);
    List<Product> getProductWithSales(String name,  String category);
}
