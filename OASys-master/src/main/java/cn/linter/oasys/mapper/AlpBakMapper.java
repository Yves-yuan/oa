package cn.linter.oasys.mapper;

import cn.linter.oasys.entity.AlpBackupRecord;
import cn.linter.oasys.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface AlpBakMapper {
    int backupGoods(@Param("bak_ts") Timestamp bakTs);
    int backupGoodsOrdering(@Param("bak_ts") Timestamp bakTs);
    int recoverFromGoodsBackup(@Param("bak_ts") Timestamp bakTs);
    int recoverFromGoodsOrderingBackup(@Param("bak_ts") Timestamp bakTs);
    List<AlpBackupRecord> selectGoodsBackups();
    AlpBackupRecord selectGoodsBackupsById(@Param("id") int id);
    int insertBackupRecord(AlpBackupRecord alpBackupRecord);
}
