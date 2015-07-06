package ru.mera.agileboard.rest.info;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by antfom on 18.02.2015.
 */
@XmlRootElement
@XmlType(propOrder = {"name", "count"})
public class TagInfo {

    private String name;

    private int count;

    public TagInfo() {
    }

    public TagInfo(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public static List<TagInfo> fromTagsMap(Map<String, Integer> tags) {
        List<TagInfo> list = tags.entrySet().stream().map(e -> new TagInfo(e.getKey(), e.getValue())).collect(Collectors.toList());

        System.err.println(list);

        Collections.sort(list, new Comparator<TagInfo>() {
            @Override
            public int compare(TagInfo o1, TagInfo o2) {
                return o2.getCount() - o1.getCount();
            }
        });
        System.err.println("----");
        System.err.println(list);
        if (list.size() > 10) {
            list = list.subList(0, 10);
        }

        Collections.sort(list, new Comparator<TagInfo>() {
            @Override
            public int compare(TagInfo o1, TagInfo o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        System.err.println("----");
        System.err.println(list);
        return list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" + name +
                ", " + count +
                '}';
    }
}
