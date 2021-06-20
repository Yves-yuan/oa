package cn.linter.oasys.service;

import cn.linter.oasys.entity.AlpBackupRecord;
import cn.linter.oasys.entity.GoodsOrdering;
import com.github.pagehelper.PageInfo;

import java.sql.Timestamp;

public interface GoodsBackupService {
    int backupGoods(Timestamp bak_ts);
    int backupGoodsOrdering(Timestamp bak_ts);
    int recoverFromGoodsBackup(Timestamp bak_ts);
    int recoverFromGoodsOrderingBackup(Timestamp bak_ts);
    PageInfo<?> getGoodsBackups(int pageNumber, int pageSize);
    AlpBackupRecord getGoodsBackupById(int id);
    int insertBackupRecord(AlpBackupRecord alpBackupRecord);

}
