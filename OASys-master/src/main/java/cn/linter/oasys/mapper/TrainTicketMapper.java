package cn.linter.oasys.mapper;

import cn.linter.oasys.entity.TrainTicket;
import cn.linter.oasys.entity.TrainTicket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface TrainTicketMapper {
    int insertTrainTicket(List<TrainTicket> goods);
    int insertTrainTicket(TrainTicket goods);
    List<TrainTicket> selectTrainTicket(HashMap<String,Object> m);
    TrainTicket selectTrainTicketByID(@Param("id") int id);
    int updateTrainTicket(TrainTicket goods);
    void deleteTrainTicket(@Param("ids") Integer[] ids);
}
