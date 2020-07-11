package cn.linter.oasys.service;

import cn.linter.oasys.entity.TrainTicketOrder;
import cn.linter.oasys.mapper.TrainTicketOrderMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;

@Service
public class TrainTicketOrderServiceImpl implements TrainTicketOrderService {
    private final TrainTicketOrderMapper flightMapper;
    @Autowired
    public TrainTicketOrderServiceImpl(TrainTicketOrderMapper flightMapper) {
        this.flightMapper = flightMapper;
    }
    @Override
    public TrainTicketOrder getTrainTicketOrderById(int id) {
        return flightMapper.selectTrainTicketOrderByID(id);
    }

    @Override
    public PageInfo<?> getTrainTicketOrder(int pageNumber, int pageSize, Integer userId, Integer ticketId) {
        PageHelper.startPage(pageNumber, pageSize);
        HashMap<String,Object> m = new HashMap<>();
        m.put("pageNumber",pageNumber);
        m.put("pageSize",pageSize);
        m.put("userId",userId);
        m.put("ticketId",ticketId);
        return new PageInfo<>(flightMapper.selectTrainTicketOrder(m));
    }


    @Override
    public int addTrainTicketOrder(TrainTicketOrder flight) {
        return flightMapper.insertTrainTicketOrder(flight);
    }

    @Override
    public void deleteTrainTicketOrder(Integer[] ids) {
        flightMapper.deleteTrainTicketOrder(ids);
    }
}
