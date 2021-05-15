package cn.linter.oasys.service;

import cn.linter.oasys.entity.GoodsOrdering;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

public interface GoodsOrderingService {
    GoodsOrdering getGoodsOrderingById(int id);

    PageInfo<?> getGoodsOrdering(int pageNumber, int pageSize, Timestamp begin, Timestamp end,String goodsPartNumber,
                                 String project,String customPo);
    PageInfo<?> exportGoodsOrdering(int pageNumber, int pageSize, Timestamp begin, Timestamp end,String goodsPartNumber,
                                 String project,String customPo);
    Boolean importData(MultipartFile multipartFile) throws Exception;
    Boolean importDataSimple(MultipartFile multipartFile) throws Exception;
    int updateGoodsOrdering(GoodsOrdering goods);

    Boolean addGoodsOrdering(GoodsOrdering goods);

    void deleteGoodsOrdering(Integer[] ids);

    int deleteAllGoodsOrdering();
}
