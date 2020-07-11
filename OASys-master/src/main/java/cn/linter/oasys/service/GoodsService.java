package cn.linter.oasys.service;

import cn.linter.oasys.entity.Goods;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletResponse;

public interface GoodsService {
    Goods getGoodsById(int id);

    PageInfo<?> getGoods(int pageNumber, int pageSize, String tagid, String manufacturerPartNumber
            , String description, String stockQty);
    int importGoods(String filePath) throws Exception;
    void export(HttpServletResponse response) throws Exception;
    int updateGoods(Goods goods);

    int addGoods(Goods goods);
    int deleteAllGoods();
    void deleteGoods(Integer[] ids);
}
