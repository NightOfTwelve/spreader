package com.nali.stat.dc.collecter;

import com.nali.stat.dc.data.Record;
import com.nali.stat.dc.exception.DataCollectionException;
/**
 * 收集用户提交的数据，记录到统计中心。
 * @author gavin
 *
 */
public interface DataCollecter  {
	
	/**
	 * 添加一个记录值
	 * @param name 服务名
	 * @param id 服务对象的ID
	 * @param record 记录
	 * @throws DataCollectionException 无法完成统计服务时
	 */
	void set(String name, String id, Record record)  throws DataCollectionException;
	
	/**
	 * 删除一个记录
	 * @param name 服务名
	 * @param id 服务对象的ID
	 * @throws DataCollectionException 无法完成统计服务时
	 */
	boolean del(String name, String id) throws DataCollectionException;
	
}
