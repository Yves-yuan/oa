package cn.linter.oasys.service;

import cn.linter.oasys.entity.TrainTicketOrder;
import com.github.pagehelper.PageInfo;

import java.sql.Date;

public interface TrainTicketOrderService {
    TrainTicketOrder getTrainTicketOrderById(int id);

    PageInfo<?> getTrainTicketOrder(int pageNumber, int pageSize, Integer userId, Integer ticketId);


    int addTrainTicketOrder(TrainTicketOrder flight);

    void deleteTrainTicketOrder(Integer[] ids);
}
