package sv.dk.com.youbetterwrite.Modelos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DK-Ragnar on 12/11/2018.
 */

public class ResponseCategory {

    @SerializedName("data")
    private List<Category> data;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public void setData(List<Category> data){
        this.data = data;
    }

    public List<Category> getData(){
        return data;
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
