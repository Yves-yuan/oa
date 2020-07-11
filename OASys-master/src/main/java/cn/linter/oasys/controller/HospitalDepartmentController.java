package cn.linter.oasys.controller;

import cn.linter.oasys.entity.HospitalDepartment;
import cn.linter.oasys.entity.Response;
import cn.linter.oasys.entity.User;
import cn.linter.oasys.service.HospitalDepartmentService;
import cn.linter.oasys.service.HospitalDepartmentServiceImpl;
import cn.linter.oasys.utils.ExcelUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class HospitalDepartmentController {
    private final HospitalDepartmentService hospitalDepartmentService;

    @Autowired
    public HospitalDepartmentController(HospitalDepartmentService hospitalDepartmentService) {
        this.hospitalDepartmentService = hospitalDepartmentService;
    }

    @PostMapping("/addHospitalDepartment")
    public Response addHospitalDepartment(@RequestBody HospitalDepartment hospitalDepartment) {
        int result = hospitalDepartmentService.addHospitalDepartment(hospitalDepartment);
        if (result == -1) {
            return new Response("error", "该数据已存在！");
        }
        return new Response("success", "添加成功！");
    }

    @PostMapping(value = "/exportHospitalDepartment")
    public String exportHospitalDepartment(
            HttpServletResponse response
    ) {
        //调用Excel导出工具类
        //客户需要所有数据导出
        try {
            hospitalDepartmentService.export(response);
        } catch (Exception e) {
            return "error";
        }
        return"success";
    }

    @PostMapping("/importHospitalDepartment")
    public Response importHospitalDepartment(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        String rootPath;
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            rootPath = new ApplicationHome(getClass()).getSource().getPath();
        } else {
            rootPath = new ApplicationHome(getClass()).getSource().getParent();
        }
        String fileName = multipartFile.getOriginalFilename();
        String filePath = rootPath + "/static/file/" + fileName;
        Files.write(Paths.get(filePath), multipartFile.getBytes());
        try {
            int res = hospitalDepartmentService.importHospitalDepartment(filePath);
            if (res != -1) {
                return new Response("success", "导入成功！");
            } else {
                return new Response("failure", "导入失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("failure", "导入失败" + e.getMessage());
        }
    }

    @GetMapping("/getHospitalDepartment")
    public Response getHospitalDepartment(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                          @RequestParam(value = "pageSize", defaultValue = "8") int pageSize,
                                          String name, String classification
            , String category
    ) {
        PageInfo<?> pageInfo = hospitalDepartmentService.getHospitalDepartment(pageNumber, pageSize, name, classification,
                category);
        return new Response("success", pageInfo.getTotal(), pageInfo.getList());
    }

    @PostMapping("/updateHospitalDepartment")
    public Response updateHospitalDepartment(@RequestBody HospitalDepartment hospitalDepartment) {
        int result = hospitalDepartmentService.updateHospitalDepartment(hospitalDepartment);
        if (result == -1) {
            return new Response("error", "数据已存在！");
        }
        return new Response("success", "更新成功！");
    }

    @PostMapping("/deleteHospitalDepartment")
    public Response deleteHospitalDepartment(@RequestBody Integer[] ids) {
        hospitalDepartmentService.deleteHospitalDepartment(ids);
        return new Response("success", "删除成功！");
    }
}
