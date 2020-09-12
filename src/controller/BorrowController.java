package controller;



import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dao.*;
import dao.daoimpl.BookDAOImpl;
import dao.daoimpl.BorrowDAOImpl;
import dao.daoimpl.MemberDAOImpl;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import util.BorrowTM;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class BorrowController implements Initializable {

    public JFXTextField txtBorrowId;
    public FontAwesomeIconView faHome;
    public AnchorPane borrowForm;
    @FXML
    private JFXComboBox<?> cmbBorrowId;

    @FXML
    private JFXComboBox<String> cmbBookId;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXDatePicker cmbBorrowDate;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnSave;

    @FXML
    private TableView<BorrowTM> tblBorrow;

    @FXML
    private TableColumn colBorrowId;

    @FXML
    private TableColumn colBorrowedDate;

    @FXML
    private TableColumn colBookId;

    @FXML
    private TableColumn colCustomerId;

    @FXML
    private TableColumn colDelete;

    @FXML
    private Label lab1;

    ApplicationContext context = new ClassPathXmlApplicationContext("resources/Beans.xml");
    BookDAO bookDAO = (BookDAOImpl) context.getBean("bookdaoimpl");
    MemberDAO memberDAO = (MemberDAOImpl) context.getBean("memberdaoimpl");
    BorrowDAO borrowDAO = (BorrowDAOImpl) context.getBean("borrowdaoimpl");
    ReturnDAO returnDAO= (ReturnDAO) context.getBean("returndaoimpl");


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        AddNew();

        if (txtBorrowId.getText() == "" || cmbBookId.getValue() == null || cmbBorrowDate.getValue() == null || cmbCustomerId.getValue() == null) {
            btnSave.setDisable(true);

        } else {
            btnSave.setDisable(false);
        }

        loadMembers();
        loadBooks();
        loadBorrow();
        txtBorrowId.setPromptText("BORROW ID");
        txtBorrowId.setStyle("-fx-text-fill: white; -fx-prompt-text-fill: white");
        cmbBookId.setStyle("-fx-highlight-text-fill: white");
        cmbCustomerId.setStyle("-fx-prompt-text-fill: white");

        colBorrowId.setCellValueFactory(new PropertyValueFactory<>("borrowId"));
        colBorrowedDate.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("button"));

        cmbBookId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            String bid = newValue;
            boolean available;
            try {
                boolean available1 = checkReturn(newValue);
                System.out.println("a1" + available1);
                boolean available2 = checkBorrow(newValue);
                System.out.println("a2" + available2);

                if (available1 || available2) {
                    available = true;
                    lab1.setText("BOOK IS AVAILABLE");
                    btnSave.setDisable(false);
                } else {
                    available = false;
                    lab1.setText("BOOK IS UNAVAILABLE");
                    btnSave.setDisable(true);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }


        });

        tblBorrow.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            BorrowTM selectedItem = tblBorrow.getSelectionModel().getSelectedItem();
            txtBorrowId.setText(selectedItem.getBookId());
            //cmbBorrowDate.setValue(selectedItem.getBorrowedDate());
            cmbBookId.setValue(selectedItem.getBookId());
            cmbCustomerId.setValue(selectedItem.getCusId());

            btnSave.setText("UPDATE");


        });


    }

    @FXML
    void btnAddOnAction(ActionEvent event) {AddNew();}


public void AddNew(){
        btnSave.setText("SAVE");



        tblBorrow.getSelectionModel().clearSelection();
        cmbCustomerId.setDisable(false);
        cmbBorrowDate.setDisable(false);
        cmbBookId.setDisable(false);

        btnSave.setDisable(false);

        // Generate a new id

        int maxCode = Integer.parseInt(borrowDAO.getLastBorrow().getBorrowId());


        maxCode = maxCode + 1;

        String code = "";
        if (maxCode < 10) {
            code = "00" + maxCode;
        } else if (maxCode < 100) {
            code = "0" + maxCode;
        } else {
            code = "" + maxCode;
        }

        txtBorrowId.setText(String.valueOf(code));

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {

        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {

                Date date = Date.valueOf(cmbBorrowDate.getValue());
                borrowDAO.create(txtBorrowId.getText(), cmbBookId.getValue(), date, cmbCustomerId.getValue());

                new Alert(Alert.AlertType.INFORMATION, "RECORD ADDED SUCCESSFULLY").show();
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, "SOMETHINGS WRONG").show();
            }

            cmbBookId.setValue("");
        } else {

            try {
                 borrowDAO.update(txtBorrowId.getText(),cmbBookId.getValue(),Date.valueOf(cmbBorrowDate.getValue()),cmbCustomerId.getValue());
                 new Alert(Alert.AlertType.INFORMATION, "RECORD UPDATED SUCCESSFULLY");
            } catch (Exception e){
                new Alert(Alert.AlertType.ERROR, "SOMETHINGS WRONG").show();
            }
            cmbBookId.setValue("");
        }

        tblBorrow.refresh();
        btnAddOnAction(event);
        loadBorrow();

    }

    public void loadMembers() {

        try {
            ObservableList<String> memberids = cmbCustomerId.getItems();
            memberids.clear();

            memberDAO.listMembers().stream().forEach(member -> {
                memberids.add(member.getId());
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadBooks() {

        try {

            ObservableList<String> bookids = cmbBookId.getItems();
            bookids.clear();
           bookDAO.listBooks().stream().forEach(book ->{
               bookids.add(book.getIsbn());
           } );


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void loadBorrow() {
        ObservableList<BorrowTM> borrowTMS = tblBorrow.getItems();
        borrowTMS.clear();

        try {
            borrowDAO.listBorrows().stream().forEach(borrow -> {


                Button del = new Button("DELETE");
                del.setStyle("-fx-background-color: blue");
                BorrowTM btm=new BorrowTM(borrow.getBorrowId(), borrow.getBorrowedDate(), borrow.getBookIs(), borrow.getCusId(), del);
                borrowTMS.add(btm);
                del.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {


                        // BorrowTM borrowTM=new BorrowTM(btm);
                        try {
                            deleteBorrow(btm.getBorrowId());
                            System.out.println(btm.getBorrowId());
                            tblBorrow.getSelectionModel().clearSelection();
                            borrowTMS.remove(btm);
                            borrowTMS.clear();
                            tblBorrow.refresh();
                            loadBorrow();
                            btnAddOnAction(event);
                            cmbBookId.setValue("");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                });


            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //check whether the book is returned or not
    public boolean checkReturn(String bid) throws SQLException {

        System.out.println(bid + "1st");

        return returnDAO.checkReturn(getBorrowId(bid));

        //get the borrow id of last record of the given bookid & check whether there is any
       //record available in book_return based on the borrowId
        // return true if there is a record. but if you type rs.next(); agiain, it is false since there is only one record

    }

    //get the borrow id of last record of the given bookid & pass it to checkReturn()
    public String getBorrowId(String bid) throws SQLException {

        System.out.println(bid);
        String lastborrowid=borrowDAO.getBooksLastBorrow(bid).getBorrowId();
        return lastborrowid;

    }

    //check whether the book has never borrowed or not
    public boolean checkBorrow(String bid) throws SQLException {

        boolean b=borrowDAO.checkBorrow(bid);
        if (b) {
            return false;
        } else {
            return true;
        }

    }


   public void deleteBorrow(String borId) throws SQLException {
       System.out.println(borId);
       try {
           borrowDAO.delete(borId);
           new Alert(Alert.AlertType.INFORMATION,"RECORD DELETED");
       }
       catch (Exception e){
           new Alert(Alert.AlertType.ERROR,"ERROR");
       }
       cmbBookId.setValue("");
   }

    public void homeClicked(MouseEvent mouseEvent) throws IOException {

        URL url=this.getClass().getResource("/view/Main.fxml");
        Parent parent= FXMLLoader.load(url);
        Scene scene=new Scene(parent);
        Stage stage= (Stage) this.borrowForm.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Student");
        stage.setFullScreen(false);
        stage.centerOnScreen();
    }
}






