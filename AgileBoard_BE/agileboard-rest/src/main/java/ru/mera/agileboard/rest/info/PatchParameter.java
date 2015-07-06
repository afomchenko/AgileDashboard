package ru.mera.agileboard.rest.info;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by antfom on 10.03.2015.
 */
@XmlRootElement
public class PatchParameter {

    private String op;
    private String path;
    private String value;

    public PatchParameter() {
    }

    public PatchParameter(String op, String path, String value) {

        this.op = op;
        this.path = path;
        this.value = value;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String[] pathElements() {
        return path.split("/");
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PatchParam{" +
                "op='" + op + '\'' +
                ", path='" + path + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
