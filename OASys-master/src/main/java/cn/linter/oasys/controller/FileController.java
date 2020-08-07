package cn.linter.oasys.controller;

import cn.linter.oasys.entity.Response;
import cn.linter.oasys.entity.User;
import cn.linter.oasys.service.FileService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class FileController {
    @Value("${spring.upload-path}")
    private String path;
    private final FileService fileService;
    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/getFiles")
    public Response getFile(@AuthenticationPrincipal User user, @RequestParam("parentId") int parentId, @RequestParam("personal") boolean personal,
                            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        PageInfo<?> pageInfo = fileService.getFiles(user.getId(), parentId, personal, pageNumber, pageSize);
        return new Response("success", pageInfo.getTotal(), pageInfo.getList());
    }

    @GetMapping("/addFolder")
    public Response addFolder(@AuthenticationPrincipal User user, @RequestParam("parentId") int parentId,
                              @RequestParam("folderName") String folderName, @RequestParam("personal") boolean personal) {
        fileService.addFolder(folderName, user.getId(), parentId, personal);
        return new Response("success", "添加成功！");
    }

    @PostMapping("/uploadFile")
    public Response uploadFile(@RequestParam("parentId") int parentId, @RequestParam("personal") boolean personal,
                               @AuthenticationPrincipal User user, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        String rootPath;
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            rootPath = new ApplicationHome(getClass()).getSource().getPath();
        } else {
            rootPath = new ApplicationHome(getClass()).getSource().getParent();
        }
        LOG.debug("upload file root path:" + rootPath);
        String fileName = multipartFile.getOriginalFilename();
        String filePath = path + "/" + fileName;
        long fileSize = multipartFile.getSize();
        Files.write(Paths.get(filePath), multipartFile.getBytes());
        fileService.uploadFile(fileName, fileSize, user, parentId, personal);
        return new Response("success", "上传成功！");
    }

    @PostMapping("/importData")
    public Response importData(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        String rootPath;
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            rootPath = new ApplicationHome(getClass()).getSource().getPath();
        } else {
            rootPath = new ApplicationHome(getClass()).getSource().getParent();
        }
        LOG.debug("upload file root path:" + rootPath);
        String fileName = multipartFile.getOriginalFilename();
        String filePath = path + "/" + fileName;
        Files.write(Paths.get(filePath), multipartFile.getBytes());
        try {
            int res = fileService.importData(filePath);
            if (res != -1){
                return new Response("success", "导入成功！");
            }else{
                return new Response("failure", "导入失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("failure", "导入失败"+e.getMessage());
        }
    }

    @GetMapping("/renameFile")
    public Response renameFile(@RequestParam("id") int id, @RequestParam("newName") String newName) {
        fileService.renameFile(id, newName);
        return new Response("success", "重命名成功！");
    }

    @PostMapping("/deleteFile")
    public Response deleteFile(@RequestBody Integer[] ids) {
        fileService.deleteFile(ids);
        return new Response("success", "删除成功！");
    }
}
