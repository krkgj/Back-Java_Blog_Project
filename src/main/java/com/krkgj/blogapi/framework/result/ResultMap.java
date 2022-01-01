package com.krkgj.blogapi.framework.result;
import java.util.HashMap;
public class ResultMap extends HashMap<String, Object>
{
	private static final long serialVersionUID = 1L;

	public ResultMap() {}

	public ResultMap(String resultName, Object result) {
		super();
		this.put(resultName, result);
	}
}
