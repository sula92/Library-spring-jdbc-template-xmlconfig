package dao.daoimpl;

import dao.BookDAO;
import dao.mapper.BookMapper;
import entity.Book;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class BookDAOImpl implements BookDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }


    @Override
    public void create(String isbn, String title, String author, String edition) {
        String SQL = "insert into book (id, name, author, edition) values (?, ?,?,?)";
        jdbcTemplateObject.update( SQL, isbn, title, author, edition);
        return;

    }

    @Override
    public Book getBook(String id) {

        String SQL = "select * from book where id = ?";
        Book book = jdbcTemplateObject.queryForObject(SQL,
                new Object[]{id}, new BookMapper());

        return book;
    }

    @Override
    public List<Book> listBooks() {
        String SQL = "select * from book";
        List <Book> books = jdbcTemplateObject.query(SQL, new BookMapper());
        return books;
    }

    @Override
    public void delete(String id) {

        String SQL = "delete from book where id = ?";
        jdbcTemplateObject.update(SQL, id);
        System.out.println("Deleted Record with ID = " + id );
        return;

    }

    @Override
    public void update(String id, String name, String author, String edition) {

        String SQL = "update book set name = ?, author=?, edition=? where id = ?";
        jdbcTemplateObject.update(SQL, id, name, author, edition);
        System.out.println("Updated Record with ID = " + id );
        return;

    }

    @Override
    public Book getLastBook() {

        String SQL = "SELECT * FROM book ORDER BY id DESC LIMIT 1";
        Book book = jdbcTemplateObject.queryForObject(SQL,
                new Object[]{}, new BookMapper());

        return book;
    }
}
