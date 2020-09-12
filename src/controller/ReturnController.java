package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dao.BorrowDAO;
import dao.QueryDAO;
import dao.ReturnDAO;
import db.DBConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entity.Return;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import util.ReturnTM;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ResourceBundle;

public class ReturnController implements Initializable {

    public FontAwesomeIconView faHome;
    public AnchorPane returnForm;
    @FXML
    private JFXComboBox<String> cmbBorrowId;

    @FXML
    private JFXDatePicker dateReturned;

    @FXML
    private Button btnReturn;

    @FXML
    private TableView<ReturnTM> tblReturn;

    @FXML
    private TableColumn colBorrowId;

    @FXML
    private TableColumn colBorrowDate;

    @FXML
    private TableColumn colReturneDate;

    @FXML
    private TableColumn colFine;

    @FXML
    private TableColumn colAmount;

    @FXML
    private JFXTextField txtSearch;

    ApplicationContext context=new ClassPathXmlApplicationContext("resources/Beans.xml");
    ReturnDAO returnDAO= (ReturnDAO) context.getBean("returndaoimpl");
    QueryDAO queryDAO= (QueryDAO) context.getBean("querydaoimpl");
    BorrowDAO borrowDAO= (BorrowDAO) context.getBean("borrowdaoimpl");


    @Override
    public void initialize(URL location, ResourceBundle resources) {



        colBorrowId.setCellValueFactory(new PropertyValueFactory<>("borrowId"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colReturneDate.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        colFine.setCellValueFactory(new PropertyValueFactory<>("fine"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        loadTableData();
        try {
            loadBorrowIds();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tblReturn.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            String bid=newValue.getBorrowId();
            Date date=newValue.getReturnedDate();
            btnReturn.setText("UPDATE");
            cmbBorrowId.setValue(bid);


        });

    }

    private void loadBorrowIds() throws SQLException {

        ObservableList<String> bids=cmbBorrowId.getItems();

        returnDAO.listBorrows().stream().forEach(borrow -> {
            bids.add(borrow.getBorrowId());
        });

    }


    public void loadTableData() {
        System.out.println("xxxxxxxxxx");
            ObservableList<ReturnTM> returnTMS=tblReturn.getItems();
        returnTMS.clear();

            queryDAO.getCustomEntityList().stream().forEach(customEntity -> {

                String bID=customEntity.getBorrowId();
                Date bdate=customEntity.getBorrowDate();
                Date rdate=customEntity.getReturnDate();
                double amount=customEntity.getAmount();

                System.out.println(bID);
                System.out.println(bdate);

                String fine;
                if(amount>0.0){
                    fine="YES";
                }
                else {
                    fine="NO";
                }
                ReturnTM returnTM=new ReturnTM(bID,bdate,rdate,fine,amount);

                returnTMS.add(returnTM);
                //tblReturn.setItems(returnTMS);
            });


    }

    @FXML
    void btnReturnOnAction(ActionEvent event) throws SQLException {

        String bid=cmbBorrowId.getValue();
        java.sql.Date date= java.sql.Date.valueOf(dateReturned.getValue());

        Return aReturn=new Return(bid,date);

        if (btnReturn.getText().equalsIgnoreCase("returned")) {

            boolean b=returnDAO.create(aReturn.getBorrowId(),aReturn.getReturnedDate(),getFine(bid));

            if (b) {
                new Alert(Alert.AlertType.INFORMATION, "RECORD ADDED SUCCESSFULLY").show();
            }
            else {
                new Alert(Alert.AlertType.ERROR, "AN ERROR").show();
            }



        }
        else {
                try {
                    returnDAO.update(bid,date);
                    new Alert(Alert.AlertType.INFORMATION, "RECORD UPDATED SUCCESSFULLY").show();

                }catch (Exception e){
                    new Alert(Alert.AlertType.ERROR, "AN ERROR").show();

                }

            }

        ObservableList<ReturnTM> returnTMS=tblReturn.getItems();
        returnTMS.clear();
        tblReturn.refresh();
        loadTableData();

        ObservableList<String> bids=cmbBorrowId.getItems();
        bids.clear();
        loadBorrowIds();
    }

    private int getFine(String bid) throws SQLException {

        System.out.println("aaaaaa"+bid);

            LocalDate date2;

            Date d=borrowDAO.getborrowDate(bid).getBorrowedDate();
            System.out.println(d);
            //LocalDate date1=LocalDate.parse((CharSequence) rst.getDate(1));
              LocalDate date1= ((java.sql.Date) d).toLocalDate();
            System.out.println(date1);
            date2=dateReturned.getValue();

            int duration = (int) ChronoUnit.DAYS.between(date1, date2);
            int fee=duration*10;
            System.out.println("ccccccc"+duration);
            System.out.println("dddddd"+fee);

            return fee;
    }

    public void homeOnClicked(MouseEvent mouseEvent) throws IOException {

        URL url=this.getClass().getResource("/view/Main.fxml");
        Parent parent= FXMLLoader.load(url);
        Scene scene=new Scene(parent);
        Stage stage= (Stage) this.returnForm.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Student");
        stage.setFullScreen(false);
        stage.centerOnScreen();
    }
}
