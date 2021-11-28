package cn.sion.csm.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.sion.csm.dao.FactoryDao;
import cn.sion.csm.dao.UploadFileDao;
import cn.sion.csm.model.UploadFile;
import cn.sion.csm.utils.UploadFilePropertiesUtil;

public class UploadFileServiceImpl implements UploadFileService {

	UploadFileDao uploadFileDao = FactoryDao.getUploadFileDao();
	
//	private String saveDir = UploadFilePropertiesUtil.getInstance().getProperty("savePath");
//	private String tempDir = UploadFilePropertiesUtil.getInstance().getProperty("tempPath");
	private String sizeThreshold = UploadFilePropertiesUtil.getInstance().getProperty("sizeThreshold");
	private String fileSizeMax = UploadFilePropertiesUtil.getInstance().getProperty("fileSizeMax");
	private String sizeMax = UploadFilePropertiesUtil.getInstance().getProperty("sizeMax");
	private String fileEx = UploadFilePropertiesUtil.getInstance().getProperty("fileEx");
	
	public void addFileInfo(UploadFile uploadFile) {
		//把上传上来的文件信息保存到数据库之前，肯定要先把文件存到服务器上savePath
		uploadFileDao.addFileInfo(uploadFile);
	}

	@Override
	public List<UploadFile> getUploadFiles() {
		return uploadFileDao.getUploadFiles();
	}
	
	@Override
	public List<UploadFile> getUploadFilesByPage(String page,String limit) {
		return uploadFileDao.getUploadFilesByPage(page,limit);
	}
	
	@Override
	public List<UploadFile> getUploadFilesByStitle(String stitle) {
		return uploadFileDao.getUploadFilesByStitle(stitle);
	}
	
	@Override
	public List<UploadFile> getUploadFilesBySP(String page,String limit,String stitle) {
		return uploadFileDao.getUploadFilesBySP(page,limit,stitle);
	}
	
	@Override
	public long countUploadFilesByBelong(String belong) {
		return uploadFileDao.countUploadFilesByBelong(belong);
	}
	
	@Override
	public List<UploadFile> getUploadFilesByBelong(String page,String limit,String belong) {
		return uploadFileDao.getUploadFilesByBelong(page,limit,belong);
	}

	@Override
	public void deleteUploadFile(int id) {
		UploadFile uploadFile = uploadFileDao.get(id);
		//删除文件时，先删除数据库信息，再删除文件
		uploadFileDao.deleteUploadFile(id);
		//删除服务器磁盘上的文件
		deleteFile(uploadFile.getSavePath()+"/"+uploadFile.getSaveName());//注意：windows"\\"和linux"/"路径符不一样
	}

	public void writeFile(HttpServletRequest req, HttpServletResponse resp) {
		
		String fileName = req.getParameter("filename")+".txt";
		String fileContent = req.getParameter("filecontent");
		String belong = req.getParameter("belong");
		String username = req.getParameter("username");
		
//		String savePath = req.getSession().getServletContext().getRealPath(this.saveDir);
		String savePath = "/home/csmfile/upload";
		
		long fileSize = 0;
		String fileSizeLevel = "B";
		String saveFileName = "";
		String realSavePath = "";
		String fileType = "txt";
		
//		将文件写入保存的目录中（生成新的文件名，避免一个目录中文件太多而生成新的目录）
		saveFileName = makeFileName(fileName);
		realSavePath = makePath(saveFileName,savePath);
		
		try {
			File file = new File(realSavePath + "/" + saveFileName);//注意：windows"\\"和linux"/"路径符不一样
			if (!file.exists()) {
				file.createNewFile();
			}
			
			fileSize=fileContent.getBytes().length;
			if (fileSize/1024>=1 && fileSize/1024/1024<1) {
				fileSize = fileSize/1024;
				fileSizeLevel = "KB";
			}else if (fileSize/1024/1024>=1) {
				fileSize = fileSize/1024/1024;
				fileSizeLevel = "MB";
			}
			
			PrintWriter fos = new PrintWriter(file,"UTF-8");//注意：windows"\\"和linux"/"路径符不一样
			fos.println(fileContent);		
			fos.close();
//			FileOutputStream fos = new FileOutputStream(file);
//			fos.write(fileContent.getBytes());		
//			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		UploadFile uf=new UploadFile();
		uf.setFileName(fileName);
		uf.setFileType(fileType);
		uf.setFileSize(fileSize+fileSizeLevel);	
		uf.setSavePath(realSavePath);
		uf.setSaveTime(new Date());
		uf.setSaveName(saveFileName);
		uf.setWriter(username);
		uf.setBelong(belong);
		addFileInfo(uf);
	}
	
	//Unnecessary@SuppressWarnings("unchecked")
	@Override
	public void saveFile(HttpServletRequest req, HttpServletResponse resp) {
		//先把文件保存下来到服务器指定的文件目录
//		1.获取和创建保存文件的最终目录和临时目录
//		String savePath = req.getSession().getServletContext().getRealPath(this.saveDir);//保存文件的服务器上的绝对路径
//		String tempPath = req.getSession().getServletContext().getRealPath(this.tempDir);//临时目录
		String savePath = "/home/csmfile/upload";
		String tempPath = "/home/csmfile/temp";
		File tempFile = new File(tempPath);
		if(!tempFile.exists()) {
			tempFile.mkdir();//如果临时目录不存在，用代码的方式创建一个新目录
		}
//		2.创建一个工厂类
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(Integer.parseInt(this.sizeThreshold));//100kb，小于100KB放在内存中，大于100KB的部分放到tempPath
		factory.setRepository(tempFile);//设置上传文件的临时目录		
		//factory工厂类，可以设置很多对上传文件的限制内容！
		//构造函数public DiskFileItemFactory(int sizeThreshold,File repositry)
		//int sizeThreshold:服务器里的内存，有限资源，上传文件，传过来的文件放在内存里，快，但是文件过大放不下。限制sizeThreshold临界值，600KB。
		//上传的文件小于600KB就把接收到的整个文件放在内存里面，程序可以从内存当中拿到完整的文件。				
		//上传的文件大于600KB就把传过来的文件分成很多部分，放在磁盘上的某个临时文件夹，程序需要整个文件，就去内存和临时文件夹里的临时文件去取。
		//File repositry:配置临时文件夹的位置
//		3.创建request请求的文件上传解析器
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
		//这个解析器也是可以设置对上传文件的限制内容；可以是单个文件最大容量，也可以是多个文件的总大小
		servletFileUpload.setFileSizeMax(Integer.parseInt(this.fileSizeMax));//限制上传单个文件大小在20M以内
		servletFileUpload.setHeaderEncoding("UTF-8");//防止上传的中文信息是乱码
		servletFileUpload.setSizeMax(Integer.parseInt(this.sizeMax));//限制多个文件同时上传的时候，总大小最大值
//		4.判断提交的表单数据，是不是multipart编码方式
		if(!ServletFileUpload.isMultipartContent(req)) {
			throw new RuntimeException("上传文件的Form的编码方式不正确！");
		}
//		5.使用servletFileUpload解析器上传数据，返回List集合
		String fileName = "";
		String fileType = "";
		long fileSize = 0;
		String fileSizeLevel = "B";
		String fileEx1 = "";
		String saveFileName = "";
		String realSavePath = "";
		String belong = "";
		String writer = "";
		OutputStream out = null;//输入输出流不要在循环体定义
		InputStream in = null;
		try {
			List<FileItem> fileList = servletFileUpload.parseRequest(req);
//			6.判断普通域还是文件域
			if(fileList!=null && fileList.size()>0) {
				for(FileItem fileItem:fileList) {
//				fileItem就是封装一个一个form提交过来的表单项：普通表单项/文件域表单项
//				遍历FileItem对象的集合，第一步，判断这个表单项是不是普通表单项
					if(fileItem.isFormField()) {
//						7.像普通表单进行处理,将普通表的键值对显示出来
						if(fileItem.getFieldName().equals("belong")) {
							//拿到这个传上来的表单项键
							//每次为desc赋值，代表着一个文件已经上来，意味着完成了一个文件的上传操作
							belong = fileItem.getString("UTF-8");
						}
						if(fileItem.getFieldName().equals("username")) {
							writer = fileItem.getString("UTF-8");
							//在这里把上传好的文件信息写到数据库里面
							if(!"".equals(fileName)) {
								UploadFile uf=new UploadFile();
								uf.setFileName(fileName);
								uf.setFileType(fileType);
								uf.setFileSize(fileSize+fileSizeLevel);	
								uf.setSavePath(realSavePath);
								uf.setSaveTime(new Date());
								uf.setSaveName(saveFileName);
								uf.setWriter(writer);
								uf.setBelong(belong);
								addFileInfo(uf);
							}
						}
					}else {
						//上传上来的文件，拿到各种文件信息和本身
						fileName = fileItem.getName();//拿到文件的名字,IE拿到的有绝对路径全名D:\ABC\D.doc，火狐拿到就是本身D.doc
						fileType = fileItem.getContentType();//拿到文件的类型image/jpg
						fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
						fileSize = fileItem.getSize();//拿到文件的总大小
						if (fileSize/1024>=1 && fileSize/1024/1024<1) {
							fileSize = fileSize/1024;
							fileSizeLevel = "KB";
						}else if (fileSize/1024/1024>=1) {
							fileSize = fileSize/1024/1024;
							fileSizeLevel = "MB";
						}
						fileEx1 = fileName.substring(fileName.lastIndexOf(".")+1);
//						8.验证后缀的合法性
						if(this.fileEx.indexOf(fileEx1) == -1) {
							
							throw new RuntimeException("禁止上传该文件类型，文件后缀："+fileEx1);
						}
//						9.将文件写入保存的目录中（生成新的文件名，避免一个目录中文件太多而生成新的目录）
						saveFileName = makeFileName(fileName);
						realSavePath = makePath(saveFileName,savePath);
						//先创建一个输出流
						 out = new FileOutputStream(realSavePath + "/" + saveFileName);//注意：windows"\\"和linux"/"路径符不一样
						 in = fileItem.getInputStream();
						//建立缓冲过区，做一个搬运文件数据流的勺子
						 byte[] buffer = new byte[1024];
						 int len = 0;
						 while((len=in.read(buffer))>0) {
							 out.write(buffer, 0, len);
						 }
						 in.close();
						 out.close();
					}
				}				
			}
			//删除掉临时目录下的临时文件
			File tempD = new File(tempPath);
			for(File file:tempD.listFiles()) {
				file.delete();
			}
		}catch(FileUploadBase.SizeLimitExceededException e) {
			throw new RuntimeException("上传文件总大小超出了限制："+Integer.parseInt(this.sizeMax)/(1024*1024)+"MB!");
		}catch(FileUploadBase.FileSizeLimitExceededException e) {
			throw new RuntimeException("上传单个文件总大小超出了限制："+Integer.parseInt(this.fileSizeMax)/(1024*1024)+"MB!");
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}finally {
			if(in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(out!=null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}	
		//再把文件信息保存到数据库
		
	}

	public void deleteFile(String filePath) {
		//删除服务器上的上传文件
		File file = new File(filePath);
		if(file.isFile()) {
			file.delete();
		}
	}
	
	private String makePath(String saveFileName, String savePath) {
		//拿到文件名，在内存中的地址，hashCode值
		int hashCode = saveFileName.hashCode();
		int dir1 = hashCode&0xf;//dir1的值，这个与运算的范围在0-15
		int dir2 = (hashCode >> 4)&0xf;
		//用dir1和dir2构造我的新的存储文件目录
		String dir = savePath+"/"+dir1+"/"+dir2;//注意：windows和linux路径符不一样
		File file = new File(dir);
		if(!file.exists()) {
			file.mkdirs();
		}
		return dir;
	}

	private String makeFileName(String fileName) {
		//uuid
		return UUID.randomUUID().toString()+"_"+fileName;
	}

	@Override
	public UploadFile getUploadFileById(int id) {
		return uploadFileDao.get(id);
	}

	@Override
	public List<UploadFile> getUploadFilesByBelongwx(String belong) {
		return uploadFileDao.getUploadFilesByBelongwx(belong);
	}
	
	//Unnecessary@SuppressWarnings("unchecked")
		@Override
		public void wxsaveFile(HttpServletRequest req, HttpServletResponse resp) {
			//先把文件保存下来到服务器指定的文件目录
//			1.获取和创建保存文件的最终目录和临时目录
//			String savePath = req.getSession().getServletContext().getRealPath(this.saveDir);//保存文件的服务器上的绝对路径
//			String tempPath = req.getSession().getServletContext().getRealPath(this.tempDir);//临时目录
			String savePath = "/home/csmfile/upload";
			String tempPath = "/home/csmfile/temp";
			File tempFile = new File(tempPath);
			if(!tempFile.exists()) {
				tempFile.mkdir();//如果临时目录不存在，用代码的方式创建一个新目录
			}
//			2.创建一个工厂类
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(Integer.parseInt(this.sizeThreshold));//100kb，小于100KB放在内存中，大于100KB的部分放到tempPath
			factory.setRepository(tempFile);//设置上传文件的临时目录		
			//factory工厂类，可以设置很多对上传文件的限制内容！
			//构造函数public DiskFileItemFactory(int sizeThreshold,File repositry)
			//int sizeThreshold:服务器里的内存，有限资源，上传文件，传过来的文件放在内存里，快，但是文件过大放不下。限制sizeThreshold临界值，600KB。
			//上传的文件小于600KB就把接收到的整个文件放在内存里面，程序可以从内存当中拿到完整的文件。				
			//上传的文件大于600KB就把传过来的文件分成很多部分，放在磁盘上的某个临时文件夹，程序需要整个文件，就去内存和临时文件夹里的临时文件去取。
			//File repositry:配置临时文件夹的位置
//			3.创建request请求的文件上传解析器
			ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
			//这个解析器也是可以设置对上传文件的限制内容；可以是单个文件最大容量，也可以是多个文件的总大小
			servletFileUpload.setFileSizeMax(Integer.parseInt(this.fileSizeMax));//限制上传单个文件大小在20M以内
			servletFileUpload.setHeaderEncoding("UTF-8");//防止上传的中文信息是乱码
			servletFileUpload.setSizeMax(Integer.parseInt(this.sizeMax));//限制多个文件同时上传的时候，总大小最大值
//			4.判断提交的表单数据，是不是multipart编码方式
			if(!ServletFileUpload.isMultipartContent(req)) {
				throw new RuntimeException("上传文件的Form的编码方式不正确！");
			}
//			5.使用servletFileUpload解析器上传数据，返回List集合
			String fileName = "";
			String fileType = "";
			long fileSize = 0;
			String fileSizeLevel = "B";
			String fileEx1 = "";
			String saveFileName = "";
			String realSavePath = "";
			String belong = "";
			String writer = "";
			OutputStream out = null;//输入输出流不要在循环体定义
			InputStream in = null;
			try {
				List<FileItem> fileList = servletFileUpload.parseRequest(req);
//				6.判断普通域还是文件域
				if(fileList!=null && fileList.size()>0) {
					for(FileItem fileItem:fileList) {
//					fileItem就是封装一个一个form提交过来的表单项：普通表单项/文件域表单项
//					遍历FileItem对象的集合，第一步，判断这个表单项是不是普通表单项
						if(fileItem.isFormField()) {
							
//							7.像普通表单进行处理,将普通表的键值对显示出来
							if(fileItem.getFieldName().equals("belong")) {
								//拿到这个传上来的表单项键
								//每次为desc赋值，代表着一个文件已经上来，意味着完成了一个文件的上传操作
								belong = fileItem.getString("UTF-8");
								
							}
							if(fileItem.getFieldName().equals("username")) {
								writer = fileItem.getString("UTF-8");
								
							}
							if(fileItem.getFieldName().equals("filename")) {
								fileName = fileItem.getString("UTF-8");
								
							}
						}else {
							//上传上来的文件，拿到各种文件信息和本身
							
							fileType = fileItem.getContentType();//拿到文件的类型image/jpg
							
							fileSize = fileItem.getSize();//拿到文件的总大小
							if (fileSize/1024>=1 && fileSize/1024/1024<1) {
								fileSize = fileSize/1024;
								fileSizeLevel = "KB";
							}else if (fileSize/1024/1024>=1) {
								fileSize = fileSize/1024/1024;
								fileSizeLevel = "MB";
							}
							fileEx1 = fileName.substring(fileName.lastIndexOf(".")+1);
//							8.验证后缀的合法性
							if(this.fileEx.indexOf(fileEx1) == -1) {
								
								throw new RuntimeException("禁止上传该文件类型，文件后缀："+fileEx1);
							}
//							9.将文件写入保存的目录中（生成新的文件名，避免一个目录中文件太多而生成新的目录）
							saveFileName = makeFileName(fileName);
							realSavePath = makePath(saveFileName,savePath);
							//先创建一个输出流
							 out = new FileOutputStream(realSavePath + "/" + saveFileName);//注意：windows"\\"和linux"/"路径符不一样
							 in = fileItem.getInputStream();
							//建立缓冲过区，做一个搬运文件数据流的勺子
							 byte[] buffer = new byte[1024];
							 int len = 0;
							 while((len=in.read(buffer))>0) {
								 out.write(buffer, 0, len);
							 }
							 in.close();
							 out.close();
							 if(!"".equals(fileName)) {
									UploadFile uf=new UploadFile();
									uf.setFileName(fileName);
									uf.setFileType(fileType);
									uf.setFileSize(fileSize+fileSizeLevel);	
									uf.setSavePath(realSavePath);
									uf.setSaveTime(new Date());
									uf.setSaveName(saveFileName);
									uf.setWriter(writer);
									uf.setBelong(belong);
									
									addFileInfo(uf);
								}
						}
					}				
				}
				//删除掉临时目录下的临时文件
				File tempD = new File(tempPath);
				for(File file:tempD.listFiles()) {
					file.delete();
				}
			}catch(FileUploadBase.SizeLimitExceededException e) {
				throw new RuntimeException("上传文件总大小超出了限制："+Integer.parseInt(this.sizeMax)/(1024*1024)+"MB!");
			}catch(FileUploadBase.FileSizeLimitExceededException e) {
				throw new RuntimeException("上传单个文件总大小超出了限制："+Integer.parseInt(this.fileSizeMax)/(1024*1024)+"MB!");
			}catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}finally {
				if(in!=null) {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(out!=null) {
					try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}	
			//再把文件信息保存到数据库
			
		}

}
