package com.zihexin.fileload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2018/6/26.
 */
@Controller
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @RequestMapping("upload")
    @ResponseBody
    public String upload(@RequestParam("upfile") MultipartFile upFile) {
        //获取文件名
        String fileName = upFile.getOriginalFilename();
        logger.info("上传的文件名：{}", fileName);
        //获取文件后缀
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
        logger.info("文件后缀：{}", fileSuffix);
        String savePath = "D:\\fileload\\upload\\";
        String saveName = UUID.randomUUID() + fileSuffix;
        logger.info("文件保存文件名：{}", saveName);
        File dest = new File(savePath + saveName);
        if (!dest.getParentFile().exists()) {
            dest.mkdirs();
        }
        try {
            upFile.transferTo(dest);
            return "文件上传成功";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "文件上传失败";
    }

    @RequestMapping("/download")
    public String download(@RequestParam("downfile") String downLoadFile, HttpServletResponse response) {

        logger.info("下载的文件名：{}",downLoadFile);
        String filePath = "D:\\fileload\\upload\\";
        File file = new File(filePath + downLoadFile);
        if (file.exists()) {
            response.setContentType("application/force-download");//设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + downLoadFile);

            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            ServletOutputStream out = null;

            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);

                out = response.getOutputStream();

                int read = bis.read(buffer);
                while (read != -1) {
                    out.write(buffer, 0, read);
                    read = bis.read(buffer);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
        }
        return null;
    }

    @RequestMapping(value = "/batch/upload",method = RequestMethod.POST)
    @ResponseBody
    public String batchFileUpload(HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");

        String savePath = "D:\\fileload\\upload\\";
        MultipartFile file = null;
        BufferedOutputStream stream = null;

            for(int i = 0; i < files.size(); i++){
                file = files.get(i);
                if (! file.isEmpty()) {
                    try {
                        byte[] bytes = file.getBytes();
                        String filename = file.getOriginalFilename();
                        String suffixFile = filename.substring(filename.lastIndexOf("."));
                        String saveName = UUID.randomUUID() + suffixFile;
                        stream = new BufferedOutputStream(
                                new FileOutputStream(
                                        new File(savePath + saveName)));
                        stream.write(bytes);
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        stream = null;
                        logger.error("上传文件失败 "+e.getMessage());
                        return "上传文件失败";
                    }
                }else {
                    return "上传文件为空";
                }
            }
            return "上传成功";
    }
}
