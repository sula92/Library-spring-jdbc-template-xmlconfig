package dao.mapper;

import entity.Book;
import entity.Borrow;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowMapper implements RowMapper<Borrow> {

    @Override
    public Borrow mapRow(ResultSet rs, int rowNum) throws SQLException {
        Borrow borrow= new Borrow();
        borrow.setBorrowId(rs.getString(1));
        borrow.setBookIs(rs.getString(2));
        borrow.setBorrowedDate(rs.getDate(3));
        borrow.setCusId(rs.getString(4));
        return borrow;
    }

}
