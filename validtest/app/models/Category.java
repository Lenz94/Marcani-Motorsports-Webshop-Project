package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Category {
	
	@Id
	@GeneratedValue
	private int id;
    private String name;
    
    
    public Category(){
    	
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public Category(String name){
    	this.name = name;
    }
    public Category(int id, String name){
    	this.id = id;
    	this.name = name;
    }
 
    public String getName() {
        return name;
    }
 

    public void setName(String name) {
        this.name = name;
    }
 
    public int getid() {
        return id;
    }
 
    public void setid(int id) {
        this.id = id;
    }
    
   
}
