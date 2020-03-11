package com.example.springbootuploadfile.contrlller;

import com.example.springbootuploadfile.util.ToolUpLoad;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
public class UploadController {

    @GetMapping("/")
    public String up(){
        return "upload-file";
    }
    @PostMapping("/upload")
    public String upload(MultipartFile file){
        //文件上传
        Map<String, Object> fileUpload = ToolUpLoad.fileUpload(file,"D:\\logs");
        return JSONObject.valueToString(fileUpload);
    }
}
