package com.ham.web;

import com.ham.service.FileService;
import com.ham.vo.OpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by hamsbon on 2017/2/19.
 */
@Controller
public class FileUploadController {

    @Autowired
    FileService fileService;

    private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

    public static final String ROOT = "upload-dir";

//    private final ResourceLoader resourceLoader;

//    @Autowired
//    public FileUploadController(ResourceLoader resourceLoader) {
//        this.resourceLoader = resourceLoader;
//    }

//    @RequestMapping(method = RequestMethod.GET, value = "/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<?> getFile(@PathVariable String filename) {
//
//        try {
//            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString()));
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!file.isEmpty()) {
            try {
                Files.copy(file.getInputStream(), Paths.get(ROOT, file.getOriginalFilename()));
                System.out.println(Paths.get(ROOT, file.getOriginalFilename()).toString());
                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded " + file.getOriginalFilename() + "!");
            } catch (IOException |RuntimeException e) {
                System.out.println("文件上传失败");
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("message", "Failued to upload " + file.getOriginalFilename() + " => " + e.getMessage());
            }
        } else {
            System.out.println("文件为空");
            redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getOriginalFilename() + " because it was empty");
        }

        return "redirect:/"+file.getOriginalFilename();
    }


    @RequestMapping("/upload")
    @ResponseBody
    public OpResult<String> upload(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        if(file == null){
            OpResult<String> result = new OpResult<>();
            result.setSuccess(false);
            result.setOpMsg("上传文件为空");
            return result;
        }
        return fileService.upload("test",file);
    }


    /*
    *               response.reset();
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/x-download");
                    response.setHeader("Accept-Ranges", "bytes");
                    response.setHeader("Content-Length", String.valueOf(fSize));
                    //response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(filename.getBytes(guessCharset), "utf-8") + "\"");
                    in = new FileInputStream(file.getPath());

                    long pos = 0;
                    if (null != request.getHeader("Range")) {
                        // 断点续传
                        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                        try {
                            pos = Long.parseLong(request.getHeader("Range").replaceAll("bytes=", "").replaceAll("-", ""));
                        } catch (NumberFormatException e) {
                            pos = 0;
                        }
                    }
                    out = response.getOutputStream();
                    String contentRange = new StringBuffer("bytes ").append(pos + "").append("-").append((fSize - 1) + "").append("/").append(fSize + "").toString();
                    response.setHeader("Content-Range", contentRange);
                    in.skip(pos);
                    byte[] buffer = new byte[1024 * 10];
                    int length = 0;
                    while ((length = in.read(buffer, 0, buffer.length)) != -1) {
                        out.write(buffer, 0, length);
                        // Thread.sleep(100);
                    }
    *
    * */


    @GetMapping("/pic")
    @Description("获取图片")
    public void getPic(@RequestParam("filePath") String filePath,HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("filePath-->>"+filePath);
        long pos = 0;
        if (null != request.getHeader("Range")) {
            // 断点续传
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            try {
                pos = Long.parseLong(request.getHeader("Range").replaceAll("bytes=", "").replaceAll("-", ""));
            } catch (NumberFormatException e) {
                pos = 0;
            }
        }
        OpResult<byte[]> r = fileService.getFile(filePath,pos);
        int fSize = r.getData().length;
//        response.reset();
        response.setStatus(206);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/x-download");
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-Length", String.valueOf(fSize));
        //response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(filename.getBytes(guessCharset), "utf-8") + "\"");

        OutputStream out = response.getOutputStream();
        String contentRange = new StringBuffer("bytes ").append(pos + "").append("-").append((fSize - 1) + "").append("/").append(fSize + "").toString();
        response.setHeader("Content-Range", contentRange);
        out.write(r.getData());
//        byte[] buffer = new byte[1024 * 10];
//        int length = 0;
//        while ((length = in.read(buffer, 0, buffer.length)) != -1) {
//            out.write(buffer, 0, length);
//            // Thread.sleep(100);
//        }

//        response.setBufferSize(response.getBufferSize()+1024*1024*10);
//        Integer fileSize = r.getData().length;
//        response.setHeader("Content-Length",fileSize.toString() );
//        response.setHeader("Accept-Ranges", "bytes");
//        response.setHeader("Content-Range", "bytes 0-"+(fileSize-1)+"/"+fileSize);
//        response.setHeader("Cache-control", "no-store");
//        response.setHeader("Pragma", "");
//        if(r.isSuccess()){
//            os.write(r.getData());
//            os.flush();
//        }
    }


}
