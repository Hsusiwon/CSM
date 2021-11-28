package cn.sion.csm.dao;

import java.util.List;

import cn.sion.csm.model.Problem;

public class ProblemDaoImpl  extends BaseDao<Problem> implements ProblemDao {

	//display页面获取所有项目第一条信息，包括repaired最新状态，分页
	public List<Problem> getAll(String stitle,String stime,String etime,String repaired,String level,String page,String limit) {
		String sql = "select id,serial,serialnum,title,type,level,repaired,recordtime,recorder,recordcont,recordpic from("
				+ "select * from(select ANY_VALUE(id) id,ANY_VALUE(serial) serial,ANY_VALUE(serialnum) serialnum,ANY_VALUE(title) title,ANY_VALUE(type) type,ANY_VALUE(level) level,MAX(repaired) repaired,ANY_VALUE(recordtime) recordtime,ANY_VALUE(recorder) recorder,ANY_VALUE(recordcont) recordcont,ANY_VALUE(recordpic) recordpic from worktrace a"
				+ " group by a.serial)b"
				+ " order by b.recordtime desc"
				+ ")c where 1=1";
		if(stime!=null&&stime!=""&&etime!=null&&etime!="") {
			sql=sql+" and recordtime between '"+stime+"' and '"+etime+"'";
		}
		if(stitle!=null&&stitle!="") {
			sql=sql+" and title regexp '"+stitle+"'";
		}
		if(repaired!=null&&repaired!="") {
			sql=sql+" and repaired = '"+repaired+"'";
		}
		if(level!=null&&level!="") {
			sql=sql+" and level = '"+level+"'";
		}
		sql=sql+" limit "+((Integer.valueOf(page) - 1)*Integer.valueOf(limit))+","+limit;
		return super.getlist(sql);
	}
	//display页面获取所有项目第一条信息，包括repaired最新状态，不分页
		public List<Problem> getAllO(String stitle,String stime,String etime,String repaired,String level) {
			String sql = "select id,serial,serialnum,title,type,level,repaired,recordtime,recorder,recordcont,recordpic from("
					+ "select * from(select ANY_VALUE(id) id,ANY_VALUE(serial) serial,ANY_VALUE(serialnum) serialnum,ANY_VALUE(title) title,ANY_VALUE(type) type,ANY_VALUE(level) level,MAX(repaired) repaired,ANY_VALUE(recordtime) recordtime,ANY_VALUE(recorder) recorder,ANY_VALUE(recordcont) recordcont,ANY_VALUE(recordpic) recordpic from worktrace a"
					+ " group by a.serial)b"
					+ " order by b.recordtime desc"
					+ ")c where 1=1";
			if(stime!=null&&stime!=""&&etime!=null&&etime!="") {
				sql=sql+" and recordtime between '"+stime+"' and '"+etime+"'";
			}
			if(stitle!=null&&stitle!="") {
				sql=sql+" and title regexp '"+stitle+"'";
			}
			if(repaired!=null&&repaired!="") {
				sql=sql+" and repaired = '"+repaired+"'";
			}
			if(level!=null&&level!="") {
				sql=sql+" and level = '"+level+"'";
			}
			return super.getlist(sql);
		}
		
	public List<Problem> getAllSerial() {
		String sql = "select id,serial,serialnum,title,type,level,repaired,recordtime,recorder,recordcont,recordpic,updatetime,updater,updatecont,updatepic from worktrace where serialnum=1";
		return super.getlist(sql);
	}
	
	public List<Problem> getAllSerialByStitle(String stitle) {
		String sql = "select id,serial,serialnum,title,type,level,repaired,recordtime,recorder,recordcont,recordpic,updatetime,updater,updatecont,updatepic from worktrace where serialnum=1 and title regexp ?";
		return super.getlist(sql,stitle);
	}
	
	public List<Problem> getAllSerialByPage(String page,String limit){
		String sql = "select id,serial,serialnum,title,type,level,repaired,recordtime,recorder,recordcont,recordpic,updatetime,updater,updatecont,updatepic from worktrace where serialnum=1 limit "+((Integer.valueOf(page) - 1)*Integer.valueOf(limit))+","+limit+" ;";
		return super.getlist(sql);
	}
	
	public List<Problem> getAllSerialBySP(String page,String limit,String stitle){
		String sql = "select id,serial,serialnum,title,type,level,repaired,recordtime,recorder,recordcont,recordpic,updatetime,updater,updatecont,updatepic from worktrace where serialnum=1 and title regexp ? limit "+((Integer.valueOf(page) - 1)*Integer.valueOf(limit))+","+limit+" ;";
		return super.getlist(sql,stitle);
	}
	
	public List<Problem> getAllSerialNum(String serial) {
		String sql = "select id,serial,serialnum,title,type,level,repaired,recordtime,recorder,recordcont,recordpic,updatetime,updater,updatecont,updatepic from worktrace where serial=? and serialnum!=1";
		return super.getlist(sql,serial);
	}

	public int getMaxSerial() {
		String sql = "select max(serial) from worktrace";
		return (int) super.getValue(sql);
	}
	
	public int add(Problem problem) {
		String sql = "insert into worktrace(serial,serialnum,title,type,level,repaired,recordtime,recorder,recordcont,recordpic,updatetime,updater,updatecont,updatepic) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return super.update(sql,problem.getSerial(),problem.getSerialnum(),problem.getTitle(),problem.getType(),problem.getLevel(),problem.getRepaired(),problem.getRecordtime(),problem.getRecorder(),problem.getRecordcont(),problem.getRecordpic(),problem.getUpdatetime(),problem.getUpdater(),problem.getUpdatecont(),problem.getUpdatepic());
	}
	
	@Override
	public int delete(String serial) {
		String sql = "delete from worktrace where serial=?";
		return super.update(sql, serial);
		
	}
	
	@Override
	public int deleted(String serial,String serialnum) {
		String sql = "delete from worktrace where serial=? and serialnum=?";
		return super.update(sql, serial,serialnum);
		
	}

	public int getMaxSerialNum(String serial){
		String sql = "select max(serialnum) from worktrace where serial=?";
		return (int) super.getValue(sql,serial);
	}
	
	public int addd(Problem problem) {
		String sql = "insert into worktrace(serial,serialnum,title,type,level,repaired,recordtime,recorder,recordcont,recordpic,updatetime,updater,updatecont,updatepic) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return super.update(sql,problem.getSerial(),problem.getSerialnum(),problem.getTitle(),problem.getType(),problem.getLevel(),problem.getRepaired(),problem.getRecordtime(),problem.getRecorder(),problem.getRecordcont(),problem.getRecordpic(),problem.getUpdatetime(),problem.getUpdater(),problem.getUpdatecont(),problem.getUpdatepic());
	}
	
	public Problem getOne(String serial,String serialnum) {
		String sql = "select id,serial,serialnum,title,type,level,repaired,recordtime,recorder,recordcont,recordpic,updatetime,updater,updatecont,updatepic from worktrace where serial=? and serialnum=?";
		return super.get(sql,serial,serialnum);
	}
	
	public List<Problem> getAll(String serial) {
		String sql = "select id,serial,serialnum,title,type,level,repaired,recordtime,recorder,recordcont,recordpic,updatetime,updater,updatecont,updatepic from worktrace where serial=?";
		return super.getlist(sql,serial);
	}
	@Override
	public int wxaddpic(String seiral, String serialnum, String recordpic) {
		String sql = "update worktrace set recordpic=? where serial=? and serialnum=?";
		return super.update(sql,recordpic,seiral,serialnum);
	}
	@Override
	public int wxeditpic(String seiral, String serialnum, String updatepic) {
		String sql = "update worktrace set updatepic=? where serial=? and serialnum=?";
		return super.update(sql,updatepic,seiral,serialnum);
	}
	
}
