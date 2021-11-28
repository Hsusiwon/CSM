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
		//���ϴ��������ļ���Ϣ���浽���ݿ�֮ǰ���϶�Ҫ�Ȱ��ļ��浽��������savePath
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
		//ɾ���ļ�ʱ����ɾ�����ݿ���Ϣ����ɾ���ļ�
		uploadFileDao.deleteUploadFile(id);
		//ɾ�������������ϵ��ļ�
		deleteFile(uploadFile.getSavePath()+"/"+uploadFile.getSaveName());//ע�⣺windows"\\"��linux"/"·������һ��
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
		
//		���ļ�д�뱣���Ŀ¼�У������µ��ļ���������һ��Ŀ¼���ļ�̫��������µ�Ŀ¼��
		saveFileName = makeFileName(fileName);
		realSavePath = makePath(saveFileName,savePath);
		
		try {
			File file = new File(realSavePath + "/" + saveFileName);//ע�⣺windows"\\"��linux"/"·������һ��
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
			
			PrintWriter fos = new PrintWriter(file,"UTF-8");//ע�⣺windows"\\"��linux"/"·������һ��
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
		//�Ȱ��ļ�����������������ָ�����ļ�Ŀ¼
//		1.��ȡ�ʹ��������ļ�������Ŀ¼����ʱĿ¼
//		String savePath = req.getSession().getServletContext().getRealPath(this.saveDir);//�����ļ��ķ������ϵľ���·��
//		String tempPath = req.getSession().getServletContext().getRealPath(this.tempDir);//��ʱĿ¼
		String savePath = "/home/csmfile/upload";
		String tempPath = "/home/csmfile/temp";
		File tempFile = new File(tempPath);
		if(!tempFile.exists()) {
			tempFile.mkdir();//�����ʱĿ¼�����ڣ��ô���ķ�ʽ����һ����Ŀ¼
		}
//		2.����һ��������
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(Integer.parseInt(this.sizeThreshold));//100kb��С��100KB�����ڴ��У�����100KB�Ĳ��ַŵ�tempPath
		factory.setRepository(tempFile);//�����ϴ��ļ�����ʱĿ¼		
		//factory�����࣬�������úܶ���ϴ��ļ����������ݣ�
		//���캯��public DiskFileItemFactory(int sizeThreshold,File repositry)
		//int sizeThreshold:����������ڴ棬������Դ���ϴ��ļ������������ļ������ڴ���죬�����ļ�����Ų��¡�����sizeThreshold�ٽ�ֵ��600KB��
		//�ϴ����ļ�С��600KB�Ͱѽ��յ��������ļ������ڴ����棬������Դ��ڴ浱���õ��������ļ���				
		//�ϴ����ļ�����600KB�ͰѴ��������ļ��ֳɺܶಿ�֣����ڴ����ϵ�ĳ����ʱ�ļ��У�������Ҫ�����ļ�����ȥ�ڴ����ʱ�ļ��������ʱ�ļ�ȥȡ��
		//File repositry:������ʱ�ļ��е�λ��
//		3.����request������ļ��ϴ�������
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
		//���������Ҳ�ǿ������ö��ϴ��ļ����������ݣ������ǵ����ļ����������Ҳ�����Ƕ���ļ����ܴ�С
		servletFileUpload.setFileSizeMax(Integer.parseInt(this.fileSizeMax));//�����ϴ������ļ���С��20M����
		servletFileUpload.setHeaderEncoding("UTF-8");//��ֹ�ϴ���������Ϣ������
		servletFileUpload.setSizeMax(Integer.parseInt(this.sizeMax));//���ƶ���ļ�ͬʱ�ϴ���ʱ���ܴ�С���ֵ
//		4.�ж��ύ�ı����ݣ��ǲ���multipart���뷽ʽ
		if(!ServletFileUpload.isMultipartContent(req)) {
			throw new RuntimeException("�ϴ��ļ���Form�ı��뷽ʽ����ȷ��");
		}
//		5.ʹ��servletFileUpload�������ϴ����ݣ�����List����
		String fileName = "";
		String fileType = "";
		long fileSize = 0;
		String fileSizeLevel = "B";
		String fileEx1 = "";
		String saveFileName = "";
		String realSavePath = "";
		String belong = "";
		String writer = "";
		OutputStream out = null;//�����������Ҫ��ѭ���嶨��
		InputStream in = null;
		try {
			List<FileItem> fileList = servletFileUpload.parseRequest(req);
//			6.�ж���ͨ�����ļ���
			if(fileList!=null && fileList.size()>0) {
				for(FileItem fileItem:fileList) {
//				fileItem���Ƿ�װһ��һ��form�ύ�����ı����ͨ����/�ļ������
//				����FileItem����ļ��ϣ���һ�����ж���������ǲ�����ͨ����
					if(fileItem.isFormField()) {
//						7.����ͨ�����д���,����ͨ��ļ�ֵ����ʾ����
						if(fileItem.getFieldName().equals("belong")) {
							//�õ�����������ı����
							//ÿ��Ϊdesc��ֵ��������һ���ļ��Ѿ���������ζ�������һ���ļ����ϴ�����
							belong = fileItem.getString("UTF-8");
						}
						if(fileItem.getFieldName().equals("username")) {
							writer = fileItem.getString("UTF-8");
							//��������ϴ��õ��ļ���Ϣд�����ݿ�����
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
						//�ϴ��������ļ����õ������ļ���Ϣ�ͱ���
						fileName = fileItem.getName();//�õ��ļ�������,IE�õ����о���·��ȫ��D:\ABC\D.doc������õ����Ǳ���D.doc
						fileType = fileItem.getContentType();//�õ��ļ�������image/jpg
						fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
						fileSize = fileItem.getSize();//�õ��ļ����ܴ�С
						if (fileSize/1024>=1 && fileSize/1024/1024<1) {
							fileSize = fileSize/1024;
							fileSizeLevel = "KB";
						}else if (fileSize/1024/1024>=1) {
							fileSize = fileSize/1024/1024;
							fileSizeLevel = "MB";
						}
						fileEx1 = fileName.substring(fileName.lastIndexOf(".")+1);
//						8.��֤��׺�ĺϷ���
						if(this.fileEx.indexOf(fileEx1) == -1) {
							
							throw new RuntimeException("��ֹ�ϴ����ļ����ͣ��ļ���׺��"+fileEx1);
						}
//						9.���ļ�д�뱣���Ŀ¼�У������µ��ļ���������һ��Ŀ¼���ļ�̫��������µ�Ŀ¼��
						saveFileName = makeFileName(fileName);
						realSavePath = makePath(saveFileName,savePath);
						//�ȴ���һ�������
						 out = new FileOutputStream(realSavePath + "/" + saveFileName);//ע�⣺windows"\\"��linux"/"·������һ��
						 in = fileItem.getInputStream();
						//���������������һ�������ļ�������������
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
			//ɾ������ʱĿ¼�µ���ʱ�ļ�
			File tempD = new File(tempPath);
			for(File file:tempD.listFiles()) {
				file.delete();
			}
		}catch(FileUploadBase.SizeLimitExceededException e) {
			throw new RuntimeException("�ϴ��ļ��ܴ�С���������ƣ�"+Integer.parseInt(this.sizeMax)/(1024*1024)+"MB!");
		}catch(FileUploadBase.FileSizeLimitExceededException e) {
			throw new RuntimeException("�ϴ������ļ��ܴ�С���������ƣ�"+Integer.parseInt(this.fileSizeMax)/(1024*1024)+"MB!");
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
		//�ٰ��ļ���Ϣ���浽���ݿ�
		
	}

	public void deleteFile(String filePath) {
		//ɾ���������ϵ��ϴ��ļ�
		File file = new File(filePath);
		if(file.isFile()) {
			file.delete();
		}
	}
	
	private String makePath(String saveFileName, String savePath) {
		//�õ��ļ��������ڴ��еĵ�ַ��hashCodeֵ
		int hashCode = saveFileName.hashCode();
		int dir1 = hashCode&0xf;//dir1��ֵ�����������ķ�Χ��0-15
		int dir2 = (hashCode >> 4)&0xf;
		//��dir1��dir2�����ҵ��µĴ洢�ļ�Ŀ¼
		String dir = savePath+"/"+dir1+"/"+dir2;//ע�⣺windows��linux·������һ��
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
			//�Ȱ��ļ�����������������ָ�����ļ�Ŀ¼
//			1.��ȡ�ʹ��������ļ�������Ŀ¼����ʱĿ¼
//			String savePath = req.getSession().getServletContext().getRealPath(this.saveDir);//�����ļ��ķ������ϵľ���·��
//			String tempPath = req.getSession().getServletContext().getRealPath(this.tempDir);//��ʱĿ¼
			String savePath = "/home/csmfile/upload";
			String tempPath = "/home/csmfile/temp";
			File tempFile = new File(tempPath);
			if(!tempFile.exists()) {
				tempFile.mkdir();//�����ʱĿ¼�����ڣ��ô���ķ�ʽ����һ����Ŀ¼
			}
//			2.����һ��������
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(Integer.parseInt(this.sizeThreshold));//100kb��С��100KB�����ڴ��У�����100KB�Ĳ��ַŵ�tempPath
			factory.setRepository(tempFile);//�����ϴ��ļ�����ʱĿ¼		
			//factory�����࣬�������úܶ���ϴ��ļ����������ݣ�
			//���캯��public DiskFileItemFactory(int sizeThreshold,File repositry)
			//int sizeThreshold:����������ڴ棬������Դ���ϴ��ļ������������ļ������ڴ���죬�����ļ�����Ų��¡�����sizeThreshold�ٽ�ֵ��600KB��
			//�ϴ����ļ�С��600KB�Ͱѽ��յ��������ļ������ڴ����棬������Դ��ڴ浱���õ��������ļ���				
			//�ϴ����ļ�����600KB�ͰѴ��������ļ��ֳɺܶಿ�֣����ڴ����ϵ�ĳ����ʱ�ļ��У�������Ҫ�����ļ�����ȥ�ڴ����ʱ�ļ��������ʱ�ļ�ȥȡ��
			//File repositry:������ʱ�ļ��е�λ��
//			3.����request������ļ��ϴ�������
			ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
			//���������Ҳ�ǿ������ö��ϴ��ļ����������ݣ������ǵ����ļ����������Ҳ�����Ƕ���ļ����ܴ�С
			servletFileUpload.setFileSizeMax(Integer.parseInt(this.fileSizeMax));//�����ϴ������ļ���С��20M����
			servletFileUpload.setHeaderEncoding("UTF-8");//��ֹ�ϴ���������Ϣ������
			servletFileUpload.setSizeMax(Integer.parseInt(this.sizeMax));//���ƶ���ļ�ͬʱ�ϴ���ʱ���ܴ�С���ֵ
//			4.�ж��ύ�ı����ݣ��ǲ���multipart���뷽ʽ
			if(!ServletFileUpload.isMultipartContent(req)) {
				throw new RuntimeException("�ϴ��ļ���Form�ı��뷽ʽ����ȷ��");
			}
//			5.ʹ��servletFileUpload�������ϴ����ݣ�����List����
			String fileName = "";
			String fileType = "";
			long fileSize = 0;
			String fileSizeLevel = "B";
			String fileEx1 = "";
			String saveFileName = "";
			String realSavePath = "";
			String belong = "";
			String writer = "";
			OutputStream out = null;//�����������Ҫ��ѭ���嶨��
			InputStream in = null;
			try {
				List<FileItem> fileList = servletFileUpload.parseRequest(req);
//				6.�ж���ͨ�����ļ���
				if(fileList!=null && fileList.size()>0) {
					for(FileItem fileItem:fileList) {
//					fileItem���Ƿ�װһ��һ��form�ύ�����ı����ͨ����/�ļ������
//					����FileItem����ļ��ϣ���һ�����ж���������ǲ�����ͨ����
						if(fileItem.isFormField()) {
							
//							7.����ͨ�����д���,����ͨ��ļ�ֵ����ʾ����
							if(fileItem.getFieldName().equals("belong")) {
								//�õ�����������ı����
								//ÿ��Ϊdesc��ֵ��������һ���ļ��Ѿ���������ζ�������һ���ļ����ϴ�����
								belong = fileItem.getString("UTF-8");
								
							}
							if(fileItem.getFieldName().equals("username")) {
								writer = fileItem.getString("UTF-8");
								
							}
							if(fileItem.getFieldName().equals("filename")) {
								fileName = fileItem.getString("UTF-8");
								
							}
						}else {
							//�ϴ��������ļ����õ������ļ���Ϣ�ͱ���
							
							fileType = fileItem.getContentType();//�õ��ļ�������image/jpg
							
							fileSize = fileItem.getSize();//�õ��ļ����ܴ�С
							if (fileSize/1024>=1 && fileSize/1024/1024<1) {
								fileSize = fileSize/1024;
								fileSizeLevel = "KB";
							}else if (fileSize/1024/1024>=1) {
								fileSize = fileSize/1024/1024;
								fileSizeLevel = "MB";
							}
							fileEx1 = fileName.substring(fileName.lastIndexOf(".")+1);
//							8.��֤��׺�ĺϷ���
							if(this.fileEx.indexOf(fileEx1) == -1) {
								
								throw new RuntimeException("��ֹ�ϴ����ļ����ͣ��ļ���׺��"+fileEx1);
							}
//							9.���ļ�д�뱣���Ŀ¼�У������µ��ļ���������һ��Ŀ¼���ļ�̫��������µ�Ŀ¼��
							saveFileName = makeFileName(fileName);
							realSavePath = makePath(saveFileName,savePath);
							//�ȴ���һ�������
							 out = new FileOutputStream(realSavePath + "/" + saveFileName);//ע�⣺windows"\\"��linux"/"·������һ��
							 in = fileItem.getInputStream();
							//���������������һ�������ļ�������������
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
				//ɾ������ʱĿ¼�µ���ʱ�ļ�
				File tempD = new File(tempPath);
				for(File file:tempD.listFiles()) {
					file.delete();
				}
			}catch(FileUploadBase.SizeLimitExceededException e) {
				throw new RuntimeException("�ϴ��ļ��ܴ�С���������ƣ�"+Integer.parseInt(this.sizeMax)/(1024*1024)+"MB!");
			}catch(FileUploadBase.FileSizeLimitExceededException e) {
				throw new RuntimeException("�ϴ������ļ��ܴ�С���������ƣ�"+Integer.parseInt(this.fileSizeMax)/(1024*1024)+"MB!");
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
			//�ٰ��ļ���Ϣ���浽���ݿ�
			
		}

}
