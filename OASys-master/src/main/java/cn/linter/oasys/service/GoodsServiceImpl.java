package cn.linter.oasys.service;

import cn.linter.oasys.entity.Goods;
import cn.linter.oasys.mapper.GoodsMapper;
import cn.linter.oasys.utils.ExcelUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
public class GoodsServiceImpl implements GoodsService {
    private final GoodsMapper goodsMapper;

    @Autowired
    public GoodsServiceImpl(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    @Override
    public Goods getGoodsById(int id) {
        return goodsMapper.selectGoodsByID(id);
    }

    public static String[] arr = new String[]{"id", "tagid", "component type", "sub-type", "manufacturer",
            "manufacturer part number", "description", "stock qty", "annual stock", "auto replenish rate",
            "lead time"};

    @Override
    public PageInfo<?> getGoods(int pageNumber, int pageSize, String tagid, String manufacturerPartNumber, String description, String stockQty) {
        PageHelper.startPage(pageNumber, pageSize);
        HashMap<String, Object> m = new HashMap<>();
        m.put("pageNumber", pageNumber);
        m.put("pageSize", pageSize);
        m.put("tagid", tagid);
        m.put("manufacturerPartNumber", manufacturerPartNumber);
        m.put("description", description);
        m.put("stockQty", stockQty);
        return new PageInfo<>(goodsMapper.selectGoods(m));
    }
    public void export(HttpServletResponse response) throws Exception {
        ExcelUtils.export(response, getGoods(1, Integer.MAX_VALUE, null, null,
                null,null).getList(), arr);

    }

    public int importGoods(String filePath) throws Exception {
        List<Map<String, Object>> list = ExcelUtils.importExcel(filePath, arr, (o, columnName) -> {
            if (columnName.equals("price")) {
                String cs = (String) o;
                try {
                    return new BigDecimal(cs);
                } catch (Exception ex) {
                    return null;
                }
            }
            return o;
        });
        return goodsMapper.insertGoodsList(list);
    }

    @Override
    public int updateGoods(Goods goods) {
        return goodsMapper.updateGoods(goods);
    }

    @Override
    public int addGoods(Goods goods) {
        return goodsMapper.insertGoods(goods);
    }
    @Override
    public int deleteAllGoods() {
        return goodsMapper.deleteAllGoods();
    }

    @Override
    public void deleteGoods(Integer[] ids) {
        goodsMapper.deleteGoods(ids);
    }
}
