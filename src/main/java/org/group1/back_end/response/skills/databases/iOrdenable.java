package org.group1.back_end.response.skills.databases;

public interface iOrdenable <Key, Value, Direction> {

    Value order(Key key, Key object, Direction upDown);
    Value getFirst(Key key, Key object, Direction upDown, double threshold);

}
