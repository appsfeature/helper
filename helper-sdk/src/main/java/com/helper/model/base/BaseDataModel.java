package com.helper.model.base;

import androidx.room.Ignore;

import com.adssdk.BaseAdModelClass;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseDataModel extends BaseAdModelClass {

    @Expose
    @SerializedName(value="image_path", alternate={"whiteboard_image_path"})
    @Ignore
    private String imagePath ;

    @Expose
    @SerializedName(value="pdf_path", alternate={"whiteboard_pdf_path"})
    @Ignore
    private String pdfPath ;

    @Expose
    @SerializedName(value="content_image_path")
    @Ignore
    private String contentImagePath ;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getContentImagePath() {
        return contentImagePath;
    }

    public void setContentImagePath(String contentImagePath) {
        this.contentImagePath = contentImagePath;
    }
}