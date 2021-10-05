package com.krkgj.blogapi.framework.utility;

import org.springframework.data.domain.Sort;

public class Utility 
{

	
    public static boolean isNull(String str)
    {
        if (str == null || "".equals(str) || str.trim().length() == 0 || str.equals("null"))
            return true;
        else
            return false;
    }
    
    public static boolean isNotNull(String str)
    {
        return !isNull(str);
    }
}
