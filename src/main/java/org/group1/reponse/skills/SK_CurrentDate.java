package org.group1.reponse.skills;

import java.util.Date;
import java.util.List;

public class SK_CurrentDate implements iSkill{

    @Override
    public String getSkill(List<String> words) {
        return new Date().toString();
    }

    @Override
    public String getSkill() {
        return new Date().toString();
    }

    public static void main(String[] args) {
        iSkill a = new SK_CurrentDate();
        System.out.println(a.getSkill(null));
    }


}
