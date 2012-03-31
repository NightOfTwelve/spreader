package com.nali.spreader.model;

import com.nali.common.model.Limit;
import com.nali.common.model.Shard;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientErrorExample {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_error
     *
     * @ibatorgenerated Thu Mar 08 18:39:02 CST 2012
     */
    public ClientErrorExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_error
     *
     * @ibatorgenerated Thu Mar 08 18:39:02 CST 2012
     */
    protected ClientErrorExample(ClientErrorExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
        this.limit = example.limit;
        this.shard = example.shard;
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_client_error
     *
     * @ibatorgenerated Fri Mar 30 16:48:07 CST 2012
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_client_error
     *
     * @ibatorgenerated Fri Mar 30 16:48:07 CST 2012
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_client_error
     *
     * @ibatorgenerated Fri Mar 30 16:48:07 CST 2012
     */
    private Shard shard;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_client_error
     *
     * @ibatorgenerated Fri Mar 30 16:48:07 CST 2012
     */
    private Limit limit;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_error
     *
     * @ibatorgenerated Fri Mar 30 16:48:07 CST 2012
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_error
     *
     * @ibatorgenerated Fri Mar 30 16:48:07 CST 2012
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_error
     *
     * @ibatorgenerated Fri Mar 30 16:48:07 CST 2012
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_error
     *
     * @ibatorgenerated Fri Mar 30 16:48:07 CST 2012
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_error
     *
     * @ibatorgenerated Fri Mar 30 16:48:07 CST 2012
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
     * This method corresponds to the database table spreader.tb_client_error
     *
     * @ibatorgenerated Fri Mar 30 16:48:07 CST 2012
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_error
     *
     * @ibatorgenerated Fri Mar 30 16:48:07 CST 2012
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_error
     *
     * @ibatorgenerated Fri Mar 30 16:48:07 CST 2012
     */
    public void setShard(Shard shard) {
        this.shard = shard;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_error
     *
     * @ibatorgenerated Fri Mar 30 16:48:07 CST 2012
     */
    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_client_error
     *
     * @ibatorgenerated Fri Mar 30 16:48:07 CST 2012
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

        public Criteria andTaskIdIsNull() {
            addCriterion("task_id is null");
            return this;
        }

        public Criteria andTaskIdIsNotNull() {
            addCriterion("task_id is not null");
            return this;
        }

        public Criteria andTaskIdEqualTo(Long value) {
            addCriterion("task_id =", value, "taskId");
            return this;
        }

        public Criteria andTaskIdNotEqualTo(Long value) {
            addCriterion("task_id <>", value, "taskId");
            return this;
        }

        public Criteria andTaskIdGreaterThan(Long value) {
            addCriterion("task_id >", value, "taskId");
            return this;
        }

        public Criteria andTaskIdGreaterThanOrEqualTo(Long value) {
            addCriterion("task_id >=", value, "taskId");
            return this;
        }

        public Criteria andTaskIdLessThan(Long value) {
            addCriterion("task_id <", value, "taskId");
            return this;
        }

        public Criteria andTaskIdLessThanOrEqualTo(Long value) {
            addCriterion("task_id <=", value, "taskId");
            return this;
        }

        public Criteria andTaskIdIn(List<Long> values) {
            addCriterion("task_id in", values, "taskId");
            return this;
        }

        public Criteria andTaskIdNotIn(List<Long> values) {
            addCriterion("task_id not in", values, "taskId");
            return this;
        }

        public Criteria andTaskIdBetween(Long value1, Long value2) {
            addCriterion("task_id between", value1, value2, "taskId");
            return this;
        }

        public Criteria andTaskIdNotBetween(Long value1, Long value2) {
            addCriterion("task_id not between", value1, value2, "taskId");
            return this;
        }

        public Criteria andClientIdIsNull() {
            addCriterion("client_id is null");
            return this;
        }

        public Criteria andClientIdIsNotNull() {
            addCriterion("client_id is not null");
            return this;
        }

        public Criteria andClientIdEqualTo(Long value) {
            addCriterion("client_id =", value, "clientId");
            return this;
        }

        public Criteria andClientIdNotEqualTo(Long value) {
            addCriterion("client_id <>", value, "clientId");
            return this;
        }

        public Criteria andClientIdGreaterThan(Long value) {
            addCriterion("client_id >", value, "clientId");
            return this;
        }

        public Criteria andClientIdGreaterThanOrEqualTo(Long value) {
            addCriterion("client_id >=", value, "clientId");
            return this;
        }

        public Criteria andClientIdLessThan(Long value) {
            addCriterion("client_id <", value, "clientId");
            return this;
        }

        public Criteria andClientIdLessThanOrEqualTo(Long value) {
            addCriterion("client_id <=", value, "clientId");
            return this;
        }

        public Criteria andClientIdIn(List<Long> values) {
            addCriterion("client_id in", values, "clientId");
            return this;
        }

        public Criteria andClientIdNotIn(List<Long> values) {
            addCriterion("client_id not in", values, "clientId");
            return this;
        }

        public Criteria andClientIdBetween(Long value1, Long value2) {
            addCriterion("client_id between", value1, value2, "clientId");
            return this;
        }

        public Criteria andClientIdNotBetween(Long value1, Long value2) {
            addCriterion("client_id not between", value1, value2, "clientId");
            return this;
        }

        public Criteria andTaskCodeIsNull() {
            addCriterion("task_code is null");
            return this;
        }

        public Criteria andTaskCodeIsNotNull() {
            addCriterion("task_code is not null");
            return this;
        }

        public Criteria andTaskCodeEqualTo(String value) {
            addCriterion("task_code =", value, "taskCode");
            return this;
        }

        public Criteria andTaskCodeNotEqualTo(String value) {
            addCriterion("task_code <>", value, "taskCode");
            return this;
        }

        public Criteria andTaskCodeGreaterThan(String value) {
            addCriterion("task_code >", value, "taskCode");
            return this;
        }

        public Criteria andTaskCodeGreaterThanOrEqualTo(String value) {
            addCriterion("task_code >=", value, "taskCode");
            return this;
        }

        public Criteria andTaskCodeLessThan(String value) {
            addCriterion("task_code <", value, "taskCode");
            return this;
        }

        public Criteria andTaskCodeLessThanOrEqualTo(String value) {
            addCriterion("task_code <=", value, "taskCode");
            return this;
        }

        public Criteria andTaskCodeLike(String value) {
            addCriterion("task_code like", value, "taskCode");
            return this;
        }

        public Criteria andTaskCodeNotLike(String value) {
            addCriterion("task_code not like", value, "taskCode");
            return this;
        }

        public Criteria andTaskCodeIn(List<String> values) {
            addCriterion("task_code in", values, "taskCode");
            return this;
        }

        public Criteria andTaskCodeNotIn(List<String> values) {
            addCriterion("task_code not in", values, "taskCode");
            return this;
        }

        public Criteria andTaskCodeBetween(String value1, String value2) {
            addCriterion("task_code between", value1, value2, "taskCode");
            return this;
        }

        public Criteria andTaskCodeNotBetween(String value1, String value2) {
            addCriterion("task_code not between", value1, value2, "taskCode");
            return this;
        }

        public Criteria andErrorCodeIsNull() {
            addCriterion("error_code is null");
            return this;
        }

        public Criteria andErrorCodeIsNotNull() {
            addCriterion("error_code is not null");
            return this;
        }

        public Criteria andErrorCodeEqualTo(String value) {
            addCriterion("error_code =", value, "errorCode");
            return this;
        }

        public Criteria andErrorCodeNotEqualTo(String value) {
            addCriterion("error_code <>", value, "errorCode");
            return this;
        }

        public Criteria andErrorCodeGreaterThan(String value) {
            addCriterion("error_code >", value, "errorCode");
            return this;
        }

        public Criteria andErrorCodeGreaterThanOrEqualTo(String value) {
            addCriterion("error_code >=", value, "errorCode");
            return this;
        }

        public Criteria andErrorCodeLessThan(String value) {
            addCriterion("error_code <", value, "errorCode");
            return this;
        }

        public Criteria andErrorCodeLessThanOrEqualTo(String value) {
            addCriterion("error_code <=", value, "errorCode");
            return this;
        }

        public Criteria andErrorCodeLike(String value) {
            addCriterion("error_code like", value, "errorCode");
            return this;
        }

        public Criteria andErrorCodeNotLike(String value) {
            addCriterion("error_code not like", value, "errorCode");
            return this;
        }

        public Criteria andErrorCodeIn(List<String> values) {
            addCriterion("error_code in", values, "errorCode");
            return this;
        }

        public Criteria andErrorCodeNotIn(List<String> values) {
            addCriterion("error_code not in", values, "errorCode");
            return this;
        }

        public Criteria andErrorCodeBetween(String value1, String value2) {
            addCriterion("error_code between", value1, value2, "errorCode");
            return this;
        }

        public Criteria andErrorCodeNotBetween(String value1, String value2) {
            addCriterion("error_code not between", value1, value2, "errorCode");
            return this;
        }

        public Criteria andErrorTimeIsNull() {
            addCriterion("error_time is null");
            return this;
        }

        public Criteria andErrorTimeIsNotNull() {
            addCriterion("error_time is not null");
            return this;
        }

        public Criteria andErrorTimeEqualTo(Date value) {
            addCriterion("error_time =", value, "errorTime");
            return this;
        }

        public Criteria andErrorTimeNotEqualTo(Date value) {
            addCriterion("error_time <>", value, "errorTime");
            return this;
        }

        public Criteria andErrorTimeGreaterThan(Date value) {
            addCriterion("error_time >", value, "errorTime");
            return this;
        }

        public Criteria andErrorTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("error_time >=", value, "errorTime");
            return this;
        }

        public Criteria andErrorTimeLessThan(Date value) {
            addCriterion("error_time <", value, "errorTime");
            return this;
        }

        public Criteria andErrorTimeLessThanOrEqualTo(Date value) {
            addCriterion("error_time <=", value, "errorTime");
            return this;
        }

        public Criteria andErrorTimeIn(List<Date> values) {
            addCriterion("error_time in", values, "errorTime");
            return this;
        }

        public Criteria andErrorTimeNotIn(List<Date> values) {
            addCriterion("error_time not in", values, "errorTime");
            return this;
        }

        public Criteria andErrorTimeBetween(Date value1, Date value2) {
            addCriterion("error_time between", value1, value2, "errorTime");
            return this;
        }

        public Criteria andErrorTimeNotBetween(Date value1, Date value2) {
            addCriterion("error_time not between", value1, value2, "errorTime");
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

        public Criteria andWebsiteErrorCodeIsNull() {
            addCriterion("website_error_code is null");
            return this;
        }

        public Criteria andWebsiteErrorCodeIsNotNull() {
            addCriterion("website_error_code is not null");
            return this;
        }

        public Criteria andWebsiteErrorCodeEqualTo(String value) {
            addCriterion("website_error_code =", value, "websiteErrorCode");
            return this;
        }

        public Criteria andWebsiteErrorCodeNotEqualTo(String value) {
            addCriterion("website_error_code <>", value, "websiteErrorCode");
            return this;
        }

        public Criteria andWebsiteErrorCodeGreaterThan(String value) {
            addCriterion("website_error_code >", value, "websiteErrorCode");
            return this;
        }

        public Criteria andWebsiteErrorCodeGreaterThanOrEqualTo(String value) {
            addCriterion("website_error_code >=", value, "websiteErrorCode");
            return this;
        }

        public Criteria andWebsiteErrorCodeLessThan(String value) {
            addCriterion("website_error_code <", value, "websiteErrorCode");
            return this;
        }

        public Criteria andWebsiteErrorCodeLessThanOrEqualTo(String value) {
            addCriterion("website_error_code <=", value, "websiteErrorCode");
            return this;
        }

        public Criteria andWebsiteErrorCodeLike(String value) {
            addCriterion("website_error_code like", value, "websiteErrorCode");
            return this;
        }

        public Criteria andWebsiteErrorCodeNotLike(String value) {
            addCriterion("website_error_code not like", value, "websiteErrorCode");
            return this;
        }

        public Criteria andWebsiteErrorCodeIn(List<String> values) {
            addCriterion("website_error_code in", values, "websiteErrorCode");
            return this;
        }

        public Criteria andWebsiteErrorCodeNotIn(List<String> values) {
            addCriterion("website_error_code not in", values, "websiteErrorCode");
            return this;
        }

        public Criteria andWebsiteErrorCodeBetween(String value1, String value2) {
            addCriterion("website_error_code between", value1, value2, "websiteErrorCode");
            return this;
        }

        public Criteria andWebsiteErrorCodeNotBetween(String value1, String value2) {
            addCriterion("website_error_code not between", value1, value2, "websiteErrorCode");
            return this;
        }

        public Criteria andWebsiteErrorDescIsNull() {
            addCriterion("website_error_desc is null");
            return this;
        }

        public Criteria andWebsiteErrorDescIsNotNull() {
            addCriterion("website_error_desc is not null");
            return this;
        }

        public Criteria andWebsiteErrorDescEqualTo(String value) {
            addCriterion("website_error_desc =", value, "websiteErrorDesc");
            return this;
        }

        public Criteria andWebsiteErrorDescNotEqualTo(String value) {
            addCriterion("website_error_desc <>", value, "websiteErrorDesc");
            return this;
        }

        public Criteria andWebsiteErrorDescGreaterThan(String value) {
            addCriterion("website_error_desc >", value, "websiteErrorDesc");
            return this;
        }

        public Criteria andWebsiteErrorDescGreaterThanOrEqualTo(String value) {
            addCriterion("website_error_desc >=", value, "websiteErrorDesc");
            return this;
        }

        public Criteria andWebsiteErrorDescLessThan(String value) {
            addCriterion("website_error_desc <", value, "websiteErrorDesc");
            return this;
        }

        public Criteria andWebsiteErrorDescLessThanOrEqualTo(String value) {
            addCriterion("website_error_desc <=", value, "websiteErrorDesc");
            return this;
        }

        public Criteria andWebsiteErrorDescLike(String value) {
            addCriterion("website_error_desc like", value, "websiteErrorDesc");
            return this;
        }

        public Criteria andWebsiteErrorDescNotLike(String value) {
            addCriterion("website_error_desc not like", value, "websiteErrorDesc");
            return this;
        }

        public Criteria andWebsiteErrorDescIn(List<String> values) {
            addCriterion("website_error_desc in", values, "websiteErrorDesc");
            return this;
        }

        public Criteria andWebsiteErrorDescNotIn(List<String> values) {
            addCriterion("website_error_desc not in", values, "websiteErrorDesc");
            return this;
        }

        public Criteria andWebsiteErrorDescBetween(String value1, String value2) {
            addCriterion("website_error_desc between", value1, value2, "websiteErrorDesc");
            return this;
        }

        public Criteria andWebsiteErrorDescNotBetween(String value1, String value2) {
            addCriterion("website_error_desc not between", value1, value2, "websiteErrorDesc");
            return this;
        }

        public Criteria andRefererIdIsNull() {
            addCriterion("referer_id is null");
            return this;
        }

        public Criteria andRefererIdIsNotNull() {
            addCriterion("referer_id is not null");
            return this;
        }

        public Criteria andRefererIdEqualTo(Long value) {
            addCriterion("referer_id =", value, "refererId");
            return this;
        }

        public Criteria andRefererIdNotEqualTo(Long value) {
            addCriterion("referer_id <>", value, "refererId");
            return this;
        }

        public Criteria andRefererIdGreaterThan(Long value) {
            addCriterion("referer_id >", value, "refererId");
            return this;
        }

        public Criteria andRefererIdGreaterThanOrEqualTo(Long value) {
            addCriterion("referer_id >=", value, "refererId");
            return this;
        }

        public Criteria andRefererIdLessThan(Long value) {
            addCriterion("referer_id <", value, "refererId");
            return this;
        }

        public Criteria andRefererIdLessThanOrEqualTo(Long value) {
            addCriterion("referer_id <=", value, "refererId");
            return this;
        }

        public Criteria andRefererIdIn(List<Long> values) {
            addCriterion("referer_id in", values, "refererId");
            return this;
        }

        public Criteria andRefererIdNotIn(List<Long> values) {
            addCriterion("referer_id not in", values, "refererId");
            return this;
        }

        public Criteria andRefererIdBetween(Long value1, Long value2) {
            addCriterion("referer_id between", value1, value2, "refererId");
            return this;
        }

        public Criteria andRefererIdNotBetween(Long value1, Long value2) {
            addCriterion("referer_id not between", value1, value2, "refererId");
            return this;
        }
    }
}