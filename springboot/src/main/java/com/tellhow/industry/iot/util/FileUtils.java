package com.tellhow.industry.iot.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传工具包
 */
public class FileUtils {

    /**
     *
     * @param file 文件
     * @param path 文件存放路径
     * @param id 人员id
     * @return
     */
    public static boolean uploadFace(MultipartFile file, String path, String id){


        // 生成新的文件名
        //String realPath = path + "/" + FileNameUtils.getFileName(fileName);

        //使用原文件名
        String realPath = path + "/" + id+".jpg";

        File dest = new File(realPath);

        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }

        try {
            //保存文件
            file.transferTo(dest);
            return true;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }


}
