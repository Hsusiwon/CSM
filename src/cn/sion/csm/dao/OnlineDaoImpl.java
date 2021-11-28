package cn.sion.csm.dao;

import java.util.List;

import cn.sion.csm.model.Online;

public class OnlineDaoImpl extends BaseDao<Online> implements OnlineDao {

	@Override
	public List<Online> getAllOnline() {
		String sql = "select `ssid`,`username`,`page`,`ip`,`time` from `online` order by `time`";
		return super.getlist(sql);
	}

	@Override
	public void insertOnline(Online online) {
		String sql = "insert `online` set `ssid`=?,`username`=?,`page`=?,`ip`=?,`time`=?";
		super.update(sql, online.getSsid(),online.getUsername(),online.getPage(),online.getIp(),online.getTime());	
	}

	@Override
	public void updateOnline(Online online) {
		String sql = "update `online` set `username`=?,`page`=?,`ip`=?,`time`=? where `ssid`=?";
		super.update(sql, online.getUsername(),online.getPage(),online.getIp(),online.getTime(),online.getSsid());
	}

	@Override
	public void deleteExpiresOnline(String ssid) {
		String sql = "delete from `online` where `ssid`=?";
		super.update(sql, ssid);
		
	}

	@Override
	public Online getOnlineBySsid(String ssid) {
		String sql = "select `ssid`,`username`,`page`,`ip`,`time` from `online` where `ssid`=?";
		return super.get(sql, ssid);
	}

}
