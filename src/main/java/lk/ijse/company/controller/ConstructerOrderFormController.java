package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ConstructerOrderFormController {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXComboBox<?> cmbRegCode;

    @FXML
    private JFXComboBox<?> cmbTechnisiyanId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblId;

    @FXML
    private AnchorPane rootConOrder;

    @FXML
    private TableView<?> tblBigOrder;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtAdvance;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtCount;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtFullPayment;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtRemainingPayment;

    @FXML
    void btnAdd_OnAction(ActionEvent event) {

    }

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrder_OnAction(ActionEvent event) {

    }

    @FXML
    void txtQty_OnAction(ActionEvent event) {

    }

}
