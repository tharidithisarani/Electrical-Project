package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.company.model.Supplier;
import lk.ijse.company.reposotory.SupplierRepo;

import java.io.IOException;

public class SupplierFormController {

    @FXML
    private JFXButton btnAdd1;

    @FXML
    private JFXButton btnAddItem;

    @FXML
    private JFXButton btnClear1;

    @FXML
    private Label lblSupID;

    @FXML
    private AnchorPane rootSupplier;

    @FXML
    private JFXTextArea txtCompanyName;

    @FXML
    private JFXTextArea txtContaceNum;

    @FXML
    private TextArea txtDescription;

    @FXML
    private JFXTextArea txtEmail;

    @FXML
    private JFXTextArea txtItemCode;

    @FXML
    private JFXTextArea txtQTY;

    @FXML
    private JFXTextArea txtSupplierName;
    private boolean valid;

    @FXML
    void btnAddItemOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/item.fxml"));
        Stage stage = (Stage) rootSupplier.getScene().getWindow();
        rootSupplier.getChildren().clear();
        rootSupplier.getChildren().add(rootNode);
        stage.setTitle("Item Form");
        stage.centerOnScreen();

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

        String itemCode = txtItemCode.getText();
        String CompanyName = txtCompanyName.getText();
        String supplierName = txtSupplierName.getText();
        String description = txtDescription.getText();
        String email = txtEmail.getText();
        String contact = txtContaceNum.getText();
        String qty = txtQTY.getText();

        Supplier supplier = new Supplier( itemCode, CompanyName, supplierName, description, email, contact, qty);

        if (isValid()) {
            try {

                boolean isAdd = SupplierRepo.add(supplier);
                if (isAdd) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Supplier added!").show();
                }

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "something went wrong ").show();
            }
            clearFields();


        }


    }

    private void clearFields() {
        lblSupID.setText("");
        txtItemCode.setText("");
        txtCompanyName.setText("");
        txtSupplierName.setText("");
        txtDescription.setText("");
        txtEmail.setText("");
        txtContaceNum.setText("");
        txtQTY.setText("");
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    public boolean isValid() {
        return valid;
    }
}
