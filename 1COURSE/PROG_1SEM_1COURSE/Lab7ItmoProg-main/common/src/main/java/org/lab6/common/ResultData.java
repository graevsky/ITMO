package org.lab6.common;

import java.io.Serializable;

public class ResultData implements Serializable {
    private String result;
    private String error;
    private int resultId;
    public String getResult(){
        return result;
    }
    public String getError(){
        return error;
    }
    public int getResultId(){
        return resultId;
    }
    public ResultData(String result,String error,int resultId){
        this.result = result;
        this.error = error;
        this.resultId = resultId;
    }
}
