package cn.linter.oasys.config;

import cn.linter.oasys.entity.AlpBackupRecord;
import cn.linter.oasys.service.GoodsBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class StaticScheduleTask {
    @Autowired
    GoodsBackupService goodsBackupService;

    @Scheduled(cron = "1 * * * * ?")
    private void backupAlp() {
        AlpBackupRecord alpBackupRecord = new AlpBackupRecord();
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        alpBackupRecord.setBakTs(ts);
        alpBackupRecord.setDescription("auto backup");
        goodsBackupService.insertBackupRecord(alpBackupRecord);
        goodsBackupService.backupGoods(ts);
        goodsBackupService.backupGoodsOrdering(ts);
    }
}
