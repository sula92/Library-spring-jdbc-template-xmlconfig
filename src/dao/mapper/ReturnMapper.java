package dao.mapper;

import entity.Return;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturnMapper implements RowMapper<Return> {
    @Override
    public Return mapRow(ResultSet rs, int rowNum) throws SQLException {
        Return aReturn=new Return();
       aReturn.setBorrowId(rs.getString(1));
       aReturn.setReturnedDate(rs.getDate(2));

       return aReturn;
    }
}
