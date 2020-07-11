package cn.linter.oasys.controller;

import cn.linter.oasys.entity.Flight;
import cn.linter.oasys.entity.Response;
import cn.linter.oasys.service.FlightService;
import cn.linter.oasys.service.FlightServiceImpl;
import cn.linter.oasys.utils.ExcelUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

@RestController
@RequestMapping("/api")
public class FlightController {
    private final FlightService flightService;
    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/addFlight")
    public Response addFlight(@RequestBody Flight flight) {
        int result = flightService.addFlight(flight);
        if (result == -1) {
            return new Response("error", "该数据已存在！");
        }
        return new Response("success", "添加成功！");
    }


    @GetMapping("/getFlight")
    public Response getFlight(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                             @RequestParam(value = "pageSize", defaultValue = "8") int pageSize,
                              String cityFromName, String cityToName, String flightCompanyName, Date depDate,
                              @RequestParam(value = "ordering", defaultValue = "0") int ordering
    ) {
        PageInfo<?> pageInfo = flightService.getFlight(pageNumber, pageSize,cityFromName,cityToName,
                flightCompanyName,depDate,ordering);
        return new Response("success", pageInfo.getTotal(), pageInfo.getList());
    }

    @PostMapping("/updateFlight")
    public Response updateFlight(@RequestBody Flight flight) {
        int result = flightService.updateFlight(flight);
        if (result == -1) {
            return new Response("error", "数据已存在！");
        }
        return new Response("success", "更新成功！");
    }

    @PostMapping("/deleteFlight")
    public Response deleteFlight(@RequestBody Integer[] ids) {
        flightService.deleteFlight(ids);
        return new Response("success", "删除成功！");
    }
}
