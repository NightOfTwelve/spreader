package com.nali.stat.dc.collecter;


import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.nali.stat.dc.data.CountQuery;
import com.nali.stat.dc.data.CountResult;
import com.nali.stat.dc.data.DateCount;
import com.nali.stat.dc.data.QueryUnit;
import com.nali.stat.dc.data.SimpleCount;
import com.nali.stat.dc.exception.DataCollectionException;

/**
 * 简单计数服务。根据提供的服务名，对象ID，值，日期。将服务名，对象ID,日期构成简单计数服务的Key，值作为value。
 * 将键值对存储在Key-Value store中。并提供查询服务。
 * 提供以下服务：
 * <table border="1">
 * 	<th> 
 *  <tr>
 *	 <td><b>类型</b></td>
 *	 <td><b>描述</b></td>
 *	</tr>
 * 	</th>
 * 	<tbody>
 *   <tr>
 * 		 <td>增加</td>
 * 		 <td>增加给定的数值</td>
 * 
 * 	 </tr>
 *     <tr>
 * 		 <td>减少</td>
 * 		 <td>减去给定的数值</td>
 * 	  </tr>
 *    <tr>
 * 		 <td>赋值</td>
 * 		 <td>直接设置给定的数值</td>
 * 	  </tr>
 *    <tr>
 * 		 <td>重置</td>
 * 		 <td>直接赋值为0</td>
 * 	  </tr>
 *    <tr>
 * 		 <td>获取</td>
 * 		 <td>获取指定记录的值</td>
 * 	  </tr>
 * </tbody>
 * </table>
 * 
 * @author gavin
 *
 */
public interface SimpleCountCollecter extends DataCollecter {
    /**
     * 将name服务的id对象的值增加count。如果服务配置了<code>{@link TimeUnit}</code>,就将当前时间单元的计数值增加count。
     * @param name 服务名
     * @param id 对象id
     * @param count 增加的值
     * @return 增加后的值，如果服务配有时间单元，返回的是当前时间单元的值
     * @throws DataCollectionException
     */
	long incr(String name, String id, int count) throws DataCollectionException;
	
	

	/**
	 * 根据<code>{@link DateCount}</code>描述的值。对相关的记录增加指定的值。
	 * @param count 增加计数的参数
	 * @throws DataCollectionException 无法完成计数服务
	 */
	void incr(DateCount dateCount) throws DataCollectionException;
	
	/**
	 * 根据<code>{@link SimpleCount}</code>描述的值。对相关的记录增加指定的值。
	 * @param count 增加计数的参数
	 * @return 增加的结果，如果服务配有时间单元，返回的是当前时间单元的值
	 * @throws DataCollectionException
	 */
	CountResult incr(SimpleCount count) throws DataCollectionException;
	
	
	/**
	 * 将name服务的id对象值加一.
	 * @see #incr(String, String, int)
	 */
	long incr(String name, String id) throws DataCollectionException;
	
	/**
	 * 将name服务id对象在date时间计数值增加指定的数值。
	 * @param name 服务名
	 * @param id 对象ID
	 * @param count 增加的数目
	 * @param date 指定的时间
	 * @return 指定时间对应时间单元增加后的值
	 * @throws DataCollectionException
	 */
	long incr(String name, String id, int count, Date date) throws DataCollectionException;
	
    /**
     * 将name服务的id对象的减去count。如果服务配置了<code>{@link TimeUnit}</code>,就将当前时间单元的计数值增加count。
     * @param name 服务名
     * @param id 对象id
     * @param count 减去的值
     * @return 减去后的值，如果服务配有时间单元，返回的是当前时间单元的值
     * @throws DataCollectionException
     */
	long decr(String name, String id, int count) throws DataCollectionException;
	
	/**
	 * 将name服务id对象在date时间计数值减去指定的数值。
	 * @param name 服务名
	 * @param id 对象ID
	 * @param count 数目
	 * @param date 指定的时间
	 * @return 指定时间对应时间单元减去后的值
	 * @throws DataCollectionException
	 */
	long decr(String name, String id, int count, Date date) throws DataCollectionException;
	
	/**
	 * 将name服务的id对象的减1
	 * @see #decr(String, String, int)
	 */
	long decr(String name, String id) throws DataCollectionException;
	
	/**
	 * 根据<code>{@link DateCount}</code>描述的值。对相关的记录减去指定的值。
	 * @param count 减去计数的参数
	 * @throws DataCollectionException 无法完成计数服务
	 */
	void decr(DateCount dateCount) throws DataCollectionException;
	
	/**
	 * 根据<code>{@link SimpleCount}</code>描述的值。对相关的记录减去指定的值。
	 * @param count 减去计数的参数
	 * @return 减去后的结果，如果服务配有时间单元，返回的是当前时间单元的值
	 * @throws DataCollectionException
	 */
	CountResult decr(SimpleCount count) throws DataCollectionException;
	
	/**
	 * 根据<code>{@link DateCount}</code>描述的值。对相关的记录设置值。
	 * @param count 设置的参数
	 * @throws DataCollectionException 无法完成计数服务
	 */
	void set(DateCount count) throws DataCollectionException;
	
	/**
	 * 根据<code>{@link SimpleCount}</code>描述的值。对相关的记录设置值。
	 * @param count 设置的参数
	 * @throws DataCollectionException 无法完成计数服务
	 */
	void set(SimpleCount count) throws DataCollectionException;
	
	/**
	 * 将name服务id对象设置成指定的数值。如果服务配置了<code>{@link TimeUnit}</code>,就将当前时间单元的计数值设置成指定的值。
	 * @param name 服务名
	 * @param id 对象ID
	 * @param count 数目
	 * @throws DataCollectionException
	 */
	void set(String name, String id, long value) throws DataCollectionException;
	
	/**
	 * 将name服务id对象在对应时间的记录设置成指定的数值
	 * @param name 服务名
	 * @param id 对象ID
	 * @param count 数目
	 * @param date 指定的时间
	 * @throws DataCollectionException
	 */
	void set(String name, String id, long value, Date date) throws DataCollectionException;
	
	/**
	 * 重置name服务id对象的记录数值。
	 * @param name 服务名
	 * @param id 对象id
	 * @throws DataCollectionException
	 */
	void reset(String name, String id) throws DataCollectionException;
	
	/**
	 * 重置name服务id对象最近limit条数的记录。该服务必须配置有<code>TimeUnit</code>。
	 * @param name 服务名
	 * @param id 对象id
	 * @param limit 条数
	 * @throws DataCollectionException
	 */
	void reset(String name, String id, int limit) throws DataCollectionException;
	
	/**
	 * 获取name服务id对象的计数值。如果有配了<code>TimeUnit</code>，则返回当前时间单元的数值。
	 * @param name
	 * @param id
	 * @return
	 * @throws DataCollectionException
	 */
	long get(String name, String id) throws DataCollectionException;
	
	/**
	 * 获取那么服务id对象指定条数的记录值的和。该服务必须配有<code>TimeUnit</code>。
	 * 获取204用户近一个星期内的发帖总数。
	 * <p>
	 * 	  collecter.get("paste", "204", 7);
	 * </p>
	 * @param name 服务名
	 * @param id 对象id
	 * @param limit 时间单元数
	 * @return 计数总和
	 * @throws DataCollectionException
	 */
	long get(String name, String id, int limit) throws DataCollectionException;
	
	/**
	 * 批量获取id对象多个服务在limit个数的时间单元内的计数总和
	 * @param names 多个服务名。
	 * @param id 对象id
	 * @param limit 时间单元个数
	 * @return 和各个服务对应的记录和，顺序和提供的服务名的顺序是一致的。
	 * @throws DataCollectionException
	 */
	long[] get(String[] names, String id, int limit) throws DataCollectionException;
	
	/**
	 * 批量获取id对象多个服务的计数值。如果配有<code>TimeUnit</code>为当前时间单元的记录值。
	 * @param names 多个服务名
	 * @param id 对象id
	 * @return 和各个服务对应的记录值，顺序和提供的服务名的顺序是一致的。
	 * @throws DataCollectionException
	 */
	long[] get(String[] names, String id) throws DataCollectionException;
	
	/**
	 * 批量获取name服务下多个对象的计数值。如果配有<code>TimeUnit</code>为当前时间单元的记录值。
	 * @param name 服务名
	 * @param ids 多个对象id
	 * @return 和各对象对应的记录值。顺序和提供的对象id顺序是一致的。
	 * @throws DataCollectionException
	 */
	long[] get(String name, String[] ids) throws DataCollectionException;
	
	/**
	 * 批量获取name服务下多个对象的多条计数总和，
	 * @param name 服务名
	 * @param ids 多个对象id
	 * @param limit 指定条数
	 * @return 和各对象对应的计数总和。顺序和提供的对象id顺序是一致的。
	 * @throws DataCollectionException
	 */
	long[] get(String name, String[] ids, int limit) throws DataCollectionException;
	
	/**
	 * 批量获取<code>CountQuery</code>提供的服务，id，时间的记录值之和。
	 * @param query
	 * @return 对应的计数之和。
	 * @throws DataCollectionException
	 */
	<T extends QueryUnit>  CountResult  get(CountQuery<T> query) throws DataCollectionException;
	
	
	/**
	 * 根据<code>CountQuery</code>批量删除计数值。
	 * @param query
	 * @throws DataCollectionException
	 */
	<T extends QueryUnit> void del(CountQuery<T> query) throws DataCollectionException;
}
