package ulti;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class Ulti_auction {
    private String owner;
    private String name;
    private String description;
    private int reserve_price;
    private int buyout_price;
    private int start_price;
    private Timestamp start_date;
    private boolean status;
    private Timestamp end_date;
    private int current_bid_price;
    
    public void setOwner(String owner){
        this.owner = owner;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setReserve_Price(int reserve_price){
        this.reserve_price = reserve_price;
    }
    
    public void setBuyout_Price(int buyout_price){
        this.buyout_price = buyout_price;
    }
    
    public void setStart_Price(int start_price){
        this.start_price = start_price;
    }
    
    public void setStart_date(Timestamp start_date){
        this.start_date = start_date;
    }
    
    public void setStatus(boolean status){
        this.status = status;
    }
    
    public void setEnd_date(Timestamp end_date){
        this.end_date = end_date;
    }
    
    public void setCurrent_bid_price(int current_bid_price){
        this.current_bid_price = current_bid_price;
    }
    
   
    
    public String getOwner(){
        return this.owner;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public int getReserve_price(){
        return this.reserve_price;
    }

    public int getBuyout_price(){
        return this.buyout_price;
    }

    public int getStart_price(){
        return this.start_price;
    }
    
    public boolean getStatus(){
        return this.status;
    }

    public Timestamp getStart_date(){
        return this.start_date;
    }

    public Timestamp getEnd_date(){
        return this.end_date;
    }

    public int getCurrent_bid_price(){
        return this.current_bid_price;
    }
    
    public String getEnd_date_Striing(){
        //return this.end_date;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = df.format(this.end_date);
       
        return str;
        
    }
}
