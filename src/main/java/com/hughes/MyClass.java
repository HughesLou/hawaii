package com.hughes;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1466811 on 9/1/2015.
 */
public class MyClass {
    private String name;

    public static void main(String[] args) {
        MyClass m1 = new MyClass();
        MyClass m2 = new MyClass();
        m1.name = m2.name = "A";

        m1 = call(m1, m2);

        System.out.println(m1 + " & " + m2);
    }

    private static MyClass call(MyClass... myClasses) {
        myClasses[0] = myClasses[1];
        myClasses[1].name = "B";
        return myClasses[0];
    }

    private static final List<String> NAMES =
            new ArrayList<String>() {{
                add("Hughes");
                System.out.println(NAMES);
            }};

    private List<String> list = Lists.newArrayList();
}
