package org.group1.response.matching.Support;

import java.util.List;

public interface iMatcher<E> {
    public E match(List<E> list, String question) throws Exception;
}
