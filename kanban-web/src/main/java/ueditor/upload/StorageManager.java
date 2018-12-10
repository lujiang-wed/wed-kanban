package ueditor.upload;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StorageManager
{
//    public static final int BUFFER_SIZE = 8192;
//    private static final String DEFAULT_URL = "http://172.24.6.27/";
//    private static final String NAME = "Coach";
//    static final Logger logger = LoggerFactory.getLogger(StorageManager.class);
//
//    public static String saveBinaryFile(byte[] fileBuffer, String fileName)
//    {
//        UploadClient uploadClient = UploadClient.getInstance();
//        uploadClient.setUrl(DEFAULT_URL);
//        uploadClient.setAppId(NAME);
//
//        try{
//            UploadFacade uploadFacade = uploadClient.getUploadFacade();
//            String code = uploadFacade.upload(fileName,fileBuffer);
//            logger.info("上传结果:{}",code);
//            return DEFAULT_URL + code.substring(code.indexOf("code")+7,code.length()-3);
//        }catch (IOException e){
//            logger.info("文件上传失败");
//            return "IO_ERROR";
//        }
//    }
//
//    public static String saveFileByInputStream(InputStream is, String fileName, long maxSize)
//    {
//        BufferedInputStream bis = new BufferedInputStream(is, BUFFER_SIZE);
//        try
//        {
//            int allCount = 0;
//            List<byte[]> readList = new LinkedList<byte[]>();
//            int count = 0;
//            byte[] dataBuf = new byte[2048];
//            while ((count = bis.read(dataBuf)) != -1) {
//                byte[] tempBuf = new byte[count];
//                System.arraycopy(dataBuf,0,tempBuf,0,count);
//                readList.add(tempBuf);
//                allCount = allCount + count;
//            }
//
//            if (allCount > maxSize) {
//                logger.info("文件太大{}",allCount);
//                return "MAX_SIZE";
//            }
//
//            byte[] fileBuffer = new byte[allCount];
//            int index = 0;
//            for (byte[] tempBuf : readList){
//                System.arraycopy(tempBuf,0,fileBuffer,index,tempBuf.length);
//                index = index + tempBuf.length;
//            }
//            return saveBinaryFile(fileBuffer,fileName);
//        }
//        catch (IOException localIOException) {
//            logger.info("将文件搞进byte数组失败");
//            return "IO_ERROR";
//        }
//    }


}