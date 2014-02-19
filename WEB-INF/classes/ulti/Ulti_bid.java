package ulti;


public class Ulti_bid {
	private String bider;
	private String bid_auction;
	private String bid_description;
	private int bid_price;
	private boolean status;
	
	public void setBider(String bider){
		this.bider = bider;
	}
	
	public void setBid_auction(String bid_auction){
		this.bid_auction = bid_auction;
	}

	public void setBid_description(String bid_description){
		this.bid_description = bid_description;
	}

	public void setBid_price(int bid_price){
		this.bid_price = bid_price;
	}

	
	public void setStatus(boolean status){
		this.status = status;
	}

	public String getBider(){
		return this.bider;
	}
	
	public String getBid_auction(){
		return this.bid_auction;
	}
	
	public String getBid_description(){
		return this.bid_description;
	}
	
	public int getBid_price(){
		return this.bid_price;
	}
	
	
	public boolean getStatus(){
		return this.status;
	}
}
