package dao;

import entity.Borrow;
import entity.Return;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

public interface ReturnDAO {

    public void setDataSource(DataSource ds);

    /**
     * This is the method to be used to list down
     * all the records from the Student table.
     */

    public boolean create(String borowid, Date bordate,int amount);

    /**
     * This is the method to be used to list down
     * a record from the Student table corresponding
     * to a passed student id.
     */
    public Return getBooksLastReturn(String id);

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
    public void update(String borowid, Date bordate);

    public Return getLastReturn();

    public boolean checkReturn(String bid);





}
