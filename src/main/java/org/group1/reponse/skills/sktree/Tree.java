package org.group1.reponse.skills.sktree;

import org.group1.collections.Delim;
import org.group1.exception.NullTextException;
import org.group1.reponse.procesor.Tokenization;
import org.group1.reponse.skills.sk.SK_CurrentDate;
import org.group1.reponse.skills.sk.iSkill;

import java.util.LinkedList;
import java.util.List;

public class Tree {

    private Node root;
    private List<String> queue;

    public Tree(Node root) {
        this.root = root;
    }

    public void checker(){

    }

    public void createBranch(List<String> words, iSkill skill){
        queue = new LinkedList<>(words);
        iterator((LinkedList<String>) queue, skill, this.root);
    }

    public void iterator(LinkedList<String> words, iSkill skill, Node node){

        if(words.size() == 0){
            node.setSkill(skill);
        }else {
            String word = words.pop();
            if(node.exist(word)){
                iterator(words, skill, node.getMatch(word));
            }else {
                System.out.println(word);
                node.addChild(new Node(node, word));
                iterator(words, skill, node.getMatch(word));
            }
        }
    }

    static {

        //RULE 1:
        String a = "what is your name";
        iSkill b = new SK_CurrentDate();

        //RULE 2:
        String a2 = "what is your name";
        iSkill b2 = new SK_CurrentDate();
    }

    public static void main(String[] args) throws NullTextException {
        Tree t = new Tree(new Node(null, "a"));
        List<String> test = Tokenization.tokenize("what is your name", Delim.SPACE);
        t.createBranch(test, new SK_CurrentDate());
        List<String> test2 = Tokenization.tokenize("what your addres", Delim.SPACE);
        t.createBranch(test2, new SK_CurrentDate());

    }
}
