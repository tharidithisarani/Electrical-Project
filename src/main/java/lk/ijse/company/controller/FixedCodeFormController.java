package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.company.database.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FixedCodeFormController {

    @FXML
    private JFXButton btnOk;

    @FXML
    private AnchorPane rootFixedCodeLode;

    @FXML
    private TextField txtFixedCode;

    @FXML
    private TextField txtUserName;
    private AnchorPane rootNode;

    @FXML
    void btnOkOnAction(ActionEvent event) throws SQLException {
        String un = txtUserName.getText();
        String fc = txtFixedCode.getText();

        try {
            checkCredential(un, fc);
        } catch (SQLException | IOException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    private void checkCredential(String un, String fc) throws SQLException, IOException {
        String sql = "SELECT user_Name, fixed_Code FROM user WHERE user_Name = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, un);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            String dbfc = resultSet.getString(2);
            if (dbfc.equals(fc)){
                goToTheDashboard();
            }else{
                new Alert(Alert.AlertType.ERROR, "The Code you entered is INCORRECT, Try again...!").show();
            }
        }else {
            new Alert(Alert.AlertType.INFORMATION, "The username entered can't be found.").show();
        }
    }

    private void goToTheDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard.fxml"));
        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

}
