package org.group1.back_end.response.skills.databases;

public interface iDataBase<Key, Value> {
    void add(Key key, Value value);
    String getFirst(Key key);
    void printKeys();
}
