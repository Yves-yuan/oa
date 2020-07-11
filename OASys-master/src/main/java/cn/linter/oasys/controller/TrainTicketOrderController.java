package cn.linter.oasys.controller;

import cn.linter.oasys.entity.TrainTicketOrder;
import cn.linter.oasys.entity.Response;
import cn.linter.oasys.service.TrainTicketOrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

/**
 * 作者:黄铱雷181203011014
 */
@RestController
@RequestMapping("/api")
public class TrainTicketOrderController {
    private final TrainTicketOrderService flightService;
    @Autowired
    public TrainTicketOrderController(TrainTicketOrderService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/addTrainTicketOrder")
    public Response addTrainTicketOrder(@RequestBody TrainTicketOrder flight) {
        int result = flightService.addTrainTicketOrder(flight);
        if (result == -1) {
            return new Response("error", "订票失败！");
        }
        return new Response("success", "订票成功！");
    }


    @GetMapping("/getTrainTicketOrder")
    public Response getTrainTicketOrder(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                              @RequestParam(value = "pageSize", defaultValue = "8") int pageSize,
                              Integer userId, Integer ticketId
    ) {
        PageInfo<?> pageInfo = flightService.getTrainTicketOrder(pageNumber, pageSize,userId,ticketId);
        return new Response("success", pageInfo.getTotal(), pageInfo.getList());
    }


    @PostMapping("/deleteTrainTicketOrder")
    public Response deleteTrainTicketOrder(@RequestBody Integer[] ids) {
        flightService.deleteTrainTicketOrder(ids);
        return new Response("success", "删除成功！");
    }
}
