package dao;

import entity.Book;
import entity.Member;

import javax.sql.DataSource;
import java.util.List;

public interface BookDAO {

    public void setDataSource(DataSource ds);

    /**
     * This is the method to be used to list down
     * all the records from the Student table.
     */

    public void create(String isbn, String title, String author, String edition);

    /**
     * This is the method to be used to list down
     * a record from the Student table corresponding
     * to a passed student id.
     */
    public Book getBook(String id);

    /**
     * This is the method to be used to list down
     * all the records from the Student table.
     */
    public List<Book> listBooks();

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
    public void update(String id, String name, String author, String edition);

    public Book getLastBook();


}
