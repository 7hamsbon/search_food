package com.ham.service;

import com.ham.vo.OpResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by hamsbon on 2017/2/18.
 */
public interface FileService {

    /**
     * 上传文件返回文件url
     */
    OpResult<String> upload(String basePath, MultipartFile uploadFile);

    OpResult<byte[]> getFile(String filePath);
}
