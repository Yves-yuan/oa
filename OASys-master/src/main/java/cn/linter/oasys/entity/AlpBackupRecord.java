package cn.linter.oasys.entity;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;

@Alias("AlpBackupRecord")
public class AlpBackupRecord implements Serializable {
    private int id;
    private Timestamp bakTs;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getBakTs() {
        return bakTs;
    }

    public void setBakTs(Timestamp bakTs) {
        this.bakTs = bakTs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
