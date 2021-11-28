package cn.sion.csm.utils;

import java.io.File;


import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;


public class office2PDF {
	private static String openOfficePath = "/opt/openoffice4";

    public static void officeToPDF(String sourceFile, String destFile) {
        try {
            File inputFile = new File(sourceFile);
            if (!inputFile.exists()) {
            	System.out.println("找不到源文件");
               throw new Exception("找不到源文件");
            }
            File outputFile = new File(destFile);
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            String OpenOffice_HOME = openOfficePath;
            if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '\\') {
                OpenOffice_HOME += "\\";
            }
            // 连接OpenOffice的服务  安装之后默认ip和端口
//            String command = OpenOffice_HOME
//                    + "soffice -headless -accept=\"socket,host=0.0.0.1,port=8100;urp;\" -nofirststartwizard";
            OpenOfficeConnection connection = new SocketOpenOfficeConnection(
                    "192.168.168.3", 8100);
            connection.connect();
            DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
            converter.convert(inputFile, outputFile);
            connection.disconnect();
//            return 0;
        } catch (Exception e) {
        	System.out.println("转换失败：{}"+e.getMessage());
            e.printStackTrace();
        }
//        System.out.println("转换成功");
    }


}
