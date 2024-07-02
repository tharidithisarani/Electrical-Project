package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import lk.ijse.company.model.tm.OrderDetailTm;
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
    private JFXButton btnAddCart;

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
    private JFXComboBox<?> cmbItemCode;

    @FXML
    private Label lblCusID;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblItemCount;

    @FXML
    private Label lblOrderCount;

    @FXML
    private Label lblTotal;

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
    private TableView<?> tblOrderDetails;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;

    private ObservableList<CartTm> cartList = FXCollections.observableArrayList();

    private double fullAmmount = 0;

    private int orderCount;

    private int itemCount;
    private String orderId;
    private  String customerId;
    private Label lblId;

    public void initialize() {
        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrderDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<OrderDetailTm, Button> lastCol = (TableColumn<OrderDetailTm, Button>) tblOrderDetails.getColumns().get(5);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction(event -> {
                tblOrderDetails.getItems().remove(param.getValue());
                tblOrderDetails.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        orderId = generateNewOrderId();
        lblId.setText("Order ID: " + orderId);
        lblDate.setText(LocalDate.now().toString());
        btnPlaceOrder.setDisable(true);
        txtDescription.setFocusTraversable(false);
        txtDescription.setEditable(false);
        txtUnitPrice.setFocusTraversable(false);
        txtUnitPrice.setEditable(false);
        txtQtyOnHand.setFocusTraversable(false);
        txtQtyOnHand.setEditable(false);
        txtQty.setOnAction(event -> btnAddCart.fire());
        txtQty.setEditable(false);
        btnAddCart.setDisable(true);

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtQty.setEditable(newItemCode != null);
            btnAddCart.setDisable(newItemCode == null);

            if (newItemCode != null) {

                try {
                    if (!existItem(newItemCode + "")) {
//                        throw new NotFoundException("There is no such item associated with the id " + code);
                    }
                    Connection connection = DbConnection.getInstance().getConnection();
                    PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Item WHERE code=?");
                    pstm.setString(1, newItemCode + "");
                    ResultSet rst = pstm.executeQuery();
                    rst.next();
                    Item item = new Item(newItemCode + "", rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));

                    txtDescription.setText(item.getDescription());
                    txtUnitPrice.setText(item.getUnitPrice().setScale(2).toString());

                    Optional<OrderDetailTm> optOrderDetail = tblOrderDetails.getItems().stream().filter(detail -> detail.getCode().equals(newItemCode)).findFirst();
                    txtQtyOnHand.setText((optOrderDetail.isPresent() ? item.getQtyOnHand() - optOrderDetail.get().getQty() : item.getQtyOnHand()) + "");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                txtDescription.clear();
                txtQty.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
            }
        });

        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {

            if (selectedOrderDetail != null) {
                cmbItemCode.setDisable(true);
                cmbItemCode.setValue(selectedOrderDetail.getCode());
                btnAddCart.setText("Update");
                txtQtyOnHand.setText(Integer.parseInt(txtQtyOnHand.getText()) + selectedOrderDetail.getQty() + "");
                txtQty.setText(selectedOrderDetail.getQty() + "");
            } else {
                btnAddCart.setText("Add");
                cmbItemCode.setDisable(false);
                cmbItemCode.getSelectionModel().clearSelection();
                txtQty.clear();
            }

        });

        loadAllCustomerIds();
        loadAllItemCodes();
    }

    private void loadAllItemCodes() {
    }

    private void loadAllCustomerIds() {
    }

    private String generateNewOrderId() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");

            return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        }
        return "OID-001";
    }

    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT code FROM Item WHERE code=?");
        pstm.setString(1, code);
        return pstm.executeQuery().next();
    }

    private void enableOrDisablePlaceOrderButton() {
    }

    private void calculateTotal() {
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

    @FXML
    void txtQty_OnAction(ActionEvent event) {

    }
}



