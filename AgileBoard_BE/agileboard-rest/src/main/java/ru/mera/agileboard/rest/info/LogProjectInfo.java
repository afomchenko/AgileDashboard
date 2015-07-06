package ru.mera.agileboard.rest.info;

import java.util.Map;

/**
 * Created by antfom on 10.03.2015.
 */
public class LogProjectInfo {
    private Map<String, String> map;

    public LogProjectInfo(Map<String, String> map) {
        this.map = map;
    }

    public LogProjectInfo() {

    }


    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
