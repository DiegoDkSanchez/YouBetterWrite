package sv.dk.com.youbetterwrite.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Story implements Serializable{

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("id_category")
	private int idCategory;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("state")
	private int state;

	@SerializedName("category")
	private Category category;

	@SerializedName("deleted_at")
	private Object deletedAt;

	@SerializedName("sections")
	private ArrayList<SectionsItem> sections;

	@SerializedName("url")
	private String url;


	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setIdCategory(int idCategory){
		this.idCategory = idCategory;
	}

	public int getIdCategory(){
		return idCategory;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setState(int state){
		this.state = state;
	}

	public int getState(){
		return state;
	}

	public void setCategory(Category category){
		this.category = category;
	}

	public Category getCategory(){
		return category;
	}

	public void setDeletedAt(Object deletedAt){
		this.deletedAt = deletedAt;
	}

	public Object getDeletedAt(){
		return deletedAt;
	}

	public void setSections(ArrayList<SectionsItem> sections){
		this.sections = sections;
	}

	public ArrayList<SectionsItem> getSections(){
		return sections;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
 	public String toString(){
		return 
			"Story{" +
			"updated_at = '" + updatedAt + '\'' + 
			",id_category = '" + idCategory + '\'' + 
			",name = '" + name + '\'' +
			",url = '" + url + '\'' +
			",created_at = '" + createdAt + '\'' +
			",id = '" + id + '\'' + 
			",state = '" + state + '\'' + 
			",category = '" + category + '\'' + 
			",deleted_at = '" + deletedAt + '\'' + 
			",sections = '" + sections + '\'' + 
			"}";
		}
}