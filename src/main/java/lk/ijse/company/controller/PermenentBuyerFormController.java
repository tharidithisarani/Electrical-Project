package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class PermenentBuyerFormController {

    @FXML
    private JFXButton btnAdd;

    /*@FXML
    private JFXButton btnBack;*/

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private Label lblCusID;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblRegID;

    @FXML
    private Label lblTime1;

    @FXML
    private AnchorPane rootPermanentBuyerLode;

    @FXML
    private JFXTextArea txtAddress;

    @FXML
    private JFXTextArea txtContact;

    @FXML
    private JFXTextArea txtName;

    @FXML
    private JFXTextArea txtStatus;

    public void initialize(){
        nextRegesterID();
    }

    private void nextRegesterID() {
        /*
        try {
            String currentId = permenentRepo.currentId();
            String nextId = nextId(currentId);

            lblRegID.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

         */
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    public void btnBackOnAction(ActionEvent actionEvent) {
    }
}
