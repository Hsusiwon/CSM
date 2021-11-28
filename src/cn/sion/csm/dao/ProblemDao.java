package cn.sion.csm.dao;

import java.util.List;

import cn.sion.csm.model.Problem;

public interface ProblemDao {

	/**
	 * 取出所有条故障信息
	 * @return
	 */
	public List<Problem> getAll(String stitle,String stime,String etime,String repaired,String level,String page,String limit);
	
	public List<Problem> getAllO(String stitle,String stime,String etime,String repaired,String level);
	
	public List<Problem> getAllSerial();
	
	public List<Problem> getAllSerialByStitle(String stitle);
	
	public List<Problem> getAllSerialByPage(String page,String limit);
	
	public List<Problem> getAllSerialBySP(String page,String limit,String stitle);
	
	public List<Problem> getAllSerialNum(String serial);
	
	public int getMaxSerial();
	
	public int add(Problem problem);

	public int delete(String seiral);
	
	public int deleted(String serial,String serialnum);
	
	public int getMaxSerialNum(String serial);
	
	public int addd(Problem problem);
	
	public Problem getOne(String serial,String serialnum);
	
	public List<Problem> getAll(String serial);
	
	public int wxaddpic(String seiral,String serialnum,String recordpic);
	
	public int wxeditpic(String seiral,String serialnum,String updatepic);
}
