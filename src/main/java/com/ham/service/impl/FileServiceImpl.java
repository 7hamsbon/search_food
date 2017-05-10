package com.ham.service.impl;

import com.ham.service.FileService;
import com.ham.util.FileUtils;
import com.ham.util.PathUtils;
import com.ham.util.TimeUtils;
import com.ham.vo.OpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * Created by hamsbon on 2017/2/18.
 */
@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public OpResult<String> upload(String basePath, MultipartFile uploadFile) {
        OpResult<String> result = new OpResult<String>(false,null,"文件上传失败");

        String storePath = PathUtils.getStaticResourcePath(basePath) + TimeUtils.getNowDir(TimeUtils.STORE_DIR);
        logger.debug("storePath--->>>"+storePath);
        String fileName = UUID.randomUUID().toString()+ FileUtils.getSubfix(uploadFile.getOriginalFilename());
        File dir = new File(storePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        try {
            uploadFile.transferTo(new File(storePath,fileName));
            result.setSuccess(true);
            result.setOpMsg("/" + basePath + TimeUtils.getNowDir(TimeUtils.VISIT_DIR) + fileName);
        } catch (IOException e) {
            logger.error("文件上传失败",e);
        }
        return result;
    }

    @Override
    public OpResult<byte[]> getFile(String filePath,long pos) {
        OpResult<byte[]> opResult = new OpResult<>(false,null,"获取图片失败");
        File sourceFile = new File(PathUtils.getStaticResourcePath(filePath));
        try {
            InputStream source = new FileInputStream(sourceFile);
            source.skip(pos);
            byte[] buffer = new byte[(int) sourceFile.length()];
            source.read(buffer);
            opResult.setSuccess(true);
            opResult.setData(buffer);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return opResult;
    }
}
