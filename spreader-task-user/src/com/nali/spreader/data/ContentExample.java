package com.nali.spreader.data;

import com.nali.common.model.Limit;
import com.nali.common.model.Shard;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentExample {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public ContentExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    protected ContentExample(ContentExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
        this.limit = example.limit;
        this.shard = example.shard;
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Thu Oct 27 13:53:09 CST 2011
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Thu Oct 27 13:53:09 CST 2011
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Thu Oct 27 13:53:09 CST 2011
     */
    private Shard shard;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Thu Oct 27 13:53:09 CST 2011
     */
    private Limit limit;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Thu Oct 27 13:53:09 CST 2011
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Thu Oct 27 13:53:09 CST 2011
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Thu Oct 27 13:53:09 CST 2011
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Thu Oct 27 13:53:09 CST 2011
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Thu Oct 27 13:53:09 CST 2011
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Thu Oct 27 13:53:09 CST 2011
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Thu Oct 27 13:53:09 CST 2011
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Thu Oct 27 13:53:09 CST 2011
     */
    public void setShard(Shard shard) {
        this.shard = shard;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Thu Oct 27 13:53:09 CST 2011
     */
    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Thu Oct 27 13:53:09 CST 2011
     */
    public static class Criteria {

        protected List<String> criteriaWithoutValue;

        protected List<Map<String, Object>> criteriaWithSingleValue;

        protected List<Map<String, Object>> criteriaWithListValue;

        protected List<Map<String, Object>> criteriaWithBetweenValue;

        protected Criteria() {
            super();
            criteriaWithoutValue = new ArrayList<String>();
            criteriaWithSingleValue = new ArrayList<Map<String, Object>>();
            criteriaWithListValue = new ArrayList<Map<String, Object>>();
            criteriaWithBetweenValue = new ArrayList<Map<String, Object>>();
        }

        public boolean isValid() {
            return criteriaWithoutValue.size() > 0 || criteriaWithSingleValue.size() > 0 || criteriaWithListValue.size() > 0 || criteriaWithBetweenValue.size() > 0;
        }

        public List<String> getCriteriaWithoutValue() {
            return criteriaWithoutValue;
        }

        public List<Map<String, Object>> getCriteriaWithSingleValue() {
            return criteriaWithSingleValue;
        }

        public List<Map<String, Object>> getCriteriaWithListValue() {
            return criteriaWithListValue;
        }

        public List<Map<String, Object>> getCriteriaWithBetweenValue() {
            return criteriaWithBetweenValue;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteriaWithoutValue.add(condition);
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List<? extends Object> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            List<Object> list = new ArrayList<Object>();
            list.add(value1);
            list.add(value2);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenValue.add(map);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return this;
        }

        public Criteria andWebsiteIdIsNull() {
            addCriterion("website_id is null");
            return this;
        }

        public Criteria andWebsiteIdIsNotNull() {
            addCriterion("website_id is not null");
            return this;
        }

        public Criteria andWebsiteIdEqualTo(Integer value) {
            addCriterion("website_id =", value, "websiteId");
            return this;
        }

        public Criteria andWebsiteIdNotEqualTo(Integer value) {
            addCriterion("website_id <>", value, "websiteId");
            return this;
        }

        public Criteria andWebsiteIdGreaterThan(Integer value) {
            addCriterion("website_id >", value, "websiteId");
            return this;
        }

        public Criteria andWebsiteIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("website_id >=", value, "websiteId");
            return this;
        }

        public Criteria andWebsiteIdLessThan(Integer value) {
            addCriterion("website_id <", value, "websiteId");
            return this;
        }

        public Criteria andWebsiteIdLessThanOrEqualTo(Integer value) {
            addCriterion("website_id <=", value, "websiteId");
            return this;
        }

        public Criteria andWebsiteIdIn(List<Integer> values) {
            addCriterion("website_id in", values, "websiteId");
            return this;
        }

        public Criteria andWebsiteIdNotIn(List<Integer> values) {
            addCriterion("website_id not in", values, "websiteId");
            return this;
        }

        public Criteria andWebsiteIdBetween(Integer value1, Integer value2) {
            addCriterion("website_id between", value1, value2, "websiteId");
            return this;
        }

        public Criteria andWebsiteIdNotBetween(Integer value1, Integer value2) {
            addCriterion("website_id not between", value1, value2, "websiteId");
            return this;
        }

        public Criteria andWebsiteContentIdIsNull() {
            addCriterion("website_content_id is null");
            return this;
        }

        public Criteria andWebsiteContentIdIsNotNull() {
            addCriterion("website_content_id is not null");
            return this;
        }

        public Criteria andWebsiteContentIdEqualTo(Long value) {
            addCriterion("website_content_id =", value, "websiteContentId");
            return this;
        }

        public Criteria andWebsiteContentIdNotEqualTo(Long value) {
            addCriterion("website_content_id <>", value, "websiteContentId");
            return this;
        }

        public Criteria andWebsiteContentIdGreaterThan(Long value) {
            addCriterion("website_content_id >", value, "websiteContentId");
            return this;
        }

        public Criteria andWebsiteContentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("website_content_id >=", value, "websiteContentId");
            return this;
        }

        public Criteria andWebsiteContentIdLessThan(Long value) {
            addCriterion("website_content_id <", value, "websiteContentId");
            return this;
        }

        public Criteria andWebsiteContentIdLessThanOrEqualTo(Long value) {
            addCriterion("website_content_id <=", value, "websiteContentId");
            return this;
        }

        public Criteria andWebsiteContentIdIn(List<Long> values) {
            addCriterion("website_content_id in", values, "websiteContentId");
            return this;
        }

        public Criteria andWebsiteContentIdNotIn(List<Long> values) {
            addCriterion("website_content_id not in", values, "websiteContentId");
            return this;
        }

        public Criteria andWebsiteContentIdBetween(Long value1, Long value2) {
            addCriterion("website_content_id between", value1, value2, "websiteContentId");
            return this;
        }

        public Criteria andWebsiteContentIdNotBetween(Long value1, Long value2) {
            addCriterion("website_content_id not between", value1, value2, "websiteContentId");
            return this;
        }

        public Criteria andWebsiteRefIdIsNull() {
            addCriterion("website_ref_id is null");
            return this;
        }

        public Criteria andWebsiteRefIdIsNotNull() {
            addCriterion("website_ref_id is not null");
            return this;
        }

        public Criteria andWebsiteRefIdEqualTo(Long value) {
            addCriterion("website_ref_id =", value, "websiteRefId");
            return this;
        }

        public Criteria andWebsiteRefIdNotEqualTo(Long value) {
            addCriterion("website_ref_id <>", value, "websiteRefId");
            return this;
        }

        public Criteria andWebsiteRefIdGreaterThan(Long value) {
            addCriterion("website_ref_id >", value, "websiteRefId");
            return this;
        }

        public Criteria andWebsiteRefIdGreaterThanOrEqualTo(Long value) {
            addCriterion("website_ref_id >=", value, "websiteRefId");
            return this;
        }

        public Criteria andWebsiteRefIdLessThan(Long value) {
            addCriterion("website_ref_id <", value, "websiteRefId");
            return this;
        }

        public Criteria andWebsiteRefIdLessThanOrEqualTo(Long value) {
            addCriterion("website_ref_id <=", value, "websiteRefId");
            return this;
        }

        public Criteria andWebsiteRefIdIn(List<Long> values) {
            addCriterion("website_ref_id in", values, "websiteRefId");
            return this;
        }

        public Criteria andWebsiteRefIdNotIn(List<Long> values) {
            addCriterion("website_ref_id not in", values, "websiteRefId");
            return this;
        }

        public Criteria andWebsiteRefIdBetween(Long value1, Long value2) {
            addCriterion("website_ref_id between", value1, value2, "websiteRefId");
            return this;
        }

        public Criteria andWebsiteRefIdNotBetween(Long value1, Long value2) {
            addCriterion("website_ref_id not between", value1, value2, "websiteRefId");
            return this;
        }

        public Criteria andWebsiteUidIsNull() {
            addCriterion("website_uid is null");
            return this;
        }

        public Criteria andWebsiteUidIsNotNull() {
            addCriterion("website_uid is not null");
            return this;
        }

        public Criteria andWebsiteUidEqualTo(Long value) {
            addCriterion("website_uid =", value, "websiteUid");
            return this;
        }

        public Criteria andWebsiteUidNotEqualTo(Long value) {
            addCriterion("website_uid <>", value, "websiteUid");
            return this;
        }

        public Criteria andWebsiteUidGreaterThan(Long value) {
            addCriterion("website_uid >", value, "websiteUid");
            return this;
        }

        public Criteria andWebsiteUidGreaterThanOrEqualTo(Long value) {
            addCriterion("website_uid >=", value, "websiteUid");
            return this;
        }

        public Criteria andWebsiteUidLessThan(Long value) {
            addCriterion("website_uid <", value, "websiteUid");
            return this;
        }

        public Criteria andWebsiteUidLessThanOrEqualTo(Long value) {
            addCriterion("website_uid <=", value, "websiteUid");
            return this;
        }

        public Criteria andWebsiteUidIn(List<Long> values) {
            addCriterion("website_uid in", values, "websiteUid");
            return this;
        }

        public Criteria andWebsiteUidNotIn(List<Long> values) {
            addCriterion("website_uid not in", values, "websiteUid");
            return this;
        }

        public Criteria andWebsiteUidBetween(Long value1, Long value2) {
            addCriterion("website_uid between", value1, value2, "websiteUid");
            return this;
        }

        public Criteria andWebsiteUidNotBetween(Long value1, Long value2) {
            addCriterion("website_uid not between", value1, value2, "websiteUid");
            return this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return this;
        }

        public Criteria andUidEqualTo(Long value) {
            addCriterion("uid =", value, "uid");
            return this;
        }

        public Criteria andUidNotEqualTo(Long value) {
            addCriterion("uid <>", value, "uid");
            return this;
        }

        public Criteria andUidGreaterThan(Long value) {
            addCriterion("uid >", value, "uid");
            return this;
        }

        public Criteria andUidGreaterThanOrEqualTo(Long value) {
            addCriterion("uid >=", value, "uid");
            return this;
        }

        public Criteria andUidLessThan(Long value) {
            addCriterion("uid <", value, "uid");
            return this;
        }

        public Criteria andUidLessThanOrEqualTo(Long value) {
            addCriterion("uid <=", value, "uid");
            return this;
        }

        public Criteria andUidIn(List<Long> values) {
            addCriterion("uid in", values, "uid");
            return this;
        }

        public Criteria andUidNotIn(List<Long> values) {
            addCriterion("uid not in", values, "uid");
            return this;
        }

        public Criteria andUidBetween(Long value1, Long value2) {
            addCriterion("uid between", value1, value2, "uid");
            return this;
        }

        public Criteria andUidNotBetween(Long value1, Long value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return this;
        }

        public Criteria andPubDateIsNull() {
            addCriterion("pub_date is null");
            return this;
        }

        public Criteria andPubDateIsNotNull() {
            addCriterion("pub_date is not null");
            return this;
        }

        public Criteria andPubDateEqualTo(Date value) {
            addCriterion("pub_date =", value, "pubDate");
            return this;
        }

        public Criteria andPubDateNotEqualTo(Date value) {
            addCriterion("pub_date <>", value, "pubDate");
            return this;
        }

        public Criteria andPubDateGreaterThan(Date value) {
            addCriterion("pub_date >", value, "pubDate");
            return this;
        }

        public Criteria andPubDateGreaterThanOrEqualTo(Date value) {
            addCriterion("pub_date >=", value, "pubDate");
            return this;
        }

        public Criteria andPubDateLessThan(Date value) {
            addCriterion("pub_date <", value, "pubDate");
            return this;
        }

        public Criteria andPubDateLessThanOrEqualTo(Date value) {
            addCriterion("pub_date <=", value, "pubDate");
            return this;
        }

        public Criteria andPubDateIn(List<Date> values) {
            addCriterion("pub_date in", values, "pubDate");
            return this;
        }

        public Criteria andPubDateNotIn(List<Date> values) {
            addCriterion("pub_date not in", values, "pubDate");
            return this;
        }

        public Criteria andPubDateBetween(Date value1, Date value2) {
            addCriterion("pub_date between", value1, value2, "pubDate");
            return this;
        }

        public Criteria andPubDateNotBetween(Date value1, Date value2) {
            addCriterion("pub_date not between", value1, value2, "pubDate");
            return this;
        }

        public Criteria andSyncDateIsNull() {
            addCriterion("sync_date is null");
            return this;
        }

        public Criteria andSyncDateIsNotNull() {
            addCriterion("sync_date is not null");
            return this;
        }

        public Criteria andSyncDateEqualTo(Date value) {
            addCriterion("sync_date =", value, "syncDate");
            return this;
        }

        public Criteria andSyncDateNotEqualTo(Date value) {
            addCriterion("sync_date <>", value, "syncDate");
            return this;
        }

        public Criteria andSyncDateGreaterThan(Date value) {
            addCriterion("sync_date >", value, "syncDate");
            return this;
        }

        public Criteria andSyncDateGreaterThanOrEqualTo(Date value) {
            addCriterion("sync_date >=", value, "syncDate");
            return this;
        }

        public Criteria andSyncDateLessThan(Date value) {
            addCriterion("sync_date <", value, "syncDate");
            return this;
        }

        public Criteria andSyncDateLessThanOrEqualTo(Date value) {
            addCriterion("sync_date <=", value, "syncDate");
            return this;
        }

        public Criteria andSyncDateIn(List<Date> values) {
            addCriterion("sync_date in", values, "syncDate");
            return this;
        }

        public Criteria andSyncDateNotIn(List<Date> values) {
            addCriterion("sync_date not in", values, "syncDate");
            return this;
        }

        public Criteria andSyncDateBetween(Date value1, Date value2) {
            addCriterion("sync_date between", value1, value2, "syncDate");
            return this;
        }

        public Criteria andSyncDateNotBetween(Date value1, Date value2) {
            addCriterion("sync_date not between", value1, value2, "syncDate");
            return this;
        }

        public Criteria andRefCountIsNull() {
            addCriterion("ref_count is null");
            return this;
        }

        public Criteria andRefCountIsNotNull() {
            addCriterion("ref_count is not null");
            return this;
        }

        public Criteria andRefCountEqualTo(Integer value) {
            addCriterion("ref_count =", value, "refCount");
            return this;
        }

        public Criteria andRefCountNotEqualTo(Integer value) {
            addCriterion("ref_count <>", value, "refCount");
            return this;
        }

        public Criteria andRefCountGreaterThan(Integer value) {
            addCriterion("ref_count >", value, "refCount");
            return this;
        }

        public Criteria andRefCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("ref_count >=", value, "refCount");
            return this;
        }

        public Criteria andRefCountLessThan(Integer value) {
            addCriterion("ref_count <", value, "refCount");
            return this;
        }

        public Criteria andRefCountLessThanOrEqualTo(Integer value) {
            addCriterion("ref_count <=", value, "refCount");
            return this;
        }

        public Criteria andRefCountIn(List<Integer> values) {
            addCriterion("ref_count in", values, "refCount");
            return this;
        }

        public Criteria andRefCountNotIn(List<Integer> values) {
            addCriterion("ref_count not in", values, "refCount");
            return this;
        }

        public Criteria andRefCountBetween(Integer value1, Integer value2) {
            addCriterion("ref_count between", value1, value2, "refCount");
            return this;
        }

        public Criteria andRefCountNotBetween(Integer value1, Integer value2) {
            addCriterion("ref_count not between", value1, value2, "refCount");
            return this;
        }

        public Criteria andReplyCountIsNull() {
            addCriterion("reply_count is null");
            return this;
        }

        public Criteria andReplyCountIsNotNull() {
            addCriterion("reply_count is not null");
            return this;
        }

        public Criteria andReplyCountEqualTo(Integer value) {
            addCriterion("reply_count =", value, "replyCount");
            return this;
        }

        public Criteria andReplyCountNotEqualTo(Integer value) {
            addCriterion("reply_count <>", value, "replyCount");
            return this;
        }

        public Criteria andReplyCountGreaterThan(Integer value) {
            addCriterion("reply_count >", value, "replyCount");
            return this;
        }

        public Criteria andReplyCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("reply_count >=", value, "replyCount");
            return this;
        }

        public Criteria andReplyCountLessThan(Integer value) {
            addCriterion("reply_count <", value, "replyCount");
            return this;
        }

        public Criteria andReplyCountLessThanOrEqualTo(Integer value) {
            addCriterion("reply_count <=", value, "replyCount");
            return this;
        }

        public Criteria andReplyCountIn(List<Integer> values) {
            addCriterion("reply_count in", values, "replyCount");
            return this;
        }

        public Criteria andReplyCountNotIn(List<Integer> values) {
            addCriterion("reply_count not in", values, "replyCount");
            return this;
        }

        public Criteria andReplyCountBetween(Integer value1, Integer value2) {
            addCriterion("reply_count between", value1, value2, "replyCount");
            return this;
        }

        public Criteria andReplyCountNotBetween(Integer value1, Integer value2) {
            addCriterion("reply_count not between", value1, value2, "replyCount");
            return this;
        }
    }
}
