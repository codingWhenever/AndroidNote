package com.sz.leo.androidnote.chapter10.rxJava;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：leo
 * @date：2019/6/27
 * @email：lei.lu@e-at.com
 */
public class Plan {
    private String time;
    private String content;
    private List<String> actionList = new ArrayList<>();

    public Plan(String time, String content) {
        this.time = time;
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getActionList() {
        return actionList;
    }

    public void setActionList(List<String> actionList) {
        this.actionList = actionList;
    }
}
