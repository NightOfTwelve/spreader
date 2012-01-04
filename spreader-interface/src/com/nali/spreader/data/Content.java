package com.nali.spreader.data;

import com.nali.common.model.BaseModel;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Content extends BaseModel implements Serializable {

	private static final long serialVersionUID = -29631703474189657L;

	public static final Integer TYPE_WEIBO = 1;
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 网站名称
	 */
	private String webSiteName;
	/**
	 * 网站分类标签
	 */
	private String categoryNames;

	private List<Category> categorys;

	private String typeName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column spreader.tb_content.id
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	private Long id;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column spreader.tb_content.type
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	private Integer type;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column spreader.tb_content.website_id
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	private Integer websiteId;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column spreader.tb_content.website_content_id
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	private Long websiteContentId;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column spreader.tb_content.website_ref_id
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	private Long websiteRefId;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column spreader.tb_content.website_uid
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	private Long websiteUid;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column spreader.tb_content.uid
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	private Long uid;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column spreader.tb_content.title
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	private String title;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column spreader.tb_content.pub_date
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	private Date pubDate;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column spreader.tb_content.sync_date
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	private Date syncDate;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column spreader.tb_content.ref_count
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	private Integer refCount;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column spreader.tb_content.reply_count
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	private Integer replyCount;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column spreader.tb_content.entry
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	private String entry;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column spreader.tb_content.content
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	private String content;

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column spreader.tb_content.id
	 * 
	 * @return the value of spreader.tb_content.id
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public Long getId() {
		return id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column spreader.tb_content.id
	 * 
	 * @param id
	 *            the value for spreader.tb_content.id
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(String categoryNames) {
		this.categoryNames = categoryNames;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column spreader.tb_content.type
	 * 
	 * @return the value of spreader.tb_content.type
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column spreader.tb_content.type
	 * 
	 * @param type
	 *            the value for spreader.tb_content.type
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column spreader.tb_content.website_id
	 * 
	 * @return the value of spreader.tb_content.website_id
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public Integer getWebsiteId() {
		return websiteId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column spreader.tb_content.website_id
	 * 
	 * @param websiteId
	 *            the value for spreader.tb_content.website_id
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public void setWebsiteId(Integer websiteId) {
		this.websiteId = websiteId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column spreader.tb_content.website_content_id
	 * 
	 * @return the value of spreader.tb_content.website_content_id
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public Long getWebsiteContentId() {
		return websiteContentId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column spreader.tb_content.website_content_id
	 * 
	 * @param websiteContentId
	 *            the value for spreader.tb_content.website_content_id
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public void setWebsiteContentId(Long websiteContentId) {
		this.websiteContentId = websiteContentId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column spreader.tb_content.website_ref_id
	 * 
	 * @return the value of spreader.tb_content.website_ref_id
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public Long getWebsiteRefId() {
		return websiteRefId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column spreader.tb_content.website_ref_id
	 * 
	 * @param websiteRefId
	 *            the value for spreader.tb_content.website_ref_id
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public void setWebsiteRefId(Long websiteRefId) {
		this.websiteRefId = websiteRefId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column spreader.tb_content.website_uid
	 * 
	 * @return the value of spreader.tb_content.website_uid
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public Long getWebsiteUid() {
		return websiteUid;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column spreader.tb_content.website_uid
	 * 
	 * @param websiteUid
	 *            the value for spreader.tb_content.website_uid
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public void setWebsiteUid(Long websiteUid) {
		this.websiteUid = websiteUid;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column spreader.tb_content.uid
	 * 
	 * @return the value of spreader.tb_content.uid
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public Long getUid() {
		return uid;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column spreader.tb_content.uid
	 * 
	 * @param uid
	 *            the value for spreader.tb_content.uid
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column spreader.tb_content.title
	 * 
	 * @return the value of spreader.tb_content.title
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column spreader.tb_content.title
	 * 
	 * @param title
	 *            the value for spreader.tb_content.title
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column spreader.tb_content.pub_date
	 * 
	 * @return the value of spreader.tb_content.pub_date
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public Date getPubDate() {
		return pubDate;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column spreader.tb_content.pub_date
	 * 
	 * @param pubDate
	 *            the value for spreader.tb_content.pub_date
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column spreader.tb_content.sync_date
	 * 
	 * @return the value of spreader.tb_content.sync_date
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public Date getSyncDate() {
		return syncDate;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column spreader.tb_content.sync_date
	 * 
	 * @param syncDate
	 *            the value for spreader.tb_content.sync_date
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public void setSyncDate(Date syncDate) {
		this.syncDate = syncDate;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column spreader.tb_content.ref_count
	 * 
	 * @return the value of spreader.tb_content.ref_count
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public Integer getRefCount() {
		return refCount;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column spreader.tb_content.ref_count
	 * 
	 * @param refCount
	 *            the value for spreader.tb_content.ref_count
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public void setRefCount(Integer refCount) {
		this.refCount = refCount;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column spreader.tb_content.reply_count
	 * 
	 * @return the value of spreader.tb_content.reply_count
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public Integer getReplyCount() {
		return replyCount;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column spreader.tb_content.reply_count
	 * 
	 * @param replyCount
	 *            the value for spreader.tb_content.reply_count
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	public List<Category> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<Category> categorys) {
		this.categorys = categorys;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column spreader.tb_content.entry
	 * 
	 * @return the value of spreader.tb_content.entry
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public String getEntry() {
		return entry;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column spreader.tb_content.entry
	 * 
	 * @param entry
	 *            the value for spreader.tb_content.entry
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public void setEntry(String entry) {
		this.entry = entry;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column spreader.tb_content.content
	 * 
	 * @return the value of spreader.tb_content.content
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public String getContent() {
		return content;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column spreader.tb_content.content
	 * 
	 * @param content
	 *            the value for spreader.tb_content.content
	 * 
	 * @ibatorgenerated Wed Dec 07 16:23:50 CST 2011
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public String getWebSiteName() {
		return webSiteName;
	}

	public void setWebSiteName(String webSiteName) {
		this.webSiteName = webSiteName;
	}
}