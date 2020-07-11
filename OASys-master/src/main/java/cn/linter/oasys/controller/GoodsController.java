package cn.linter.oasys.controller;

import cn.linter.oasys.entity.Goods;
import cn.linter.oasys.entity.Response;
import cn.linter.oasys.entity.User;
import cn.linter.oasys.service.GoodsService;
import cn.linter.oasys.service.GoodsServiceImpl;
import cn.linter.oasys.utils.ExcelUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class GoodsController {
    private final GoodsService goodsService;
    @Value("${spring.upload-path}")
    private String path;

    @Autowired
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @PostMapping("/addGoods")
    public Response addGoods(@RequestBody Goods goods) {
        int result = goodsService.addGoods(goods);
        if (result == -1) {
            return new Response("error", "该数据已存在！");
        }
        return new Response("success", "添加成功！");
    }

    @PostMapping("/importGoods")
    public Response importGoods(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        String filePath = path + "/" + fileName;
        Files.write(Paths.get(filePath), multipartFile.getBytes());
        try {
            int res = goodsService.importGoods(filePath);
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
    @PostMapping("/deleteAllGoods")
    public Response deleteAllGoods() {
        try {
            int result = goodsService.deleteAllGoods();
            return new Response("success", "删除成功");
        } catch (Exception e) {
            return new Response("error", "删除失败！");
        }

    }
    @PostMapping(value = "/exportGoods")
    public String exportGoods(
            String tagid,
            String manufacturerPartNumber,
            String description,
            String stockQty, HttpServletResponse response
    ) {
        //调用Excel导出工具类
        //客户需要所有数据导出
//        ExcelUtils.export(response, goodsService.getGoods(1, Integer.MAX_VALUE, null, null,
//                null, null).getList(), GoodsServiceImpl.arr);
        try {
            goodsService.export(response);
        } catch (Exception e) {
            return "error";
        }
        return"success";
    }

    @GetMapping("/getGoods")
    public Response getGoods(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                             @RequestParam(value = "pageSize", defaultValue = "8") int pageSize,
                             String tagid,
                             String manufacturerPartNumber,
                             String description,
                             String stockQty
    ) {
        PageInfo<?> pageInfo = goodsService.getGoods(pageNumber, pageSize, tagid, manufacturerPartNumber,
                description, stockQty);
        return new Response("success", pageInfo.getTotal(), pageInfo.getList());
    }

    @PostMapping("/updateGoods")
    public Response updateGoods(@RequestBody Goods goods) {
        int result = goodsService.updateGoods(goods);
        if (result == -1) {
            return new Response("error", "数据已存在！");
        }
        return new Response("success", "更新成功！");
    }

    @PostMapping("/deleteGoods")
    public Response deleteGoods(@RequestBody Integer[] ids) {
        goodsService.deleteGoods(ids);
        return new Response("success", "删除成功！");
    }
}
