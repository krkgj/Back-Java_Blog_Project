package com.krkgj.blogapi.utility;

import org.springframework.data.domain.Sort;

public class Utility 
{
	public static Sort getSortOrderByAsc(String str)
	{
		return Sort.by(Sort.Direction.ASC, str);
	}

	public static Sort getSortOrderByDesc(String str)
	{
		return Sort.by(Sort.Direction.DESC, str);
	}
	
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
