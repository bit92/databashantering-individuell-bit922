package se.systementor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    String url = "jdbc:mysql://localhost:3306/projectdb";
    String user = "root";
    String password = "Bks1992?????";


    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }

    public List<Product> activeProducts(){
        List<Product> products = new ArrayList<Product>();

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from products");

            while (rs.next()) {
                //objects
                Product product = new Product();
                product.setProductName(rs.getString("productName"));
                product.setPrice(rs.getDouble("price"));
                products.add(product);

            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

}
