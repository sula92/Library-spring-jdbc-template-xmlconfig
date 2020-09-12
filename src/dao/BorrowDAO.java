package dao;

import entity.Book;
import entity.Borrow;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

public interface BorrowDAO {

    public void setDataSource(DataSource ds);

    /**
     * This is the method to be used to list down
     * all the records from the Student table.
     */

    public void create(String borowid, String bookid, Date date, String membrid);

    /**
     * This is the method to be used to list down
     * a record from the Student table corresponding
     * to a passed student id.
     */
    public Borrow getBooksLastBorrow(String id);

    /**
     * This is the method to be used to list down
     * all the records from the Student table.
     */
    public List<Borrow> listBorrows();

    /**
     * This is the method to be used to delete
     * a record from the Student table corresponding
     * to a passed student id.
     */
    public void delete(String id);

    /**
     * This is the method to be used to update
     * a record into the Student table.
     */
    public void update(String borowid, String bookid, Date date, String membrid);

    public Borrow getLastBorrow();

    public boolean checkBorrow(String bid);

    public Borrow getborrowDate(String borid);

}
