package com.hughes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;

/**
 * Created by 1466811 on 9/1/2015.
 */
public class MyClass {

    private String name;

    public static void main(String[] args) {
        String input = "爱恋<num=tel>12390</num>说,<num=tel>66666</num>";
        Matcher matcher = Pattern.compile("(<num=(tel|dig)>\\d+</num>)").matcher(input);
        int inc = 1;
        while (matcher.find()) {
            String reservedTag = matcher.group(inc);
            System.out.println(reservedTag);
            inc++;
        }

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

    private static final List<String> NAMES = new ArrayList<String>() {

        {
            add("Hughes");
            System.out.println(NAMES);
        }
    };

    private List<String> list = Lists.newArrayList();
}
