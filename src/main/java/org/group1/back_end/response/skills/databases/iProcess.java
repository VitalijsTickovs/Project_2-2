package org.group1.back_end.response.skills.databases;

public interface iProcess <Input, Output> {
    Output process(Input sentence);
}
