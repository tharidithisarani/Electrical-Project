package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class MachineFormController {

    @FXML
    private JFXButton btnAdd1;

    @FXML
    private JFXButton btnClear1;

    @FXML
    private JFXButton btnDelete1;

    @FXML
    private JFXButton btnUpdate1;

    @FXML
    private JFXComboBox<?> cmbTechID;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colMachineName;

    @FXML
    private TableColumn<?, ?> colTechID;

    @FXML
    private Label lblMachineCode;

    @FXML
    private Label lblTechName;

    @FXML
    private TableView<?> tblMachine;

    @FXML
    private TextArea txtDescription;

    @FXML
    private JFXTextArea txtSupplierName;

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

}
