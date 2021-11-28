package cn.sion.csm.dao;

public class FactoryDao {

	public static UserDao getUserDao() {
		return new UserDaoImpl();
	}
	
	public static OnlineDao getOnlineDao() {
		return new OnlineDaoImpl();
	}
	
	public static UploadFileDao getUploadFileDao() {
		return new UploadFileDaoImpl();
	}
	
	public static ProblemDao getProblemDao() {
		return new ProblemDaoImpl();
	}
}
