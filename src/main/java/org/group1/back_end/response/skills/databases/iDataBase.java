package org.group1.back_end.response.skills.databases;

public interface iDataBase {
    void add(String key, String value);
    String getFirst(String key);
    void printDistances(String key);
    void printKeys();
}
