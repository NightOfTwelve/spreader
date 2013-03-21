package com.nali.spreader.lucene.service;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;

import com.nali.spreader.data.ReplySearch;

/**
 * 查询相关服务
 * 
 * @author xiefei
 * 
 */
public interface ISearchService {
	/**
	 * 根据域建立索引
	 * 
	 * @param fields
	 * @return
	 */
	long index(List<Field> fields);

	/**
	 * 根据文档建立索引
	 * 
	 * @param docs
	 * @return
	 */
	void indexDocs(List<Document> docs);

	/**
	 * 建立文档的索引
	 * 
	 * @param doc
	 * @return
	 */
	boolean indexDoc(Document doc);

	/**
	 * 按Query删除索引
	 * 
	 * @param queries
	 * @return
	 */
	long delete(Query... queries);

	/**
	 * 按Term删除索引
	 * 
	 * @param terms
	 * @return
	 */
	long delete(Term... terms);

	/**
	 * 删除全部索引
	 * 
	 * @return
	 */
	long deleteAll();

	/**
	 * 更新索引
	 * 
	 * @param t
	 * @param doc
	 * @return
	 */
	long update(Term t, Document doc);

	/**
	 * 根据关键字查询回复内容
	 * 
	 * @param keyword
	 * @param fieldName
	 * @param rows
	 * @return
	 */
	List<ReplySearch> searchReply(String keyword, String[] fieldName, int rows);
}
