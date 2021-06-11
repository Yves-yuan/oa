package cn.linter.oasys.controller;

import cn.linter.oasys.entity.Goods;
import cn.linter.oasys.entity.Response;
import cn.linter.oasys.service.GoodsService;
import cn.linter.oasys.service.GoodsServiceImpl;
import cn.linter.oasys.utils.ExcelUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class GoodsController {
    private final GoodsService goodsService;

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


    @PostMapping("/deleteAllGoods")
    public Response deleteAllGoods() {
        try {
            int result = goodsService.deleteAllGoods();
            return new Response("success", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
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
        ExcelUtils.export(response, goodsService.getGoods(1, Integer.MAX_VALUE, null, null,
                null, null, Integer.MAX_VALUE).getList(), GoodsServiceImpl.arr);
        return "success";
    }

    @GetMapping("/getWarningGoods")
    public Response getWarningGoods(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                    @RequestParam(value = "pageSize", defaultValue = "8") int pageSize,
                                    String tagid,
                                    String manufacturerPartNumber,
                                    String description,
                                    String stockQty,
                                    Integer number
    ) {
        PageInfo<?> pageInfo = goodsService.getWarningGoods(pageNumber, pageSize, tagid, manufacturerPartNumber,
                description, stockQty, number);
        return new Response("success", pageInfo.getTotal(), pageInfo.getList());
    }

    @GetMapping("/getGoods")
    public Response getGoods(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                             @RequestParam(value = "pageSize", defaultValue = "8") int pageSize,
                             String tagid,
                             String manufacturerPartNumber,
                             String descriptionStr,
                             String componentType,
                             Integer number
    ) {
        PageInfo<?> pageInfo = goodsService.getGoods(pageNumber, pageSize, tagid, manufacturerPartNumber,
                descriptionStr, componentType, number);
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
    public Response deleteGoods(@RequestBody String[] ids) {
        goodsService.deleteGoods(ids);
        return new Response("success", "删除成功！");
    }
}
