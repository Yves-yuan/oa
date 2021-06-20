package cn.linter.oasys.service;

import cn.linter.oasys.entity.AlpBackupRecord;
import cn.linter.oasys.mapper.AlpBakMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
@Component
public class GoodsBackupServiceImpl implements GoodsBackupService{
    @Autowired
    AlpBakMapper alpBakMapper;


    @Override
    public int backupGoods(Timestamp ts) {
        return alpBakMapper.backupGoods(ts);
    }

    @Override
    public int backupGoodsOrdering(Timestamp ts) {
        return alpBakMapper.backupGoodsOrdering(ts);
    }

    @Override
    public int recoverFromGoodsBackup(Timestamp ts) {
        return alpBakMapper.recoverFromGoodsBackup(ts);
    }

    @Override
    public int recoverFromGoodsOrderingBackup(Timestamp ts) {
        return alpBakMapper.recoverFromGoodsOrderingBackup(ts);
    }

    @Override
    public PageInfo<?> getGoodsBackups(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        return new PageInfo<>(alpBakMapper.selectGoodsBackups());
    }

    @Override
    public AlpBackupRecord getGoodsBackupById(int id) {
        return alpBakMapper.selectGoodsBackupsById(id);
    }

    @Override
    public int insertBackupRecord(AlpBackupRecord alpBackupRecord) {
        return alpBakMapper.insertBackupRecord(alpBackupRecord);
    }
}
