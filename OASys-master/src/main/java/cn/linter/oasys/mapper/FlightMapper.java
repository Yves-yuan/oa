package cn.linter.oasys.mapper;

import cn.linter.oasys.entity.Flight;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface FlightMapper {
    int insertFlightList(List<Flight> goods);
    int insertFlight(Flight goods);
    List<Flight> selectFlight(HashMap<String,Object> m);
    Flight selectFlightByID(@Param("id") int id);
    int updateFlight(Flight goods);
    void deleteFlight(@Param("ids") Integer[] ids);
}
