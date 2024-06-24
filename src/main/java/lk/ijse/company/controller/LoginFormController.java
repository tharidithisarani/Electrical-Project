package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.company.database.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private JFXButton btnLogin;

    @FXML
    private AnchorPane rootFPLode;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        String un = txtUserName.getText();
        String pw = txtPassword.getText();

        try {
            checkCredential(un, pw);
        } catch (SQLException | IOException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    private void checkCredential(String un, String pw) throws SQLException, IOException {
        String sql = "SELECT user_Name, password FROM user WHERE user_Name = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, un);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            String dbpw = resultSet.getString(2);
            if (dbpw.equals(pw)){
                goToTheDashboard();
            }else{
                new Alert(Alert.AlertType.ERROR, "The password you entered is INCORRECT, Try again...!").show();
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

    @FXML
    void fixedCodeOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/fixedCode.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.show();

    }

}
