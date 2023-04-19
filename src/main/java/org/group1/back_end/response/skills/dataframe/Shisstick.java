package org.group1.back_end.response.skills.dataframe;

import javax.xml.crypto.Data;
import java.util.Arrays;

public class Shisstick {

    public static void main(String[] args) {
        DataFrame ds = new DataFrame(Arrays.asList("PERFECT MATCH", "RESPONSE"));
        ds.isSet(true);
        ds.insert(new Rows(Arrays.asList(new Cell<>(Arrays.asList("which", "lecture", "be", "there", "on", "monday", "at", "<time>")), new Cell<>("I have no idea"))));
//        System.out.println(ds);
//        System.out.println(ds.contains(new Rows(Arrays.asList(new Cell<>(Arrays.asList("which", "lecture", "be", "there", "on", "monday", "at", "<time>")), new Cell<>("I have no idea")))));
        ds.insert(new Rows(Arrays.asList(new Cell<>(Arrays.asList("which", "lecture", "be", "there", "on", "monday", "at", "<time>")), new Cell<>("I have no idea"))));
//        System.out.println(ds);
    }
}
