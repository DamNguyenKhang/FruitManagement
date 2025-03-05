package service;

import model.Product;
import productDAO.ProductDAO;

import java.sql.SQLException;
import java.util.List;

public class ProductService implements IProdcutService {
    ProductDAO productDAO = new ProductDAO();
    @Override
    public List<Product> getAllProducts() {
        return productDAO.selectAllProducts();
    }

    @Override
    public Product getProductById(int id) {
        return productDAO.selectProduct(id);
    }

    @Override
    public Product getProductByName(String name) {
        return productDAO.selectProduct(name);
    }

    @Override
    public boolean deleteProduct(int id){
        return productDAO.deleteProduct(id);
    }

    @Override
    public void addProduct(Product product) throws SQLException {
        productDAO.insertProduct(product);
    }

    @Override
    public boolean updateProduct(Product product) throws SQLException {
        return productDAO.updateProduct(product);
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        return productDAO.selectProductsByCategory(categoryName);
    }

    @Override
    public List<Product> getProductWithSales(String name, String category) {
        return List.of();
    }
}
