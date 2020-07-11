package cn.linter.oasys.mapper;

import cn.linter.oasys.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsMapper {
    int insertGoodsList(List<Map<String,Object>> goods);
    int insertGoods(Goods goods);
    List<Goods> selectGoods(HashMap<String,Object> m);
    Goods selectGoodsByID(@Param("id") int id);
    int updateGoods(Goods goods);
    void deleteGoods(@Param("ids") Integer[] ids);
    int deleteAllGoods();
}
