package com.example.springbootuploadfile.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ToolUpLoad {
    /**
     * @描述:上传文件到临时目录
     *
     * @param file 上传的文件
     * @param tempPath 上传文件存放路径
     * @return
     */

    public static Map<String, Object> fileUpload(MultipartFile file, String tempPath){
        HashMap<String, Object> resultHashMap = new HashMap<>();
        if(null == file){
            resultHashMap.put("result",false);
            resultHashMap.put("msg","获取上传文件失败,请检查file上传组件的名称是否正确");
        }else if (file.isEmpty()){
            resultHashMap.put("result",false);
            resultHashMap.put("msg","没有选择上传的文件");
        }else{
            File fileDir = new File(tempPath);
            if(!fileDir.exists()){
                fileDir.mkdirs();
            }
            String filename = file.getOriginalFilename();
            filename = tempPath + UUID.randomUUID() + "_" + filename;
            File dest = new File(filename);
            //保存文件
            try {
                file.transferTo(dest);
                resultHashMap.put("result", true);
                resultHashMap.put("msg", "上传成功");
                resultHashMap.put("filePath", "filename");
            } catch (IOException e) {
                e.printStackTrace();
                resultHashMap.put("result", false);
                resultHashMap.put("msg", "文件上传发生异常");
            }
        }
        return resultHashMap;
    }
}
