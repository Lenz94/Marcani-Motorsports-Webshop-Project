package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Category {
	
	@Id
	private int categoryId;
	private String categoryName;
	private String categoryList;
	
	public Category(int categoryId, String categoryName, String categoryList) {
		
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.setCategoryList(categoryList);
		
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(String categoryList) {
		this.categoryList = categoryList;
	}


}
