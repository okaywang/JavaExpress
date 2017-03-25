package demo;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * foo...Created by wgj on 2017/3/25.
 */
public class BlogTypeEnumTypeHandler implements TypeHandler<BlogTypeEnum> {
    @Override
    public void setParameter(PreparedStatement ps, int i, BlogTypeEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());

    }

    @Override
    public BlogTypeEnum getResult(ResultSet rs, String columnName) throws SQLException {
        return BlogTypeEnum.parse(rs.getInt(columnName));
    }

    @Override
    public BlogTypeEnum getResult(ResultSet rs, int columnIndex) throws SQLException {
        return BlogTypeEnum.parse(rs.getInt(columnIndex));
    }

    @Override
    public BlogTypeEnum getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return BlogTypeEnum.parse(cs.getInt(columnIndex));
    }
}
