package ct.bb.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ct.bb.model.BackModel;
import ct.bb.utils.DBUtil;

@Service
@Transactional
public class DbServiceImpl implements DbService {

	private static final Logger logger = Logger.getLogger(DbServiceImpl.class);

	@Override
	public BackModel addRecommond(String wechatId, String phone) {
		// TODO Auto-generated method stub
		Connection conn = DBUtil.getConnection();
		BackModel backModel = new BackModel();
		logger.info("绑定手机号码，执行入库参数：  wechatId=" + wechatId + "，phone=" + phone);
		CallableStatement stat = null;
		
		try {
			stat = conn.prepareCall("{call PK_Recommend.Pr_Add_Recommend(?,?,?,?)}");
			
			stat.setString(1, wechatId);
			stat.setString(2, phone);
			stat.registerOutParameter(3, Types.NUMERIC);
			stat.registerOutParameter(4, Types.VARCHAR);
			stat.execute();
			
			backModel.setCode(stat.getInt(3));
			backModel.setResult(stat.getString(4));

		} catch (SQLException e) {
			logger.error(e);
			backModel.setCode(999);
			backModel.setResult("谢谢参与");
		} finally {
			DBUtil.close(null, stat, conn);
		}
		logger.info("添加推荐信息，执行入库返回的数据：  " + backModel.toJsonString());
		return backModel;
	}

}
