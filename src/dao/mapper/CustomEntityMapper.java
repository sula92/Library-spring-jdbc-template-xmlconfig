package dao.mapper;

import entity.CustomEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomEntityMapper implements RowMapper<CustomEntity> {
    @Override
    public CustomEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomEntity customEntity=new CustomEntity();
        customEntity.setBorrowId(rs.getString(1));
        customEntity.setBorrowDate(rs.getDate(2));
        customEntity.setReturnDate(rs.getDate(3));
        customEntity.setAmount(rs.getInt(4));

        return customEntity;
    }
}
