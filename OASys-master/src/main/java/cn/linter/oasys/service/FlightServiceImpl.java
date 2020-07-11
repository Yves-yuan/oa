package cn.linter.oasys.service;

import cn.linter.oasys.entity.Flight;
import cn.linter.oasys.mapper.FlightMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;

@Service
public class FlightServiceImpl implements FlightService {
    private final FlightMapper flightMapper;
    @Autowired
    public FlightServiceImpl(FlightMapper flightMapper) {
        this.flightMapper = flightMapper;
    }
    @Override
    public Flight getFlightById(int id) {
        return flightMapper.selectFlightByID(id);
    }

    @Override
    public PageInfo<?> getFlight(int pageNumber, int pageSize, String cityFromName, String cityToName,
                                 String flightCompanyName, Date depDate,int ordering) {
        PageHelper.startPage(pageNumber, pageSize);
        HashMap<String,Object> m = new HashMap<>();
        m.put("pageNumber",pageNumber);
        m.put("pageSize",pageSize);
        m.put("cityFromName",cityFromName);
        m.put("cityToName",cityToName);
        m.put("flightCompanyName",flightCompanyName);
        m.put("depDate",depDate);
        m.put("ordering",ordering);
        return new PageInfo<>(flightMapper.selectFlight(m));
    }

    @Override
    public int updateFlight(Flight flight) {
        return flightMapper.updateFlight(flight);
    }

    @Override
    public int addFlight(Flight flight) {
        return flightMapper.insertFlight(flight);
    }

    @Override
    public void deleteFlight(Integer[] ids) {
        flightMapper.deleteFlight(ids);
    }
}
