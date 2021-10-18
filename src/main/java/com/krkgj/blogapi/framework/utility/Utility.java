package com.krkgj.blogapi.framework.utility;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
    
    public static JSONObject jsonParser(String str)
    {
        JSONParser jsonParser = null;
        JSONObject jsonObject = null;

        if (str == null)
            return null;

        System.out.println("str > " + str);
        str = str.replaceAll("\\", "");

        try
        {
            jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(str);
        }
        catch (ParseException pe)
        {
            return null;
        }
        return jsonObject;
    }
}
