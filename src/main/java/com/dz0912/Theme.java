package com.dz0912;

import java.util.ArrayList;
import java.util.List;

public class Theme {
    private String title;
    public List<Comment> comments = new ArrayList<>();

    public Theme(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
