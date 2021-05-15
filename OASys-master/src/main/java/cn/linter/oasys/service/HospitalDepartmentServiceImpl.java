package cn.linter.oasys.service;

import cn.linter.oasys.entity.HospitalDepartment;
import cn.linter.oasys.mapper.HospitalDepartmentMapper;
import cn.linter.oasys.utils.ExcelUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class HospitalDepartmentServiceImpl implements HospitalDepartmentService{
    private final HospitalDepartmentMapper hospitalDepartmentMapper;
    public  String[] arr = new String[]{"id","name", "classification","category"};
    @Autowired
    public HospitalDepartmentServiceImpl(HospitalDepartmentMapper hospitalDepartmentMapper) {
        this.hospitalDepartmentMapper = hospitalDepartmentMapper;
    }
    @Override
    public HospitalDepartment getHospitalDepartmentById(int id) {
        return hospitalDepartmentMapper.selectHospitalDepartmentByID(id);
    }

    @Override
    public PageInfo<?> getHospitalDepartment(int pageNumber, int pageSize, String name, String classification
            , String category) {
        PageHelper.startPage(pageNumber, pageSize);
        HashMap<String,Object> m = new HashMap<>();
        m.put("pageNumber",pageNumber);
        m.put("pageSize",pageSize);
        m.put("name",name);
        m.put("classification",classification);
        m.put("category",category);
        return new PageInfo<>(hospitalDepartmentMapper.selectHospitalDepartment(m));
    }

    public int importHospitalDepartment(String filePath) throws Exception {
        List<Map<String,Object>> list = ExcelUtils.importExcel(filePath,arr);
        return hospitalDepartmentMapper.insertHospitalDepartmentList(list);
    }

    @Override
    public int updateHospitalDepartment(HospitalDepartment hospitalDepartment) {
        return hospitalDepartmentMapper.updateHospitalDepartment(hospitalDepartment);
    }
    @Override
    public void export(HttpServletResponse response){
        ExcelUtils.export(response, getHospitalDepartment(1, Integer.MAX_VALUE, null, null,
                null).getList(), arr);
    }
    @Override
    public int addHospitalDepartment(HospitalDepartment hospitalDepartment) {
        return hospitalDepartmentMapper.insertHospitalDepartment(hospitalDepartment);
    }

    @Override
    public void deleteHospitalDepartment(Integer[] ids) {
        hospitalDepartmentMapper.deleteHospitalDepartment(ids);
    }
}
