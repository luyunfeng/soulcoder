package tech.soulcoder;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tech.soulcoder.entity.ActionWithException2;
import tech.soulcoder.entity.FuncWithException2;
import tech.soulcoder.entity.Optional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
@Service
public class SqlHelper {

    @Autowired
    @Qualifier("sqlSessionFactorySoul")
    private SqlSessionFactory sqlSessionFactory;

    public SqlHelper() {
    }

    public SqlHelper(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void exec(String sql, Object[] params, ActionWithException2<ResultSet> rowHandler) {
        try {
            try (SqlSession session = sqlSessionFactory.openSession()) {
                Connection conn = session.getConnection();
                try (PreparedStatement cmd = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_READ_ONLY)) {

                    if (params != null) {
                        for (int index = 0; index < params.length; ++index) {
                            cmd.setObject(index + 1, params[index]);
                        }
                    }

                    if (rowHandler != null) {
                        cmd.setFetchSize(Integer.MIN_VALUE);
                        try (ResultSet resultSet = cmd.executeQuery()) {
                            while (resultSet.next()) {
                                rowHandler.invoke(resultSet);
                            }
                        }
                    } else {
                        cmd.execute();
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public <T> Optional<T> exec(String sql, Object[] params, FuncWithException2<T, ResultSet> rowHandler) {
        Optional<T> result = new Optional<T>();
        exec(sql, params, (row) -> {
            result.value = rowHandler.invoke(row);
        });
        return result;
    }

    public void exec(String sql) {
        exec(sql, null, (ActionWithException2<ResultSet>)null);
    }

    public int execInt(String sql) {
        return execInt(sql, null);
    }

    public void exec(String sql, Object[] params) {
        exec(sql, params, (ActionWithException2<ResultSet>)null);
    }

    public int execInt(String sql, Object[] params) {
        Optional<Integer> result = exec(sql, params, row -> {
            return row.getInt(1);
        });
        return result.value != null ? result.value : 0;
    }

    public void exec(String sql, ActionWithException2<ResultSet> rowHandler) {
        exec(sql, null, rowHandler);
    }

    public <T> Optional<T> exec(String sql, FuncWithException2<T, ResultSet> rowHandler) {
        return exec(sql, null, rowHandler);
    }

    /**
     * 读取日期时间列
     */
    public Date readDateTime(ResultSet row, String columnName) {
        try {
            return new Date(row.getTimestamp(columnName).getTime());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
