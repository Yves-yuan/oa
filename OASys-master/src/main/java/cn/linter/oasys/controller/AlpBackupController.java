package cn.linter.oasys.controller;

import cn.linter.oasys.entity.AlpBackupRecord;
import cn.linter.oasys.entity.Response;
import cn.linter.oasys.entity.User;
import cn.linter.oasys.service.GoodsBackupService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alpBackup")
public class AlpBackupController {
    @Autowired
    GoodsBackupService goodsBackupService;

    @GetMapping("/getBackups")
    public Response getBackups(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                               @RequestParam(value = "pageSize", defaultValue = "8") int pageSize
    ) {
        PageInfo<?> pageInfo = goodsBackupService.getGoodsBackups(pageNumber, pageSize);
        return new Response("success", pageInfo.getTotal(), pageInfo.getList());
    }
    @PostMapping("/recoverFrom")
    public Response recoverFrom(@RequestBody AlpBackupRecord alpBackupRecord) {
        AlpBackupRecord goodsBackupById = goodsBackupService.getGoodsBackupById(alpBackupRecord.getId());
        int goodsNum = goodsBackupService.recoverFromGoodsBackup(goodsBackupById.getBakTs());
        int orderNum = goodsBackupService.recoverFromGoodsOrderingBackup(goodsBackupById.getBakTs());
        return new Response("success", "recovered");
    }
}
