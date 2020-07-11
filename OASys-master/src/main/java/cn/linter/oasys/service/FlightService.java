package cn.linter.oasys.service;

import cn.linter.oasys.entity.Flight;
import com.github.pagehelper.PageInfo;

import java.sql.Date;

public interface FlightService {
    Flight getFlightById(int id);

    PageInfo<?> getFlight(int pageNumber, int pageSize, String cityFromName, String cityToName,
                          String flightCompanyName, Date depDate,int ordering);

    int updateFlight(Flight flight);

    int addFlight(Flight flight);

    void deleteFlight(Integer[] ids);
}
