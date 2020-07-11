package cn.linter.oasys.service;

import cn.linter.oasys.entity.HospitalDepartment;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletResponse;

public interface HospitalDepartmentService {
    HospitalDepartment getHospitalDepartmentById(int id);

    PageInfo<?> getHospitalDepartment(int pageNumber, int pageSize, String name, String classification
            , String category);
 void export(HttpServletResponse response);
    int updateHospitalDepartment(HospitalDepartment hospitalDepartment);
    int importHospitalDepartment(String filePath) throws Exception;
    int addHospitalDepartment(HospitalDepartment hospitalDepartment);

    void deleteHospitalDepartment(Integer[] ids);
}
