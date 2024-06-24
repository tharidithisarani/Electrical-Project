package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.company.model.Item;
import lk.ijse.company.model.tm.ItemTm;
import lk.ijse.company.reposotory.ItemRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemFormController {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colQTY;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private AnchorPane rootItem;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextArea txtStatus;

    @FXML
    private TextField txtUserID;


    private List<Item> itemList = new ArrayList<>();

    public void initialize() {
        this.itemList = getAllItem();
        setCellValueFactory();
        loadItemTable();
    }

  /*  private void loadItemTable() {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();

        try {
            List<Item> itemsList = ItemRepo.getAll();
            for (Item customer : itemsList) {
                tmList.add(new tmList(
                        item.getCode(),
                        item.getName(),
                        item.getStatus(),
                        item.getUnitPrice(),
                        item.getQTY(),
                        item.getUserID()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tblCustomer.setItems(customerTMS);
    }

   */


    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("QTY"));
    }

    private void loadItemTable() {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();

        for (Item item : itemList) {
            ItemTm itemTm = new ItemTm(
                    item.getCode(),
                    item.getName(),
                    item.getStatus(),
                    item.getUnitPrice(),
                    item.getQTY(),
                    item.getUserID()
            );

            tmList.add(itemTm);
        }
        tblItem.setItems(tmList);
        ItemTm selectedItem = tblItem.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }



    private List<Item> getAllItem() {
        List<Item> itemList = null;
        try {
            itemList = ItemRepo.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return itemList;
    }



    @FXML
    void btnAddOnAction(ActionEvent event) {
        String code = txtCode.getText();
        String name = txtName.getText();
        String status = txtStatus.getText();
        String unitPrice = txtUnitPrice.getText();
        String QTY = txtQty.getText();
        String ID = "1"; // Set user ID to 1

        Item item = new Item(code, name, status, unitPrice, QTY, ID);

        try {
            boolean isAdd = ItemRepo.add(item);
            if (isAdd){
                new Alert(Alert.AlertType.CONFIRMATION,"This" + code + "item is SAVED !").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void clearForm(){
        txtCode.setText("");
        txtName.setText("");
        txtStatus.setText("");
        txtUnitPrice.setText("");
        txtQty.setText("");
        txtUserID.setText("");
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearForm();

    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/supplier.fxml"));
               Stage stage = (Stage) rootItem.getScene().getWindow();
                rootItem.getChildren().clear();
                rootItem.getChildren().add(rootNode);
                stage.setTitle("Supplier Form");
                stage.centerOnScreen();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String code = txtCode.getText();
        String name = txtName.getText();
        String status = txtStatus.getText();
        String unitPrice = txtUnitPrice.getText();
        String QTY = txtQty.getText();
        String ID = txtUserID.getText();

        Item item = new Item(code, name, status, unitPrice, QTY, ID);

        try {
            boolean isUpdated = ItemRepo.update(item);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                clearForm();
            } loadItemTable();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtCodeSearchOnAction(ActionEvent event) {
        String code = txtCode.getText();

        try {
            Item item = ItemRepo.searchByCode(code);

            if (item != null) {
                txtCode.setText(item.getCode());
                txtName.setText(item.getName());
                txtStatus.setText(item.getStatus());
                txtUnitPrice.setText(item.getUnitPrice());
                txtQty.setText(item.getQTY());
                txtUserID.setText(item.getID());
            }else {
                new Alert(Alert.AlertType.ERROR,"This" + code + " Item code is new, You can't Search it !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

}

/*
public class ItemFormController {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private AnchorPane rootItem;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TextField txtItemCode;

    @FXML
    private JFXTextArea txtItemName;

    @FXML
    private JFXTextArea txtQTY;

    @FXML
    private JFXTextArea txtUnitPrice;

    private List<Item> itemList = new ArrayList<>();





    private void lodeNextItemCode() {
        try {
            String currentId = ItemRepo.itemCode();
            String nextId = nextCode(currentId);

            txtItemCode.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextCode(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("O");
//            System.out.println("Arrays.toString(split) = " + Arrays.toString(split));
            int id = Integer.parseInt(split[1]);    //2
            return "O" + ++id;

        }
        return "O1";
    }

    private List<Customer> getAllItem() {
        List<Customer> itemList = null;
        itemList = ItemRepo.getAll();
        return itemList;
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String itemCode = txtItemCode.getText();
        String name = txtItemName.getText();
        String unitPrice = txtUnitPrice.getText();
        String qty = txtQTY.getText();

        Item item = new Item(itemCode, name, unitPrice, qty);

        try {
            boolean isAdd = ItemRepo.add(item);
            if (isAdd) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtItemCode.setText("");
        txtItemName.setText("");
        txtUnitPrice.setText("");
        txtQTY.setText("");

    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/supplier.fxml"));
        Stage stage = (Stage) rootItem.getScene().getWindow();
        rootItem.getChildren().clear();
        rootItem.getChildren().add(rootNode);
        stage.setTitle("Supplier Form");
        stage.centerOnScreen();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String name = txtItemName.getText();
        String unitPrice = txtUnitPrice.getText();
        String qty = txtQTY.getText();
        String code = txtItemCode.getText();

        Item item = new Item(name, unitPrice, qty, code);

        try {
            boolean isUpdate = ItemRepo.update(item);
            if(isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void txtItemCodeSearchOnAction(ActionEvent event) {
        String code = txtItemCode.getText();

        try {
            Item item = ItemRepo.searchByCode(code);

            if (item != null) {
                txtItemCode.setText(item.getCode());
                txtItemName.setText(item.getName());
                txtUnitPrice.setText(item.getUnitPrice());
                txtQTY.setText(item.getQty());
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

}
*/