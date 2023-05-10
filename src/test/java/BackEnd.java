import org.app.connection.backEnd.BackEndManager;
import org.app.connection.backEnd.response.skill.skillData.SkillData;
import org.app.connection.backEnd.response.skill.skillData.SkillFileService;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BackEnd {

    @Test
    void Backend(){
        BackEndManager backEndManager = new BackEndManager();
        SkillData skillData = backEndManager.getSkillData();
        assertNotEquals(skillData, null);

        String expectedResponse = "Hello there";

        List<String> datas = new ArrayList<>();
        datas.add(expectedResponse);

        skillData.setCfg(datas);
        assertEquals(skillData.getCfg().get(0), expectedResponse);

        skillData.setTemplate(datas);
        assertEquals(skillData.getTemplate().get(0), expectedResponse);


        skillData.setQuery(datas);
        assertEquals(skillData.getQuery().get(0), expectedResponse);
    }

    @Test
    void ReadingSkills(){
        try {
            SkillFileService skillFileService = new SkillFileService();
            List<String> paths = skillFileService.readAll();
            assertEquals(paths.get(0),
                    "Question What event do I have planned on <DAY> at <TIME> in <CITY>?\n" +
                            "Slot <CITY> Maastricht\n" +
                            "Slot <CITY> Amsterdam\n" +
                            "Slot <CITY> Sittard\n" +
                            "Slot <CITY> Eind\n" +
                            "Slot <CITY> Utrecht\n" +
                            "Slot <DAY> Monday\n" +
                            "Slot <DAY> Tuesday\n" +
                            "Slot <DAY> Wednesday\n" +
                            "Slot <DAY> Thursday\n" +
                            "Slot <DAY> Friday\n" +
                            "Slot <DAY> Saturday\n" +
                            "Slot <DAY> Sunday\n" +
                            "Slot <DAY> July\n" +
                            "Slot <TIME> 09:00\n" +
                            "Slot <TIME> 10:00\n" +
                            "Slot <TIME> 11:00\n" +
                            "Slot <TIME> 12:00\n" +
                            "Slot <TIME> 13:00\n" +
                            "Slot <TIME> 14:00\n" +
                            "Slot <TIME> 15:00\n" +
                            "Slot <TIME> 16:00\n" +
                            "Slot <TIME> 17:00\n" +
                            "Slot <TIME> 18:00\n" +
                            "Slot <TIME> 255:00\n" +
                            "Action <CITY> Maastricht <DAY> Tuesday <TIME> 14:00 You have a swimming lesson.\n" +
                            "Action <CITY> Amsterdam <DAY> Monday <TIME> 09:00 You have a TCS lecture.\n" +
                            "Action I have no idea\n" +
                            "Action <CITY> Eind <DAY> Saturday <TIME> 255:00 Save\n" +
                            "Action <CITY> Sittard <DAY> Tuesday <TIME> 10:00 Hi\n" +
                            "Action <DAY> Tuesday <TIME> 10:00 fdasfdasfsd\n" +
                            "Action <DAY> Tuesday <TIME> 09:00 Hello to every City\n");
        }catch(Exception e){
            assertTrue(false);
        }
    }
}
