package cn.linter.oasys.service;

import cn.linter.oasys.entity.TrainTicket;
import cn.linter.oasys.mapper.TrainTicketMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;

@Service
public class TrainTicketServiceImpl  implements TrainTicketService{
    private final TrainTicketMapper trainTicketMapper;
    @Autowired
    public TrainTicketServiceImpl(TrainTicketMapper trainTicketMapper) {
        this.trainTicketMapper = trainTicketMapper;
    }
    @Override
    public TrainTicket getTrainTicketById(int id) {
        return trainTicketMapper.selectTrainTicketByID(id);
    }

    @Override
    public PageInfo<?> getTrainTicket(int pageNumber, int pageSize, String fromName, String toName,
                                      String trainNum, Timestamp depTimeFrom, Timestamp depTimeTo) {
        PageHelper.startPage(pageNumber, pageSize);
        HashMap<String,Object> m = new HashMap<>();
        m.put("pageNumber",pageNumber);
        m.put("pageSize",pageSize);
        m.put("fromName",fromName);
        m.put("toName",toName);
        m.put("trainNum",trainNum);
        m.put("depTimeFrom",depTimeFrom);
        m.put("depTimeTo",depTimeTo);
        return new PageInfo<>(trainTicketMapper.selectTrainTicket(m));
    }
    @Override
    public int updateTrainTicket(TrainTicket trainTicket) {
        return trainTicketMapper.updateTrainTicket(trainTicket);
    }

    @Override
    public int addTrainTicket(TrainTicket trainTicket) {
        return trainTicketMapper.insertTrainTicket(trainTicket);
    }

    @Override
    public void deleteTrainTicket(Integer[] ids) {
        trainTicketMapper.deleteTrainTicket(ids);
    }
}
