package org.group1.GUI;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Arrays;

public class TestSkill {
    private ArrayList<SimpleStringProperty> listOfColumns = new ArrayList();
    private String[] tempString;
    int count = 0;
    TestSkill(String... stringList) {
        tempString = Arrays.copyOf(stringList, stringList.length);
        count=tempString.length;
        defineSimpleStringProperties(stringList);
        for (int i = 0; i < count; i++) {
            getData();
        }
    }

    public void defineSimpleStringProperties(String[] stringList){
        for (int i = 0; i < stringList.length; i++) {
            listOfColumns.add(new SimpleStringProperty(stringList[i]));
        }
    }
    public String getData(){
        for (int i = 0; i < count; i++) {
            return listOfColumns.get(i).get();
        }
        return "hello";
    }
//    public void setData(){
//        for (int i = 0; i < tempString.length;i++) {
//            listOfColumns.get(i).set(tempString[i]);
//        }
//    }
//    public String getFirstName() {
//        return slotActionType.get();
//    }
////    public void setFirstName(String fName) {
////        slotActionType.set(fName);
////    }
//
//    public String getLastName() {
//        return lastName.get();
//    }
////    public void setLastName(String fName) {
////        lastName.set(fName);
////    }
//
//    public String getEmail() {
//        return email.get();
//    }
//    public void setEmail(String fName) {
//        email.set(fName);
//    }

}
