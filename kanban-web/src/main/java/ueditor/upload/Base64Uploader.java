package ueditor.upload;

import org.apache.commons.codec.binary.Base64;

public final class Base64Uploader
{
//    public static State save(String content, Map<String, Object> conf)
//    {
//        byte[] data = decode(content);
//
//        long maxSize = ((Long)conf.get("maxSize")).longValue();
//
//        if (!validSize(data, maxSize)) {
//            return new BaseState(false, 1);
//        }
//
//        String suffix = FileType.getSuffix("JPG");
//
//        String url = StorageManager.saveBinaryFile(data, (String)conf.get("filename"));
//        State storageState = new BaseState();
//
//        if (null != url) {
//            storageState.putInfo("url", url);
//            storageState.putInfo("type", suffix);
//            storageState.putInfo("original", "");
//        }
//
//        return storageState;
//    }

    private static byte[] decode(String content) {
        return Base64.decodeBase64(content);
    }

    private static boolean validSize(byte[] data, long length) {
        return data.length <= length;
    }
}