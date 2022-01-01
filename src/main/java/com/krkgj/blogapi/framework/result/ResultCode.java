package com.krkgj.blogapi.framework.result;

public interface ResultCode 
{
	public static String RESULT_OK 						= "200";
	public static String RESULT_CREATED 				= "201";
	public static String RESULT_NOT_MODIFIED 			= "304";
	public static String RESULT_NOT_FOUND 				= "404";
	public static String RESULT_INTERNAL_SERVER_ERROR 	= "500";
	public static String RESULT_BAD_REQUEST 			= "400";
	public static String RESULT_UNAUTHORIZED 			= "401";
	public static String RESULT_FORBIDDEND 				= "403";
	public static String RESULT_EMPTY					= "100";
	public static String RESULT_NOT_EMPTY				= "101";

	public static String USER_NOT_FOUND 				= "404";

	public static String TOKEN_EXPIRED 					= "1403";
	public static String REFRESH_TOKEN_NOT_FOUND 		= "1404";

	public static String REQUEST_VACATION_DAYS_LESS_THEN_REMAINING_VACATION_DAYS	= "1404";
	public static String DUPLICATED_VACATION 										= "1403";
	public static String VACATION_STARTDATE_IS_BEFORE_NOW 							= "1402";
	public static String NO_VACATION_LEFT											= "1401";
	
}