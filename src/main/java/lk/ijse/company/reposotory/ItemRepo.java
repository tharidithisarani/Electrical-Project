/*
package lk.ijse.company.reposotory;

import lk.ijse.company.database.DbConnection;
import lk.ijse.company.model.Item;
import lk.ijse.company.model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRepo {

    public static boolean add(Item item) throws SQLException {
        String sql = "INSERT INTO item VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, item.getCode());
        pstm.setObject(2, item.getName());
        pstm.setObject(3, item.getStatus());
        pstm.setObject(4, item.getUnitPrice());
        pstm.setObject(5, item.getQTY());
        pstm.setObject(6,  item.getUserID());

//        return  pstm.executeUpdate() >0;
        int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }
    }
*/
/*
    public static boolean update(Item item) throws SQLException {
        String sql = "UPDATE item SET name = ?, status = ?, unitPrice = ?, QTY = ?, user_ID = ? WHERE code = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, item.getName());
    pstm.setString(2, item.getStatus());
    pstm.setDouble(3, Double.parseDouble(item.getUnitPrice()));
    pstm.setInt(4, Integer.parseInt(item.getQTY()));
    pstm.setInt(5, 1); // Set user_ID to 1
    pstm.setInt(6, Integer.parseInt(item.getCode()));

        int affectedRows = pstm.executeUpdate();
    return affectedRows > 0;
    }

 *//*

public static boolean update(Item item) throws SQLException {
    // SQL query to update the item details in the database
    String sql = "UPDATE item SET name = ?, status = ?, unit_price = ?, qty = ?, user_ID = ? WHERE code = ?";
    PreparedStatement pstm = null;
    int affectedRows = 0;

    try {
        // Get the database connection and prepare the SQL statement
        pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        // Set the parameters for the SQL statement
        pstm.setString(1, item.getName());           // Set the 'name' parameter
        pstm.setString(2, item.getStatus());         // Set the 'status' parameter
        pstm.setDouble(3, Double.parseDouble(item.getUnitPrice()));      // Set the 'unit_price' parameter
        pstm.setInt(4, Integer.parseInt(item.getQTY()));               // Set the 'qty' parameter
        pstm.setInt(5, 1);                           // Set the 'user_ID' parameter to 1
        pstm.setInt(6, Integer.parseInt(item.getCode()));              // Set the 'code' parameter (condition for update)

        // Execute the update statement and get the number of affected rows
        affectedRows = pstm.executeUpdate();

        // Check if the update was successful
        if (affectedRows > 0) {
            // If one or more rows were updated, return true
            return true;
        } else {
            // If no rows were updated, return false
            return false;
        }
    } catch (SQLException e) {
        // Print the stack trace for debugging
        e.printStackTrace();
        // Throw the exception to be handled by the caller
        throw e;
    } finally {
        // Ensure that the PreparedStatement is closed to release resources
        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

    public static List<Item> getAll() throws SQLException {
        String sql = "SELECT * FROM item";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Item> itemsList = new ArrayList<>();
        while (resultSet.next()){
            String code = resultSet.getString(1);
            String name = resultSet.getString(2);
            String status = resultSet.getString(3);
            String unitPrice = resultSet.getString(4);
            String QTY = resultSet.getString(5);
            String ID = resultSet.getString(6);

            Item item = new Item(code, name, status, unitPrice, QTY, ID);
            itemsList.add(item);
        }
        return itemsList;
    }


    public static boolean updateQty(List<OrderDetail> odList) throws SQLException {
        for (OrderDetail od : odList) {
            if(!updateQty(od)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(OrderDetail od) throws SQLException {
        String sql = "UPDATE items SET qty_on_hand = qty_on_hand - ? WHERE code = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, od.getQty());
        pstm.setString(2, od.getItemCode());

        return pstm.executeUpdate() > 0;
    }

    public static List<String> getCodes() throws SQLException {
    String sql = "SELECT code FROM item";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        List<String> codeList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            codeList.add(resultSet.getString(1));
        }
        return codeList;
    }

    public static Item searchByCode(String code) throws SQLException {
        String sql = "SELECT * FROM item WHERE code = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, code);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
        }
        return null;
    }
}
*/
