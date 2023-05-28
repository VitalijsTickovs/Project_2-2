package org.group1.back_end;

import org.group1.back_end.response.Response;
import org.group1.back_end.response.skills.Skill;
import org.group1.back_end.utilities.strings.Print;
import org.group1.back_end.utilities.enums.DB;



public class MAIN {

    public static DB DATABASE = DB.DB_KEYWORDS;
    public static void main(String[] args) throws Exception {

        Response s = new Response();

        String[] diffWays = {
                "Which lectures are there on Monday at 9?",
                "What are the lectures on Monday at 9?",
                "What are the lectures on Monday at 9?",
                "Which lectures are scheduled for 9am on Monday?",
                "Can you tell me which lectures are happening at 9am on Monday?",
                "What are the classes on Monday at 9am?",
                "I'm trying to find out which lectures are taking place on Monday at 9am, do you know?",
                "Can you give me a list of lectures happening on Monday at 9am?",
                "Which lectures should I attend on Monday at 9am?",
                "What's on the lecture for monday at ix?",
                "lecture monday nine"
        };

        DB[] db = {DB.DB_KEYWORDS, DB.DB_PERFECT_MATCHING, DB.DB_VECTORS, DB.DB_VECTORS_SEQ};


        for (int i = 0; i < db.length; i++) {
            DATABASE = db[i];
            Skill.DATABASE_MANAGER.printKeys(db[i]);
        }

        for (int i = 0; i < db.length; i++) {
            double counter = 0;
            DATABASE = db[i];

            System.out.println("\n\n\n\n\n<|-----------------------------|>  " + db[i] + "  <|--------------------------------|>");

            for (int j = 0; j < diffWays.length; j++) {

//                Response.setDatabase(db[i]);
                String response = s.getResponse(diffWays[j]).trim();
                System.out.println("QUERY: " + diffWays[j] + " ----> RESPONSE: " + response);

                if(response.contains("with math")){
                    counter += 1;
                } else if (response.contains("no idea")) {
                    counter += 0.5;
                }
            }
            counter = (counter / diffWays.length) * 100;

            System.out.println("|--------------------------------------------------------------------------------|");
            System.out.println("\t\t\t\t\t\t\tACCURACY: " + counter + "%");
            System.out.println("|--------------------------------------------------------------------------------|\n\n\n\n\n");
        }

        Print.printSimilarityTable();
    }
}
