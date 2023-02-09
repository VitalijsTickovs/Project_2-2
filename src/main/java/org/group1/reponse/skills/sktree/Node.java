package org.group1.reponse.skills.sktree;

import org.group1.reponse.skills.sk.iSkill;

import java.util.ArrayList;
import java.util.List;

public class Node{

    private Node parent;
    private String id;
    private List<Node> children;
    private iSkill skill;

    public Node(Node parent, String id){
        this.children = new ArrayList<>();
        this.parent = parent;
        this.id = id;
    }

    public boolean exist(String word){
        for (Node child : children) {
            if(child.getId().equals(word)){
                return true;
            }
        }
        return false;
    }

    public Node getMatch(String word){
        for (Node child : children) {
            if(child.getId().equals(word)){
                return child;
            }
        }
        return null;
    }

    public Node getParent() {
        return parent;
    }

    public String getId() {
        return id;
    }

    public List<Node> getChildren() {
        return children;
    }

    public String getAction(List<String> action){
        return this.skill.getSkill(action);
    }

    public String getAction(){
        return this.skill.getSkill();
    }

    public void setSkill(iSkill skill){
        this.skill = skill;
    }

    public boolean addChild(Node node){
        return this.children.add(node);
    }

    public boolean isLeafNode(){
        return (this.getChildren().size() == 0);
    }

    public boolean isRoot(){
        return (parent == null);
    }

    public iSkill getSkill() {return skill;}
}
