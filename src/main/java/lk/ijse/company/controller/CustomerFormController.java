/*
package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.company.database.DbConnection;
import lk.ijse.company.model.*;
import lk.ijse.company.model.tm.CartTm;
import lk.ijse.company.reposotory.CustomerRepo;
import lk.ijse.company.reposotory.OrderRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class CustomerFormController {

    @FXML
    private Button bill;

    @FXML
    private JFXButton btnAddCart;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnConstructerOrder;

    @FXML
    private JFXButton btnOrdinaryBuyer;

    @FXML
    private JFXButton btnPermenentBuyer;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXButton btnRegPermenentCustomer;

    @FXML
    private JFXComboBox<String> cmbItemCode; // Updated to JFXComboBox<String>

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colItemCode;

//    @FXML
//    private TableColumn<?, ?> colNo;

    @FXML
    private TableColumn<?, ?> colQTY;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblCusID;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblFullAmount;

    @FXML
    private Label lblName;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQOH;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Pane panedesc;

    @FXML
    private Pane paneorder;

    @FXML
    private AnchorPane rootCusDetail;

    @FXML
    private AnchorPane rootCustomer;

    @FXML
    private AnchorPane rootLode;

    @FXML
    private AnchorPane rootOrdinaryBuyer;

    @FXML
    private TableView<CartTm> tblOrderCart;

    @FXML
    private TextField txtQTY;

    @FXML
    private Label lblOrderCount;

    @FXML
    private Label lblItemCount;

    private ObservableList<CartTm> cartList = FXCollections.observableArrayList();

    private double fullAmmount = 0;

    private int orderCount;

    private int itemCount;

    public void initialize() {
        setCellValueFactory();
        setDate();
        lodeNextCustomerId();
        lodeNextOrderId();
        getItemCodes();

        try {
            itemCount = (int) getItemCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setItemCount(itemCount);

        try {
            orderCount = getOrderCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setOrderCount(orderCount);
    }

    private Object getItemCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS item_count FROM item";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int itemCount = 0;
        if(resultSet.next()) {
            itemCount = resultSet.getInt("item_count");
        }
        return itemCount;
    }

    private int getOrderCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS order_count FROM orders";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int orderCount = 0;
        if(resultSet.next()) {
            orderCount = resultSet.getInt("order_count");
        }
        return orderCount;
    }

    private void setOrderCount(int orderCount) {
        lblOrderCount.setText(String.valueOf(orderCount));
    }

    private void setItemCount(int orderCount) {
        lblItemCount.setText(String.valueOf(orderCount));
    }

    private void getItemCodes() {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        try {
            List<String> codeList = ItemRepo.getCodes();
            observableList.addAll(codeList);
            cmbItemCode.setItems(observableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
       // colNo.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(tblOrderCart.getItems().indexOf(cellData.getValue()) + 1));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));

    }

    private void lodeNextCustomerId() {
        try {
            String currentId = OrderRepo.currentId();
            String nextId = nextId(currentId);
            lblCusID.setText(nextId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("O");
            int id = Integer.parseInt(split[1]);
            return "O" + ++id;
        }
        return "O1";
    }

    private void lodeNextOrderId() {
        try {
            String currentOrderID = OrderRepo.currentOrderID();
            String nextOrderId = nextOrderId(currentOrderID);
            lblOrderId.setText(nextOrderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextOrderId(String currentOrderID) {
        if (currentOrderID != null) {
            String[] split = currentOrderID.split("O");
            int id = Integer.parseInt(split[1]);
            return "O" + ++id;
        }
        return "O1";
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }

    @FXML
    void btnAddCartOnAction(ActionEvent event) {
        String code = cmbItemCode.getValue();
        String itemName = lblName.getText();
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        int qty = Integer.parseInt(txtQTY.getText());
        double total = unitPrice * qty;
        JFXButton btnRemove = new JFXButton("Remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure you want to remove this item?", yes, no).showAndWait();

            if (type.orElse(no) == yes){
                int selectedIndex = tblOrderCart.getSelectionModel().getSelectedIndex();
                cartList.remove(selectedIndex);

                tblOrderCart.refresh();
                calculateNetTotal();
            }

        });

        for (int i = 0; i < tblOrderCart.getItems().size(); i++){
            if (code.equals(colItemCode.getCellData(i))){
                qty += cartList.get(i).getQty();
                total = qty * unitPrice;

                cartList.get(i).setQty(qty);
                cartList.get(i).setTotal(total);

                tblOrderCart.refresh();
                calculateNetTotal();
                txtQTY.setText("");
                return;
            }
        }
        CartTm cartTm = new CartTm(code, itemName, unitPrice, qty, total, btnRemove);

        cartList.add(cartTm);

        tblOrderCart.setItems(cartList);
        txtQTY.setText("");
        calculateNetTotal();


    }

    private void calculateNetTotal() {
        fullAmmount = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++){
            fullAmmount += (double) colTotal.getCellData(i);
        }
        lblFullAmount.setText(String.valueOf(fullAmmount));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootCustomer.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    @FXML
    void btnBillOnAction(ActionEvent event) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Report/CustomerReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String, Object> date = new HashMap<>();
        date.put("CustomerID", lblCusID.getText());
        date.put("FullAmmount","3000");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, date, DbConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint, false);
    }

    @FXML
    void btnConstructerOrderOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/permanent_buyer.fxml"));
        this.rootLode.getChildren().clear();
        this.rootLode.getChildren().add(anchorPane);

        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/constructer_order.fxml"));
        Stage stage = (Stage) rootCusDetail.getScene().getWindow();
        rootCusDetail.getChildren().clear();
        rootCusDetail.getChildren().add(rootNode);
        stage.setTitle("Constructer Order Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnOrdinaryBuyerOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/customer.fxml"));
        Stage stage = (Stage) rootCustomer.getScene().getWindow();
        rootCustomer.getChildren().clear();
        rootCustomer.getChildren().add(rootNode);
        stage.setTitle("Customer Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnPermenentBuyerOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/permanent_buyer.fxml"));
        this.rootLode.getChildren().clear();
        this.rootLode.getChildren().add(anchorPane);
    }

    @FXML
    void btnRegPermenentCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/permanent_buyer.fxml"));
        this.rootLode.getChildren().clear();
        this.rootLode.getChildren().add(anchorPane);


    }

    @FXML
    void cmbItemOnAction(ActionEvent event) throws IOException {
        String code = (String) cmbItemCode.getValue();
        try {
            Item item = ItemRepo.searchByCode(code);
            if (item != null) {
                lblName.setText(item.getName());
                lblUnitPrice.setText(String.valueOf(item.getUnitPrice()));
                lblQOH.setText(String.valueOf(item.getQTY()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        txtQTY.requestFocus();

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId = lblOrderId.getText();
        String cusId = lblCusID.getText();
        Date date = Date.valueOf(LocalDate.now());

        var order = new Order(orderId, cusId, date);

        List<OrderDetail> odList = new ArrayList<>();
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            CartTm tm = cartList.get(i);

            OrderDetail od = new OrderDetail(orderId, tm.getCode(), tm.getQty(), tm.getUnitPrice());
            odList.add(od);
        }

        Customer cu = new Customer(order, odList);
        try {
            boolean isPlaced = CustomerRepo.customer(cu);
            if (isPlaced){
                new Alert(Alert.AlertType.CONFIRMATION, "order placed!").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "order not placed!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, "order not placed!").show();
        }
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddCartOnAction(event);
    }
}



*/
