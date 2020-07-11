package cn.linter.oasys.service;

import cn.linter.oasys.entity.Flight;
import cn.linter.oasys.entity.TrainTicket;
import com.github.pagehelper.PageInfo;

import java.sql.Date;
import java.sql.Timestamp;

public interface TrainTicketService {
    TrainTicket getTrainTicketById(int id);

    PageInfo<?> getTrainTicket(int pageNumber, int pageSize, String fromName, String toName,
                               String trainNum, Timestamp depTimeFrom, Timestamp depTimeTo);

    int updateTrainTicket(TrainTicket trainTicket);
    int addTrainTicket(TrainTicket trainTicket);

    void deleteTrainTicket(Integer[] ids);
}
