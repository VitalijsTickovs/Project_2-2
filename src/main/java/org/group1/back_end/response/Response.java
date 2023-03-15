package org.group1.back_end.response;

public class Response {

    private final ResponseLibrary responseLibrary;

    public Response() throws Exception{
        this.responseLibrary = new ResponseLibrary();
    }

    public String getResponse(String word){
        return responseLibrary.getResponseSkills(word);
    }
}
