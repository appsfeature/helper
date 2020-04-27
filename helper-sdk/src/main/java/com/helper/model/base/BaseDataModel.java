//package com.helper.model.base;
//
//import androidx.room.Ignore;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//import java.io.Serializable;
//
//public class BaseDataModel implements Serializable {
//
//    @Expose
//    @SerializedName(value="image_path", alternate={"whiteboard_image_path"})
//    @Ignore
//    private String imagePath ;
//
//    @Expose
//    @SerializedName(value="pdf_path", alternate={"whiteboard_pdf_path"})
//    @Ignore
//    private String pdfPath ;
//
//    public String getImagePath() {
//        return imagePath;
//    }
//
//    public void setImagePath(String imagePath) {
//        this.imagePath = imagePath;
//    }
//
//    public String getPdfPath() {
//        return pdfPath;
//    }
//
//    public void setPdfPath(String pdfPath) {
//        this.pdfPath = pdfPath;
//    }
//}