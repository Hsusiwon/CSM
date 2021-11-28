package cn.sion.csm.service;

import java.util.List;

import cn.sion.csm.dao.FactoryDao;
import cn.sion.csm.dao.ProblemDao;
import cn.sion.csm.model.Problem;

public class ProblemServiceImpl implements ProblemService {

	ProblemDao problemDao = FactoryDao.getProblemDao();
	@Override
	
	public List<Problem> getAll(String stitle,String stime,String etime,String repaired,String level,String page,String limit){
		return problemDao.getAll(stitle,stime,etime,repaired,level,page,limit);
	}
	
	public List<Problem> getAllO(String stitle,String stime,String etime,String repaired,String level){
		return problemDao.getAllO(stitle,stime,etime,repaired,level);
	}
	
	public List<Problem> getAllSerial() {
		return problemDao.getAllSerial();
	}
	
	public List<Problem> getAllSerialByStitle(String stitle) {
		return problemDao.getAllSerialByStitle(stitle);
	}
	
	public List<Problem> getAllSerialByPage(String page,String limit) {
		return problemDao.getAllSerialByPage(page,limit);
	}
	
	public List<Problem> getAllSerialBySP(String page,String limit,String stitle) {
		return problemDao.getAllSerialBySP(page,limit,stitle);
	}
	
	public List<Problem> getAllSerialNum(String serial) {
		return problemDao.getAllSerialNum(serial);
	}
	
	public int getMaxSerial() {
		return problemDao.getMaxSerial();
	}
	
	public int add(Problem problem) {
		return problemDao.add(problem);
	}
	
	public int delete(String seiral) {
		return problemDao.delete(seiral);
		
	}
	
	public int deleted(String seiral,String serialnum) {
		return problemDao.deleted(seiral,serialnum);
		
	}
	
	public int getMaxSerialNum(String serial) {
		return problemDao.getMaxSerialNum(serial);
	}
	
	public int addd(Problem problem) {
		return problemDao.add(problem);
	}
	
	public Problem getOne(String serial,String serialnum) {
		return problemDao.getOne(serial,serialnum);
		
	}

	public List<Problem> getAll(String serial) {
		return problemDao.getAll(serial);
	}

	@Override
	public int wxaddpic(String seiral, String serialnum, String recordpic) {
		return problemDao.wxaddpic(seiral, serialnum, recordpic);
	}

	@Override
	public int wxeditpic(String seiral, String serialnum, String updatepic) {
		return problemDao.wxeditpic(seiral, serialnum, updatepic);
	}
}
