package dao;

import entity.CustomEntity;

import javax.sql.DataSource;
import java.util.List;

public interface QueryDAO {

    public void setDataSource(DataSource ds);

    public List<CustomEntity> getCustomEntityList();

}
