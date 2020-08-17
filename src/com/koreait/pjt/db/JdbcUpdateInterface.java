package com.koreait.pjt.db;

import java.sql.*;

public interface JdbcUpdateInterface {
	int update(Connection conn, PreparedStatement ps)throws SQLException;
}
