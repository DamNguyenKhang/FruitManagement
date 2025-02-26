/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Product;
import productDAO.ProductDAO;

/**
 *
 * @author DELL
 */
@WebServlet(name = "ProductServlet", urlPatterns = {"/products"})
public class ProductServlet extends HttpServlet {

    private ProductDAO productDAO;

    public void init() {
        productDAO = new ProductDAO();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    request.getRequestDispatcher("/product/CreateProduct.jsp").forward(request, response);
                    break;
                case "edit":
                    editProduct(request, response);
                    break;
                case "delete":
                    deleteProduct(request, response);
                    break;
                default:
                    listProducts(request, response);
                    break;
            }
        } catch (SQLException e) {
            String currentPage = request.getHeader("referer"); // Lấy trang trước đó
            response.sendRedirect(currentPage + "?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    createProduct(request, response);
                    break;
                case "edit":
                    updateProduct(request, response);
                    break;
            }
        } catch (SQLException e) {
            String currentPage = request.getHeader("referer"); // Lấy trang trước đó
            response.sendRedirect(currentPage + "?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    public void createProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String message = "";
        try {
            Product pro = new Product();
            pro.setProductName(request.getParameter("name"));
            pro.setDescription(request.getParameter("description"));
            pro.setPrice(Double.parseDouble(request.getParameter("price")));
            pro.setImageURL(request.getParameter("imageURL"));
            productDAO.insertProduct(pro);
            message = "Add new product successfully";
            request.setAttribute("message", message);
        } catch (NumberFormatException e) {
            message = e.getMessage();
            request.setAttribute("error", message);
        }
        request.getRequestDispatcher("/product/CreateProduct.jsp").forward(request, response);
    }

    public void editProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productDAO.selectProduct(id);
        request.setAttribute("product", product);
        request.getRequestDispatcher("/product/EditProduct.jsp").forward(request, response);
    }

    public void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        try {
            Product pro = new Product();
            pro.setProductID(Integer.parseInt(request.getParameter("id")));
            pro.setProductName(request.getParameter("name"));
            pro.setDescription(request.getParameter("description"));
            pro.setPrice(Double.parseDouble(request.getParameter("price")));
            pro.setImageURL(request.getParameter("imageURL"));
            String importDateStr = request.getParameter("importDate");
            // Kiểm tra nếu import_date không rỗng, mới chuyển đổi
            LocalDateTime importDate = null;
            if (importDateStr != null && !importDateStr.isEmpty()) {
                importDateStr = importDateStr.replace("T", " ").split("\\.")[0];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                importDate = LocalDateTime.parse(importDateStr, formatter);
            }

            // Gọi DAO để cập nhật sản phẩm
            boolean updated = productDAO.updateProduct(pro);

            if (updated) {
                response.sendRedirect("products");
            } else {
                request.setAttribute("error", "Failed to update product!");
                request.getRequestDispatcher("/product/EditProduct.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("error", "Something went wrong: " + e.getMessage());
            request.getRequestDispatcher("/product/EditProduct.jsp").forward(request, response);
        }
    }

    public void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        productDAO.deleteProduct(id);
        response.sendRedirect("products");
    }

    public void listProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productDAO.selectAllProducts();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/product/listProduct.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
