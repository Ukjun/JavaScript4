package com.koreait.pjt.db;

import java.sql.*;

public interface JdbcSelectInterface {
	ResultSet prepared(PreparedStatement ps)throws SQLException;
	int executeQuery(ResultSet rs) throws SQLException;
}
