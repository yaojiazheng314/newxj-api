package com.perform.ftp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.perform.base.ApiController;
import com.perform.config.IConstant;
import com.perform.ftp.util.FtpFileUtil;
import com.perform.pojo.FeedBackReport;
import com.perform.utils.JSONResult;
import com.perform.v2.pojo.FeedBackReportDepartmentReason;
import com.perform.v2.service.FeedBackReportV2Service;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;


@Controller

public class FtpFileUploadController extends ApiController {

    //ftp处理文件上传
    @PostMapping(value = "/feedBack/uploadPic")
    public @ResponseBody
    JSONResult upload(@RequestParam List<MultipartFile> files
                     ) throws IOException {

        //FTP 基础服务地 址
        String baseUrl = IConstant.FTPURL;
        List<String> urlPicList=new ArrayList<>();
        for (MultipartFile file : files) {
            String filePath ="";
            String fileName = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
             filePath += baseUrl;


            Boolean flag = FtpFileUtil.uploadFile(fileName, inputStream);
            if (flag == true) {
                System.out.println("ftp上传成功！");
                filePath += fileName;
            }

            urlPicList.add(filePath);
        }
        JSONObject data = new JSONObject();
        data.put("urlPicList", urlPicList);
        return JSONResult.ok(data);
    }










}