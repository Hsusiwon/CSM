package cn.sion.csm.service;

public class FactoryService {

	public static UserService getUserService() {
		return new UserServiceImpl();
	}
	
	public static OnlineService getOnlineService() {
		return new OnlineServiceImpl();
	}
	public static UploadFileService getUploadFileService() {
		return new UploadFileServiceImpl();
	}
	
	public static ProblemService getProblemService() {
		return new ProblemServiceImpl();
	}
}
