package cn.linter.oasys.controller;

import cn.linter.oasys.entity.TrainTicket;
import cn.linter.oasys.entity.Response;
import cn.linter.oasys.service.TrainTicketService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 作者:黄铱雷181203011014
 */
@RestController
@RequestMapping("/api")
public class TrainTicketController {
    private final TrainTicketService trainTicketService;
    @Autowired
    public TrainTicketController(TrainTicketService trainTicketService) {
        this.trainTicketService = trainTicketService;
    }

    @PostMapping("/addTrainTicket")
    public Response addTrainTicket(@RequestBody TrainTicket trainTicket) {
        int result = trainTicketService.addTrainTicket(trainTicket);
        if (result == -1) {
            return new Response("error", "该数据已存在！");
        }
        return new Response("success", "添加成功！");
    }


    @GetMapping("/getTrainTicket")
    public Response getTrainTicket(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                              @RequestParam(value = "pageSize", defaultValue = "8") int pageSize,
                              String fromName, String toName, String trainNum, Timestamp depTimeFrom,Timestamp depTimeTo
    ) {
        PageInfo<?> pageInfo = trainTicketService.getTrainTicket(pageNumber, pageSize,fromName,toName,
                trainNum,depTimeFrom,depTimeTo);
        return new Response("success", pageInfo.getTotal(), pageInfo.getList());
    }

    @PostMapping("/updateTrainTicket")
    public Response updateTrainTicket(@RequestBody TrainTicket trainTicket) {
        int result = trainTicketService.updateTrainTicket(trainTicket);
        if (result == -1) {
            return new Response("error", "数据已存在！");
        }
        return new Response("success", "更新成功！");
    }

    @PostMapping("/deleteTrainTicket")
    public Response deleteTrainTicket(@RequestBody Integer[] ids) {
        trainTicketService.deleteTrainTicket(ids);
        return new Response("success", "删除成功！");
    }
}
