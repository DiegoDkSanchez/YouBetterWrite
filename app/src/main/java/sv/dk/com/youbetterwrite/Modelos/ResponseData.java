package sv.dk.com.youbetterwrite.Modelos;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class ResponseData {

	@SerializedName("data")
	private List<Story> data;

	//@SerializedName("data")
	private List<Category> dataCategory;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setData(List<Story> data){
		this.data = data;
	}

	public List<Story> getData(){
		return data;
	}

	public List<Category> getDataCategory() {
		return dataCategory;
	}

	public void setDataCategory(List<Category> dataCategory) {
		this.dataCategory = dataCategory;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"ResponseData{" +
			"data = '" + data + '\'' + 
			",success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}