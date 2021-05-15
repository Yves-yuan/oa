package cn.linter.oasys.mapper;

import cn.linter.oasys.entity.GoodsOrdering;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsOrderingMapper {
    int updateGoodsOrdering(List<Map<String, Object>> goods);
    int insertGoodsOrderingList(List<Map<String,Object>> goods);
    int insertGoodsOrdering(GoodsOrdering goods);

    List<GoodsOrdering> selectGoodsOrdering(HashMap<String, Object> m);
    List<GoodsOrdering> exportGoodsOrdering(HashMap<String, Object> m);

    GoodsOrdering selectGoodsOrderingById(@Param("id") int id);

    int updateGoodsOrdering(GoodsOrdering goods);

    void deleteGoodsOrdering(@Param("ids") Integer[] ids);

    int deleteAllGoodsOrdering();
}
