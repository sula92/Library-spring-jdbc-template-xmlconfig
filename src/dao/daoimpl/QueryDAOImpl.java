package dao.daoimpl;

import dao.mapper.CustomEntityMapper;
import dao.QueryDAO;
import entity.CustomEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    DataSource dataSource;
    JdbcTemplate jdbcTemplate;

    @Override
    public void setDataSource(DataSource ds) {
        this.dataSource=ds;
        this.jdbcTemplate=new JdbcTemplate(dataSource);
    }

    @Override
    public List<CustomEntity> getCustomEntityList() {
        String SQL = "SELECT B.borrow_id, B.date, R.date, F.amount FROM ((borrow B INNER JOIN book_return R ON B.borrow_id = R.borrow_id) INNER JOIN fee F ON R.borrow_id = F.borrow_id)";
        List <CustomEntity> customEntities = jdbcTemplate.query(SQL, new CustomEntityMapper());
        return customEntities;
    }
}
