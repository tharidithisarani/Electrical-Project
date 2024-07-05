package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.company.database.DbConnection;
import lk.ijse.company.model.Supplier;
import lk.ijse.company.model.tm.SupplierTm;

import java.io.IOException;
import java.sql.*;

public class SupplierFormController {
    @FXML
    private JFXButton btnAddNewCompany;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnItem;

    @FXML
    private Label lblSupID;

    @FXML
    private AnchorPane rootSupplier;

    @FXML
    private TableView<SupplierTm> tblCompany;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    public void initialize(){
        tblCompany.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblCompany.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCompany.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblCompany.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("email"));
        tblCompany.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("description"));

        initUI();

        tblCompany.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtCode.setText(newValue.getCode());
                txtName.setText(newValue.getName());
                txtContact.setText(newValue.getContact());
                txtEmail.setText(newValue.getEmail());
                txtDescription.setText(newValue.getDescription());

                txtCode.setDisable(false);
                txtName.setDisable(false);
                txtContact.setDisable(false);
                txtEmail.setDisable(false);
                txtDescription.setDisable(false);
            }
        });

        txtDescription.setOnAction(event -> btnSave.fire());
        loadAllItems();
    }

    private void initUI() {
        txtCode.clear();
        txtName.clear();
        txtContact.clear();
        txtEmail.clear();
        txtDescription.clear();

        txtCode.setDisable(true);
        txtName.setDisable(true);
        txtContact.setDisable(true);
        txtEmail.setDisable(true);
        txtDescription.setDisable(true);

        txtCode.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void loadAllItems() {
        tblCompany.getItems().clear();
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Company");
            while (rst.next()) {
                tblCompany.getItems().add(new SupplierTm(rst.getString("code"), rst.getString("name"), rst.getString("contact"), rst.getString("email"), rst.getString("description")));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnItemOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/item.fxml"));
        Stage stage = (Stage) rootSupplier.getScene().getWindow();
        rootSupplier.getChildren().clear();
        rootSupplier.getChildren().add(rootNode);
        stage.setTitle("Item Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnAddNew_OnAction(ActionEvent event) {
        txtCode.setDisable(false);
        txtName.setDisable(false);
        txtContact.setDisable(false);
        txtEmail.setDisable(false);
        txtDescription.setDisable(false);

        txtCode.clear();
        txtCode.setText(generateNewId());
        txtName.clear();
        txtContact.clear();
        txtEmail.clear();
        txtDescription.clear();

        txtName.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblCompany.getSelectionModel().clearSelection();
    }

    private String generateNewId() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            ResultSet rst = connection.createStatement().executeQuery("SELECT code FROM Company ORDER BY code DESC LIMIT 1;");
            if (rst.next()) {
                String id = rst.getString("code");
                int newItemId = Integer.parseInt(id.replace("C00-", "")) + 1;
                return String.format("C00-%03d", newItemId);
            } else {
                return "C00-001";
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return "C00-001";
    }

    @FXML
    void btnDelete_OnAction(ActionEvent event) {
        String code = tblCompany.getSelectionModel().getSelectedItem().getCode();
        try {
            if (!existCompant(code)) {
                new Alert(Alert.AlertType.ERROR, "There is no such company associated with the id " + code).show();
            }
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM Company WHERE code=?");
            pstm.setString(1, code);
            pstm.executeUpdate();

            tblCompany.getItems().remove(tblCompany.getSelectionModel().getSelectedItem());
            tblCompany.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the company " + code).show();
        }
    }

    @FXML
    void btnSave_OnAction(ActionEvent event) {
        String code = txtCode.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String description = txtDescription.getText();

        if (!name.matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtDescription.requestFocus();
            return;
        } else if (!txtContact.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid contact number").show();
            txtContact.requestFocus();
            return;
        }

        /*String qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);*/


        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {
                if (existCompant(code)) {
                    new Alert(Alert.AlertType.ERROR, code + " already exists").show();
                }
                //Save Item
                Connection connection = DbConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement("INSERT INTO Company (code, name, contact, email, description) VALUES (?,?,?,?,?)");
                pstm.setString(1, code);
                pstm.setString(2, name);
                pstm.setString(3, contact);
                pstm.setString(4, email);
                pstm.setString(5, description);
                pstm.executeUpdate();
                tblCompany.getItems().add(new SupplierTm(code, name, contact, email, description));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            try {

                if (!existCompant(code)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such Company associated with the id " + code).show();
                }
                /*Update Item*/
                Connection connection = DbConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement("UPDATE Company SET name=?, contact=?, email=?, description=? WHERE code=?");
                pstm.setString(1, name);
                pstm.setString(2, contact);
                pstm.setString(3, email);
                pstm.setString(4, description);
                pstm.setString(5, code);
                pstm.executeUpdate();

                SupplierTm selectSupplier = tblCompany.getSelectionModel().getSelectedItem();
//
                System.out.println("kkk");
                selectSupplier.setName(name);
                selectSupplier.setContact(contact);
                selectSupplier.setEmail(email);
                selectSupplier.setDescription(description);
                tblCompany.refresh();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

        btnAddNewCompany.fire();
    }

    private boolean existCompant(String code) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT code FROM Company WHERE code=?");
        pstm.setString(1, code);
        return pstm.executeQuery().next();
    }

    public void btnTechDetail_OnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/item.fxml"));
        Stage stage = (Stage) rootSupplier.getScene().getWindow();
        rootSupplier.getChildren().clear();
        rootSupplier.getChildren().add(rootNode);
        stage.setTitle("Item Form");
        stage.centerOnScreen();
    }
}
