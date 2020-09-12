package dao.daoimpl;

import dao.BorrowDAO;
import dao.mapper.BorrowMapper;
import entity.Borrow;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

public class BorrowDAOImpl implements BorrowDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Override
    public void setDataSource(DataSource ds) {
        this.dataSource=ds;
        jdbcTemplate=new JdbcTemplate(dataSource);

    }

    @Override
    public void create(String borowid, String bookid, Date date, String membrid) {
        String SQL = "insert into borrow (borrow_id, book_id, date, member_id) values (?, ?,?,?)";
        jdbcTemplate.update( SQL, borowid, bookid, date, membrid);
        return;

    }

    @Override
    public Borrow getBooksLastBorrow(String id) {
        String SQL = "SELECT * from borrow  where book_id=? ORDER BY borrow_id DESC LIMIT 1";
        Borrow borrow = jdbcTemplate.queryForObject(SQL,
                new Object[]{id}, new BorrowMapper());

        return borrow;
    }

    @Override
    public List<Borrow> listBorrows() {

        String SQL = "select * from borrow";
        List <Borrow> borrows = jdbcTemplate.query(SQL, new BorrowMapper());
        return borrows;
    }

    @Override
    public void delete(String id) {

        String SQL = "delete from borrow where borrow_id = ?";
        jdbcTemplate.update(SQL, id);
        System.out.println("Deleted Record with ID = " + id );
        return;

    }

    @Override
    public void update(String borowid, String bookid, Date date, String membrid) {
        String SQL = "update book set name = ?, author=?, edition=? where id = ?";
        jdbcTemplate.update(SQL, borowid, bookid, date, membrid);
        System.out.println("Updated Record with ID = " + borowid );
        return;

    }

    @Override
    public Borrow getLastBorrow() {
        String SQL = "SELECT * from borrow ORDER BY borrow_id DESC LIMIT 1";
        Borrow borrow = jdbcTemplate.queryForObject(SQL, new BorrowMapper());

        return borrow;
    }

    @Override
    public boolean checkBorrow(String bid) {
        try {
            String SQL = "SELECT * FROM borrow where book_id=?";
            Borrow borrow = jdbcTemplate.queryForObject(SQL,
                    new Object[]{bid}, new BorrowMapper());
            return false;

        }
        catch (Exception e){
            return true;
        }

    }

    @Override
    public Borrow getborrowDate(String borid) {
        String SQL = "SELECT * FROM borrow where borrow_id=?";
        Borrow borrow = jdbcTemplate.queryForObject(SQL,
                new Object[]{borid}, new BorrowMapper());
        return borrow;
    }
}
