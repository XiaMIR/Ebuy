package cn.leelei.ebuy.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbUtils {
	// 得到logger 对象日志输出
	private static Logger logger = LoggerFactory.getLogger(DbUtils.class);
	// 数据库链接池
	private static BasicDataSource dataSource;
	static {
		InputStream is = null;
		try {
			// 加载属�?配置文件propertites
			is = DbUtils.class.getClassLoader().getResourceAsStream(
					"jdbc.properties");
			Properties properties = new Properties();
			properties.load(is);
			dataSource = new BasicDataSource();
			// 设置驱动�?
			dataSource
					.setDriverClassName(properties.getProperty("jdbc.driver"));
			// 设置url地址
			dataSource.setUrl(properties.getProperty("jdbc.url"));
			// 设置用户�?
			dataSource.setUsername(properties.getProperty("jdbc.user"));
			// 设置的路密码
			dataSource.setPassword(properties.getProperty("jdbc.password"));

			logger.debug("数据源初始化成功=====================");

		} catch (IOException e) {
			logger.debug("数据源初始化失败=====================");
			e.printStackTrace();
			// 关闭数据�?
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * 获取数据库链�?
	 * 
	 * @return
	 * @throws SQLException
	 */
	
	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 释放数据库资�?
	 * 
	 * @param connection
	 * @param statment
	 * @param resultSet
	 */
	public static void closeResource(Connection connection,
			Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
