package cn.linter.oasys.mapper;

import cn.linter.oasys.entity.TrainTicketOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface TrainTicketOrderMapper {
    int insertTrainTicketOrderList(List<TrainTicketOrder> goods);
    int insertTrainTicketOrder(TrainTicketOrder goods);
    List<TrainTicketOrder> selectTrainTicketOrder(HashMap<String,Object> m);
    TrainTicketOrder selectTrainTicketOrderByID(@Param("id") int id);
    void deleteTrainTicketOrder(@Param("ids") Integer[] ids);
}
