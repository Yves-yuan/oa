package cn.linter.oasys;

import cn.linter.oasys.entity.AlpBackupRecord;
import cn.linter.oasys.entity.Role;
import cn.linter.oasys.entity.User;
import cn.linter.oasys.service.GoodsBackupService;
import cn.linter.oasys.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OasysApplicationTests {

    private final UserService userService;
    private final GoodsBackupService backupService;

    @Autowired
    OasysApplicationTests(UserService userService,GoodsBackupService backupService) {
        this.userService = userService;
        this.backupService = backupService;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void addUser() {
        Role role = new Role();
        role.setId(1);
        User user = new User();
        user.setUsername("员工一");
        user.setPassword("1234");
        user.setSynopsis("这是员工一");
        user.setPicture("/img/picture/4.jpg");
        user.setRole(role);
        userService.addUser(user);
    }

    @Test
    void backup() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        AlpBackupRecord d = new AlpBackupRecord();
        d.setBakTs(ts);
        d.setDescription("some");
        backupService.insertBackupRecord(d);
        backupService.backupGoods(ts);
        backupService.backupGoodsOrdering(ts);
        backupService.recoverFromGoodsBackup(ts);
        backupService.recoverFromGoodsOrderingBackup(ts);
    }
}
