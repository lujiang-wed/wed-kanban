package ueditor.upload;


import com.baidu.ueditor.define.FileType;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;


import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import ueditor.define.AppInfo;


public class BinaryUploader
{
    public static final State save(HttpServletRequest request, Map<String, Object> conf)
    {
//        FileItemStream fileStream = null;
//        boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;
//
//        if (!ServletFileUpload.isMultipartContent(request)) {
//            return new BaseState(false, 5);
//        }
//
//        ServletFileUpload upload = new ServletFileUpload(
//                new DiskFileItemFactory());
//
//        if (isAjaxUpload) {
//            upload.setHeaderEncoding("UTF-8");
//        }
//        try
//        {
//            FileItemIterator iterator = upload.getItemIterator(request);
//
//            while (iterator.hasNext()) {
//                fileStream = iterator.next();
//
//                if (!fileStream.isFormField())
//                    break;
//                fileStream = null;
//            }
//
//            if (fileStream == null) {
//                return new BaseState(false, 7);
//            }
//
//            String originFileName = fileStream.getName();
//            String suffix = FileType.getSuffixByFilename(originFileName);
//
//            originFileName = originFileName.substring(0,
//                    originFileName.length() - suffix.length());
//
//            long maxSize = ((Long)conf.get("maxSize")).longValue();
//
//            if (!validType(suffix, (String[])conf.get("allowFiles"))) {
//                return new BaseState(false, 8);
//            }
//
//            InputStream is = fileStream.openStream();
//            String result = StorageManager.saveFileByInputStream(is,
//                    fileStream.getName(), maxSize);
//            is.close();
//
//
//            if (null == result){
//                return new BaseState(Boolean.FALSE, AppInfo.NOT_EXIST);
//            }else if ("MAX_SIZE".equals(result)){
//                return new BaseState(Boolean.FALSE, AppInfo.MAX_SIZE);
//            }else if ("IO_ERROR".equals(result)){
//                new BaseState(Boolean.FALSE, AppInfo.IO_ERROR);
//            }else {
//                State storageState = new BaseState();
//                storageState.putInfo("url", result);
//                storageState.putInfo("type", suffix);
//                storageState.putInfo("original", originFileName + suffix);
//                return storageState;
//            }
//        } catch (FileUploadException e) {
//            return new BaseState(false, 6);
//        } catch (IOException localIOException) {
//        }
        return new BaseState(false, 4);
    }

    private static boolean validType(String type, String[] allowTypes) {
        List list = Arrays.asList(allowTypes);

        return list.contains(type);
    }
}