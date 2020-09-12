package dao.mapper;

import entity.Book;
import entity.Member;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {


    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setIsbn(rs.getString(1));
        book.setTitle(rs.getString(2));
        book.setAuthor(rs.getString(3));
        book.setEdition(rs.getString(4));
        return book;
    }
}
