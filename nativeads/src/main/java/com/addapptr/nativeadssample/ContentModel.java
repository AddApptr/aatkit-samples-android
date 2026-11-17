package com.addapptr.nativeadssample;

public class ContentModel {

    private final String text;
    public ContentModel(int rowNumber) {
        text = "Sample row number:" + rowNumber;
    }

    public String getText() {
        return text;
    }
}
