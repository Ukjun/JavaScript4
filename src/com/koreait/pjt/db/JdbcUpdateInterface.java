package com.koreait.pjt.db;

import java.sql.*;

public interface JdbcUpdateInterface {
	void update(Connection conn, PreparedStatement ps)throws SQLException;
}
