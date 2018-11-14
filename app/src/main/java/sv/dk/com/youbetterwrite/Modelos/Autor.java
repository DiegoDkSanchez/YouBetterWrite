package sv.dk.com.youbetterwrite.Modelos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DK-Ragnar on 13/11/2018.
 */

public class Autor implements Serializable {

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("id_category")
        private int idCategory;

        @SerializedName("first_name")
        private String first_name;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("id")
        private int id;

        @SerializedName("last_name")
        private String last_name;

        @SerializedName("email")
        private String email;

        @SerializedName("deleted_at")
        private Object deletedAt;

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public String toString(){
        return
                "Story{" +
                        "updated_at = '" + updatedAt + '\'' +
                        ",first_name = '" + first_name + '\'' +
                        ",last_name = '" + last_name + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",id = '" + id + '\'' +
                        ",email = '" + email + '\'' +
                        ",deleted_at = '" + deletedAt + '\'' +
                        "}";
    }
}
