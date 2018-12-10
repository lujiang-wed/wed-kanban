package com.wednesday.kanban.web.Index.Controller;

import org.springframework.stereotype.Controller;

@Controller
public class UploadFileController {
//    private static final String DEFAULT_URL = "http://172.24.6.27/";
//    private static final String NAME = "myApp";
//
//    private static final Logger logger = LoggerFactory.getLogger(UploadFileController.class);
//
//    @RequestMapping(value = "/uploadFile.html",method = RequestMethod.POST)
//    public String upload(HttpServletRequest request, HttpServletResponse response,Model model){
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//        if (multipartResolver.isMultipart(request)){
//            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
//            Iterator<String> iter = multiRequest.getFileNames();
//
//            UploadClient uploadClient = UploadClient.getInstance();
//            uploadClient.setUrl(DEFAULT_URL);
//            uploadClient.setAppId(NAME);
//
//            if (iter.hasNext()){
//                MultipartFile file = multiRequest.getFile(iter.next());
//
//                UploadFacade uploadFacade = uploadClient.getUploadFacade();
//                try{
//                    String code = uploadFacade.upload(NAME,file.getBytes());
//                    String resultPath = DEFAULT_URL + code.substring(code.indexOf("code")+7,code.length()-3);
//                    model.addAttribute("result","成功,code="+code+"路径为："+resultPath);
//                }catch (Exception e){
//                    logger.info("上传图片失败!");
//                    model.addAttribute("result","失败");
//                }
//            }
//        }
//        return "uploadResult";
//    }
//
//    @RequestMapping(value = "/uploadFile.html",method = RequestMethod.GET)
//    public String index(){
//        return "uploadFile";
//    }
}
