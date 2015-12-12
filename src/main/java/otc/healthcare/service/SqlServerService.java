package otc.healthcare.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import otc.healthcare.dao.ConnectionFactory;
import otc.healthcare.dao.HcApplydataDao;
import otc.healthcare.dao.HcApplyenvDao;
import otc.healthcare.dao.OracleDBUtil;
import otc.healthcare.pojo.ClassificationInfo;
import otc.healthcare.pojo.DatabaseInfo;
import otc.healthcare.pojo.FieldInfo;
import otc.healthcare.pojo.HcApplydata;
import otc.healthcare.pojo.HcApplyenv;
import otc.healthcare.pojo.TableInfo;
import otc.healthcare.util.DBUtil;
import otc.healthcare.util.HealthcareConfiguration;

@Component
public class SqlServerService implements IService {

	@Autowired
	private HealthcareConfiguration hcConfiguration;

	public boolean testConnection(String sqlserver_url, String sqlserver_username, String sqlserver_password) {
		ConnectionFactory connectionFactory = new ConnectionFactory("sqlserver", sqlserver_url, sqlserver_username,
				sqlserver_password);
		if (connectionFactory.getInstance().getConnection() != null)
			return true;
		return false;
	}

	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int checkStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

}
