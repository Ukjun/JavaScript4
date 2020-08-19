package com.koreait.pjt.db;

import java.sql.*;
import java.util.*;

public interface JdbcSelectInterface {
	void prepared(PreparedStatement ps)throws SQLException;
	int executeQuery(ResultSet rs) throws SQLException;
	List<?> selBoard(ResultSet rs);
}
