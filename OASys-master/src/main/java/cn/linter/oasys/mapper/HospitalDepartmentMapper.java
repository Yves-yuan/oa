package cn.linter.oasys.mapper;

import cn.linter.oasys.entity.HospitalDepartment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface HospitalDepartmentMapper {
    int insertHospitalDepartmentList(List<Map<String, Object>> hospitalDepartment);

    int insertHospitalDepartment(HospitalDepartment hospitalDepartment);

    List<HospitalDepartment> selectHospitalDepartment(HashMap<String, Object> m);

    HospitalDepartment selectHospitalDepartmentByID(@Param("id") int id);

    int updateHospitalDepartment(HospitalDepartment hospitalDepartment);

    void deleteHospitalDepartment(@Param("ids") Integer[] ids);
}
