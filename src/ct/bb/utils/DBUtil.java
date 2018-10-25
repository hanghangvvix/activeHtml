package ct.bb.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;
import org.apache.log4j.Logger;

import com.caitong.modules.spring.SpringContextHolder;

public class DBUtil {
	private static final Logger logger = Logger.getLogger(DBUtil.class);
	
	private static volatile DataSource dbPool ;
	//数据库连接池
	public static Connection getConnection(){
		if(dbPool==null){
			synchronized(DBUtil.class){
				if(dbPool==null){
					try{
						//dbPool = initPool();
						dbPool = (DataSource) SpringContextHolder.getApplicationContext().getBean("dataSource");
					}catch(Exception e){
						logger.error("初始化连接池异常!!",e);
						throw new RuntimeException(e);
					}
				}
			}
		}

		try{
			return dbPool.getConnection();
		}catch(Exception e){
			logger.error("获得数据库连接异常!!",e);
			throw new RuntimeException(e);
		}
	}
	
	public static void close(ResultSet rs,Statement st ,Connection conn){
		if(rs!=null){
			try{
				rs.close();
			}catch (Exception e) {
				logger.error("关闭记录集异常!",e);
			}finally{
				rs = null;
			}
		}
		if(st!=null){
			try{
				st.close();
			}catch (Exception e) {
				logger.error("关闭Statement异常!",e);
			}finally{
				st = null;
			}
		}
		if(conn!=null){
			try{
				conn.close();
			}catch (Exception e) {
				logger.error("关闭数据库连接异常!",e);
			}finally{
				conn = null;
			}
		}
	}	
}
