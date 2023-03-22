package org.group1.response;


/**
 * Class to API with the user interface
 * to allow and retrieve bot response
 * based on user input.
 */
public class GenerateResponse {

    private Skill loadedSkills;


    public GenerateResponse(Boolean loadSQL) {
        //Loads all skills from directory(for now)

        //TODO: loading from database
        //      for now just .txt to satisfy requirements
        try {
            loadedSkills = new Skill();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Retrieve response from skills
     * @param userInput Question mentioned by the user
     * @return the best matching response or default response
     */
    public String getBotResponse(String userInput) {
        return loadedSkills.getSkill(userInput);
    }

}
