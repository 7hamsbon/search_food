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


    @GetMapping("/pic")
    @Description("获取图片")
    public void getPic(@RequestParam("filePath") String filePath, HttpServletResponse response) throws IOException {
        OutputStream os = response.getOutputStream();
        log.debug("filePath-->>"+filePath);
        OpResult<byte[]> r = fileService.getFile(filePath);
        if(r.isSuccess()){
            os.write(r.getData());
            os.flush();
        }
    }


}
