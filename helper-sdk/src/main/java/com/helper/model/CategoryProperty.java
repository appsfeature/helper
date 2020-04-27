//package com.helper.model;
//
//
//
//import com.helper.util.LoggerCommon;
//
//import java.io.Serializable;
//import java.util.HashMap;
//
//
//public class CategoryProperty extends BaseCategoryProperty implements Serializable, Cloneable {
//
//
//    private String query = "";
//
//    private int catId = 0, subCatId = 0, imageResId = 0, position = 0;
//
//    private boolean isDate = false, isSubCat = false, isWebView = false, seeAnswer = false, isTimer = false, isLoadUI = false, isUseImageResId = false;
//
//    private HashMap<Integer, int[]> imageRes;
//
//    private boolean isCategoryOffline = false ;
//
//
//    public CategoryProperty() {
//    }
//
//
//    public boolean isCategoryOffline() {
//        return isCategoryOffline;
//    }
//
//    public void setCategoryOffline(boolean categoryOffline) {
//        isCategoryOffline = categoryOffline;
//    }
//
//    public CategoryProperty(boolean isDate) {
//        this.isDate = isDate;
//    }
//
//
//    public String getQuery() {
//        return query;
//    }
//
//    public CategoryProperty setQuery(String query) {
//        this.query = query;
//        return this;
//    }
//
//    public boolean isLoadUI() {
//        return isLoadUI;
//    }
//
//    public CategoryProperty setLoadUI(boolean loadUI) {
//        isLoadUI = loadUI;
//        return this;
//    }
//
//    public HashMap<Integer, int[]> getImageRes() {
//        return imageRes;
//    }
//
//    public CategoryProperty setImageRes(HashMap<Integer, int[]> imageRes) {
//        this.imageRes = imageRes;
//        return this;
//    }
//
//    public boolean isTimer() {
//        return isTimer;
//    }
//
//    public CategoryProperty setTimer(boolean timer) {
//        isTimer = timer;
//        return this;
//    }
//
//    public boolean isSeeAnswer() {
//        return seeAnswer;
//    }
//
//    public CategoryProperty setSeeAnswer(boolean seeAnswer) {
//        this.seeAnswer = seeAnswer;
//        return this;
//    }
//
//    public int getSubCatId() {
//        return subCatId;
//    }
//
//    public CategoryProperty setSubCatId(int subCatId) {
//        this.subCatId = subCatId;
//        return this;
//    }
//
//    public boolean isUseImageResId() {
//        return isUseImageResId;
//    }
//
//    public CategoryProperty setUseImageResId(boolean useImageResId) {
//        isUseImageResId = useImageResId;
//        return this;
//    }
//
//    public String getHost() {
//        return getHostAlias();
//    }
//
//    public CategoryProperty setHost(String host) {
//        setHostAlias(host);
//        return this;
//    }
//
//    public int getPosition() {
//        return position;
//    }
//
//    public CategoryProperty setPosition(int position) {
//        this.position = position;
//        return this;
//    }
//
//    public boolean isWebView() {
//        return isWebView;
//    }
//
//    public CategoryProperty setWebView(boolean webView) {
//        isWebView = webView;
//        return this;
//    }
//
//    public boolean isDate() {
//        return isDate;
//    }
//
//    public CategoryProperty setDate(boolean date) {
//        isDate = date;
//        return this;
//    }
//
//    public String getImageUrl() {
//        return getImage();
//    }
//
//    public CategoryProperty setImageUrl(String imageUrl) {
//        this.setImage(imageUrl);
//        return this;
//    }
//
//    public int getCatId() {
//        return catId;
//    }
//
//    public CategoryProperty setCatId(int catId) {
//        this.catId = catId;
//        return this;
//    }
//
//    public int getImageResId() {
//        return imageResId;
//    }
//
//    public CategoryProperty setImageResId(int imageResId) {
//        this.imageResId = imageResId;
//        return this;
//    }
//
//    public int getType() {
//        return getItemType();
//    }
//
//    public CategoryProperty setType(int type) {
//        this.setItemType(type);
//        return this;
//    }
//
//    public boolean isSubCat() {
//        return isSubCat;
//    }
//
//    public void setSubCat(boolean subCat) {
//        isSubCat = subCat;
//    }
//
//    public static CategoryProperty Builder() {
//        return new CategoryProperty();
//    }
//
//    @Override
//    protected Object clone() throws CloneNotSupportedException {
//        return super.clone();
//    }
//
//    public CategoryProperty getClone() {
//        try {
//            return (CategoryProperty) clone();
//        } catch (CloneNotSupportedException e) {
//            LoggerCommon.d(LoggerCommon.getClassPath(this.getClass(),"getClone"),e.toString());
//            return new CategoryProperty();
//        }
//    }
//}
