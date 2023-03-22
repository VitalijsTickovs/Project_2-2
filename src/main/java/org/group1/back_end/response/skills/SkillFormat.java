package org.group1.back_end.response.skills;

public class SkillFormat {


    private boolean problem_Dquestion = false;
    private int DquestionLine = -1;
    private boolean problem_question = false;
    private int questionLine = -1;
    private boolean problem_slot = false;
    private int slotLine = -1;
    private boolean problem_action = false;
    private int actionLine = -1;


    public SkillFormat() {}

    public String getProblem(){
        if(problem_question){
            return "The problem is in question line " + questionLine;
        }

        if(problem_slot){
            return "The problem is in slot line " + slotLine;
        }

        if(problem_action){
            return "The problem is in action line " + actionLine;
        }
        return "No format problems";
    }


    public boolean hasProblemFormat(){
        return problem_question || problem_action || problem_slot;
    }


    public boolean isProblem_question() {
        return problem_question;
    }

    public void setProblem_question(boolean problem_question) {
        this.problem_question = problem_question;
    }

    public boolean isProblem_slot() {
        return problem_slot;
    }

    public void setProblem_slot(boolean problem_slot) {
        this.problem_slot = problem_slot;
    }

    public boolean isProblem_action() {
        return problem_action;
    }

    public void setProblem_action(boolean problem_action) {
        this.problem_action = problem_action;
    }

    public int getQuestionLine() {
        return questionLine;
    }

    public void setQuestionLine(int questionLine) {
        this.questionLine = questionLine;
    }

    public int getSlotLine() {
        return slotLine;
    }

    public void setSlotLine(int slotLine) {
        this.slotLine = slotLine;
    }

    public int getActionLine() {
        return actionLine;
    }

    public void setActionLine(int actionLine) {
        this.actionLine = actionLine;
    }

    public boolean isProblem_Dquestion() {
        return problem_Dquestion;
    }

    public void setProblem_Dquestion(boolean problem_Dquestion) {
        this.problem_Dquestion = problem_Dquestion;
    }

    public int getDquestionLine() {
        return DquestionLine;
    }

    public void setDquestionLine(int dquestionLine) {
        DquestionLine = dquestionLine;
    }
}
