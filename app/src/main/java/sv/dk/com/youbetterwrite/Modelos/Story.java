package sv.dk.com.youbetterwrite.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Story implements Parcelable{

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
	private List<SectionsItem> sections;

	protected Story(Parcel in) {
		updatedAt = in.readString();
		idCategory = in.readInt();
		name = in.readString();
		createdAt = in.readString();
		id = in.readInt();
		state = in.readInt();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(updatedAt);
		dest.writeInt(idCategory);
		dest.writeString(name);
		dest.writeString(createdAt);
		dest.writeInt(id);
		dest.writeInt(state);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Story> CREATOR = new Creator<Story>() {
		@Override
		public Story createFromParcel(Parcel in) {
			return new Story(in);
		}

		@Override
		public Story[] newArray(int size) {
			return new Story[size];
		}
	};

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

	public void setSections(List<SectionsItem> sections){
		this.sections = sections;
	}

	public List<SectionsItem> getSections(){
		return sections;
	}

	@Override
 	public String toString(){
		return 
			"Story{" +
			"updated_at = '" + updatedAt + '\'' + 
			",id_category = '" + idCategory + '\'' + 
			",name = '" + name + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",state = '" + state + '\'' + 
			",category = '" + category + '\'' + 
			",deleted_at = '" + deletedAt + '\'' + 
			",sections = '" + sections + '\'' + 
			"}";
		}
}