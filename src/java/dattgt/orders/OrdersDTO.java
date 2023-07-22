package dattgt.orders;

import java.sql.Date;

public class OrdersDTO {

    private String id;
    private Date dateBuy;
    private String username;

    public OrdersDTO() {
    }

    public OrdersDTO(String id, Date dateBuy, String username) {
        this.id = id;
        this.dateBuy = dateBuy;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateBuy() {
        return dateBuy;
    }

    public void setDateBuy(Date dateBuy) {
        this.dateBuy = dateBuy;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
