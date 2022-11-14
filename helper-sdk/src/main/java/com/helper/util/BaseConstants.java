package com.helper.util;

public interface BaseConstants {

    String DEFAULT_KEY_IMAGE_PATH = "image_path";
    String DEFAULT_KEY_PDF_PATH = "pdf_path";

    String NO_DATA = "No Data";
    String EMPTY_OR_NULL_DATA = "Empty or Null Data";

    String SUCCESS = "success";
    String FAILURE = "failure";
    String NO_INTERNET_CONNECTION = "No Internet Connection";
    int ACTIVE = 1;
    int DE_ACTIVE = 0;
    String WEB_VIEW_URL = "webViewUrl";
    String ID = "id";
    String TITLE = "title";
    String CATEGORY_PROPERTY = "category_property";
    String EXTRA_PROPERTY = "extra_property";
    String DEFAULT_DIRECTORY = "Helper";

    interface Error {
        String MSG_ERROR = "Error, please try later.";
        String DATA_NOT_FOUND = "Error, Data not found";
        String CATEGORY_NOT_FOUND = "Error, This is not supported. Please update";
        String MSG_ERROR_INTEGRATION = "Error : Integration, Please contact to developer.";
    }

}
