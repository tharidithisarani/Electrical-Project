package lk.ijse.company.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private JFXComboBox<String> cmbItemCode;

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
    private TableView<OrderDetailTm> tblOrderDetails;

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

        loadAllItemCodes();
    }

    private void loadAllItemCodes() {
        try {
            /*Get all items*/
            Connection connection = DbConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Item");
            while (rst.next()) {
                cmbItemCode.getItems().add(rst.getString("code"));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
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
        btnPlaceOrder.setDisable(!(cmbItemCode.getSelectionModel().getSelectedItem() != null && !tblOrderDetails.getItems().isEmpty()));
    }

    private void calculateTotal() {
        BigDecimal total = new BigDecimal(0);

        for (OrderDetailTm detail : tblOrderDetails.getItems()) {
            total = total.add(detail.getTotal());
        }
        lblTotal.setText("Total: " +total);
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

    /*private void setOrderCount(int orderCount) {
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
    }*/

   /* private void setCellValueFactory() {
       // colNo.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(tblOrderCart.getItems().indexOf(cellData.getValue()) + 1));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));

    }*/

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

    /*private void lodeNextOrderId() {
        try {
            String currentOrderID = OrderRepo.currentOrderID();
            String nextOrderId = nextOrderId(currentOrderID);
            lblOrderId.setText(nextOrderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

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
        if (!txtQty.getText().matches("\\d+") || Integer.parseInt(txtQty.getText()) <= 0 ||
                Integer.parseInt(txtQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        }

        String itemCode = cmbItemCode.getSelectionModel().getSelectedItem();
        String description = txtDescription.getText();
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        int qty = Integer.parseInt(txtQty.getText());
        BigDecimal total = unitPrice.multiply(new BigDecimal(qty)).setScale(2);

        boolean exists = tblOrderDetails.getItems().stream().anyMatch(detail -> detail.getCode().equals(itemCode));

        if (exists) {
            OrderDetailTm orderDetailTm = tblOrderDetails.getItems().stream().filter(detail -> detail.getCode().equals(itemCode)).findFirst().get();

            if (btnAddCart.getText().equalsIgnoreCase("Update")) {
                orderDetailTm.setQty(qty);
                orderDetailTm.setTotal(total);
                tblOrderDetails.getSelectionModel().clearSelection();
            } else {
                orderDetailTm.setQty(orderDetailTm.getQty() + qty);
                total = new BigDecimal(orderDetailTm.getQty()).multiply(unitPrice).setScale(2);
                orderDetailTm.setTotal(total);
            }
            tblOrderDetails.refresh();
        } else {
            tblOrderDetails.getItems().add(new OrderDetailTm(itemCode, description, qty, unitPrice, total));
        }
        cmbItemCode.getSelectionModel().clearSelection();
        cmbItemCode.requestFocus();
        calculateTotal();
        enableOrDisablePlaceOrderButton();
    }

    /*private void calculateNetTotal() {
        fullAmmount = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++){
            fullAmmount += (double) colTotal.getCellData(i);
        }
        lblFullAmount.setText(String.valueOf(fullAmmount));
    }*/

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

    /*@FXML
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

    }*/

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        boolean b = saveOrder(orderId, LocalDate.now(),
                tblOrderDetails.getItems().stream().map(tm -> new OrderDetail(tm.getCode(), tm.getQty(), tm.getUnitPrice())).collect(Collectors.toList()));

        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        orderId = generateNewOrderId();
        lblId.setText("Order Id: " + orderId);
        cmbItemCode.getSelectionModel().clearSelection();
        tblOrderDetails.getItems().clear();
        txtQty.clear();
        calculateTotal();
    }

    public boolean saveOrder(String orderId, LocalDate orderDate, List<OrderDetail> orderDetails) {

        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT oid FROM `Orders` WHERE oid=?");
            stm.setString(1, orderId);
            /*if order id already exist*/
            if (stm.executeQuery().next()) {

            }

            connection.setAutoCommit(false);
            stm = connection.prepareStatement("INSERT INTO `Orders` (oid, date, customerID) VALUES (?,?,?)");
            stm.setString(1, orderId);
            stm.setDate(2, Date.valueOf(orderDate));
            stm.setString(3, customerId);

            if (stm.executeUpdate() != 1) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            stm = connection.prepareStatement("INSERT INTO OrderDetails (oid, itemCode, qty, unitPrice) VALUES (?,?,?,?)");

            for (OrderDetail detail : orderDetails) {
                stm.setString(1, orderId);
                stm.setString(2, detail.getItemCode());
                stm.setInt(3, detail.getQty());
                stm.setBigDecimal(4, BigDecimal.valueOf(detail.getUnitPrice()));

                if (stm.executeUpdate() != 1) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

//                //Search & Update Item
                Item item = findItem(detail.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());

                PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
                pstm.setString(1, item.getDescription());
                pstm.setBigDecimal(2, item.getUnitPrice());
                pstm.setInt(3, item.getQtyOnHand());
                pstm.setString(4, item.getCode());

                if (!(pstm.executeUpdate() > 0)) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }

            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public Item findItem(String code) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Item WHERE code=?");
            pstm.setString(1, code);
            ResultSet rst = pstm.executeQuery();
            rst.next();
            return new Item(code, rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        }
    }

}



