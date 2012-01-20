package com.nali.spreader.test.assertion;

import java.util.Map;

/**
 * 
 * @author gavin
 *
 */
public abstract class ResultCheckClosureBase implements ResultCheckClosure{
    protected Map<String, Object> checkContext;

    public ResultCheckClosureBase(Map<String, Object> checkContext) {
       this.checkContext = checkContext;
    }

    public Map<String, Object> getCheckContext() {
        return checkContext;
    }
}
