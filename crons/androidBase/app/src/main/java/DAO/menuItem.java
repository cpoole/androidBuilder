package DAO;

import com.google.gson.annotations.Expose;

public class menuItem {
    @Expose private String type;
    @Expose private String title;
    @Expose private String description;
    @Expose private float price;
    private long id;
    public menuItem(){

    }
    public menuItem(String type, String title, String description, String price){
        this.type = type;
        this.title = title;
        this.description = description;
        this.price = Float.parseFloat(price);
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }

}
