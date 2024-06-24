package lk.ijse.company.reposotory;

import lk.ijse.company.database.DbConnection;
import lk.ijse.company.model.Supplier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierRepo {
    public static boolean add(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

                pstm.setObject(1,supplier.getItemCode());
                pstm.setObject(2,supplier.getCompanyName());
                pstm.setObject(3,supplier.getSupplierName());
                pstm.setObject(4,supplier.getDescription());
                pstm.setObject(5,supplier.getEmail());
                pstm.setObject(6,supplier.getContact());
                pstm.setObject(7,supplier.getQty());

                return pstm.executeUpdate() > 0;
    }




}
