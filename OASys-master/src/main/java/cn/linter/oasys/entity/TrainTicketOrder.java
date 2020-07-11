package cn.linter.oasys.entity;

import org.apache.ibatis.type.Alias;

@Alias("TrainTicketOrder")
public class TrainTicketOrder {
    private int id;
    private User user;
    private TrainTicket trainTicket;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TrainTicket getTrainTicket() {
        return trainTicket;
    }

    public void setTrainTicket(TrainTicket trainTicket) {
        this.trainTicket = trainTicket;
    }
}
