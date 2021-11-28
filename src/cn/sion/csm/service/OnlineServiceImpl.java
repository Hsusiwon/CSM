package cn.sion.csm.service;

import java.util.List;

import cn.sion.csm.dao.FactoryDao;
import cn.sion.csm.dao.OnlineDao;
import cn.sion.csm.model.Online;

public class OnlineServiceImpl implements OnlineService {

	OnlineDao onlineDao = FactoryDao.getOnlineDao();
	
	@Override
	public List<Online> getAllOnline() {
		return onlineDao.getAllOnline();
	}

	@Override
	public void insertOnline(Online online) {
		onlineDao.insertOnline(online);
		
	}

	@Override
	public void updateOnline(Online online) {
		onlineDao.updateOnline(online);
	}

	@Override
	public void deleteExpiresOnline(List<Online> list) {
		//遍历list集合，实施删除操作
		if(list!=null && list.size()>0) {
			for(Online ol:list) {
				onlineDao.deleteExpiresOnline(ol.getSsid());
				
			}
		}
		
	}

	@Override
	public Online getOnlineBySsid(String ssid) {
		return onlineDao.getOnlineBySsid(ssid);
	}

}
