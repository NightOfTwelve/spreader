package com.nali.spreader.model;

import com.nali.common.model.Limit;
import com.nali.common.model.Shard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegularJobExample {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_regular_job
     *
     * @ibatorgenerated Sat Nov 12 17:38:19 CST 2011
     */
    public RegularJobExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_regular_job
     *
     * @ibatorgenerated Sat Nov 12 17:38:19 CST 2011
     */
    protected RegularJobExample(RegularJobExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
        this.limit = example.limit;
        this.shard = example.shard;
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_regular_job
     *
     * @ibatorgenerated Thu Jan 05 15:59:03 CST 2012
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_regular_job
     *
     * @ibatorgenerated Thu Jan 05 15:59:03 CST 2012
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_regular_job
     *
     * @ibatorgenerated Thu Jan 05 15:59:03 CST 2012
     */
    private Shard shard;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_regular_job
     *
     * @ibatorgenerated Thu Jan 05 15:59:03 CST 2012
     */
    private Limit limit;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_regular_job
     *
     * @ibatorgenerated Thu Jan 05 15:59:03 CST 2012
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_regular_job
     *
     * @ibatorgenerated Thu Jan 05 15:59:03 CST 2012
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_regular_job
     *
     * @ibatorgenerated Thu Jan 05 15:59:03 CST 2012
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_regular_job
     *
     * @ibatorgenerated Thu Jan 05 15:59:03 CST 2012
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_regular_job
     *
     * @ibatorgenerated Thu Jan 05 15:59:03 CST 2012
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
     * This method corresponds to the database table spreader.tb_regular_job
     *
     * @ibatorgenerated Thu Jan 05 15:59:03 CST 2012
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_regular_job
     *
     * @ibatorgenerated Thu Jan 05 15:59:03 CST 2012
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_regular_job
     *
     * @ibatorgenerated Thu Jan 05 15:59:03 CST 2012
     */
    public void setShard(Shard shard) {
        this.shard = shard;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_regular_job
     *
     * @ibatorgenerated Thu Jan 05 15:59:03 CST 2012
     */
    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_regular_job
     *
     * @ibatorgenerated Thu Jan 05 15:59:03 CST 2012
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return this;
        }

        public Criteria andTriggerTypeIsNull() {
            addCriterion("trigger_type is null");
            return this;
        }

        public Criteria andTriggerTypeIsNotNull() {
            addCriterion("trigger_type is not null");
            return this;
        }

        public Criteria andTriggerTypeEqualTo(Integer value) {
            addCriterion("trigger_type =", value, "triggerType");
            return this;
        }

        public Criteria andTriggerTypeNotEqualTo(Integer value) {
            addCriterion("trigger_type <>", value, "triggerType");
            return this;
        }

        public Criteria andTriggerTypeGreaterThan(Integer value) {
            addCriterion("trigger_type >", value, "triggerType");
            return this;
        }

        public Criteria andTriggerTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("trigger_type >=", value, "triggerType");
            return this;
        }

        public Criteria andTriggerTypeLessThan(Integer value) {
            addCriterion("trigger_type <", value, "triggerType");
            return this;
        }

        public Criteria andTriggerTypeLessThanOrEqualTo(Integer value) {
            addCriterion("trigger_type <=", value, "triggerType");
            return this;
        }

        public Criteria andTriggerTypeIn(List<Integer> values) {
            addCriterion("trigger_type in", values, "triggerType");
            return this;
        }

        public Criteria andTriggerTypeNotIn(List<Integer> values) {
            addCriterion("trigger_type not in", values, "triggerType");
            return this;
        }

        public Criteria andTriggerTypeBetween(Integer value1, Integer value2) {
            addCriterion("trigger_type between", value1, value2, "triggerType");
            return this;
        }

        public Criteria andTriggerTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("trigger_type not between", value1, value2, "triggerType");
            return this;
        }

        public Criteria andTriggerInfoIsNull() {
            addCriterion("trigger_info is null");
            return this;
        }

        public Criteria andTriggerInfoIsNotNull() {
            addCriterion("trigger_info is not null");
            return this;
        }

        public Criteria andTriggerInfoEqualTo(String value) {
            addCriterion("trigger_info =", value, "triggerInfo");
            return this;
        }

        public Criteria andTriggerInfoNotEqualTo(String value) {
            addCriterion("trigger_info <>", value, "triggerInfo");
            return this;
        }

        public Criteria andTriggerInfoGreaterThan(String value) {
            addCriterion("trigger_info >", value, "triggerInfo");
            return this;
        }

        public Criteria andTriggerInfoGreaterThanOrEqualTo(String value) {
            addCriterion("trigger_info >=", value, "triggerInfo");
            return this;
        }

        public Criteria andTriggerInfoLessThan(String value) {
            addCriterion("trigger_info <", value, "triggerInfo");
            return this;
        }

        public Criteria andTriggerInfoLessThanOrEqualTo(String value) {
            addCriterion("trigger_info <=", value, "triggerInfo");
            return this;
        }

        public Criteria andTriggerInfoLike(String value) {
            addCriterion("trigger_info like", value, "triggerInfo");
            return this;
        }

        public Criteria andTriggerInfoNotLike(String value) {
            addCriterion("trigger_info not like", value, "triggerInfo");
            return this;
        }

        public Criteria andTriggerInfoIn(List<String> values) {
            addCriterion("trigger_info in", values, "triggerInfo");
            return this;
        }

        public Criteria andTriggerInfoNotIn(List<String> values) {
            addCriterion("trigger_info not in", values, "triggerInfo");
            return this;
        }

        public Criteria andTriggerInfoBetween(String value1, String value2) {
            addCriterion("trigger_info between", value1, value2, "triggerInfo");
            return this;
        }

        public Criteria andTriggerInfoNotBetween(String value1, String value2) {
            addCriterion("trigger_info not between", value1, value2, "triggerInfo");
            return this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return this;
        }

        public Criteria andJobTypeIsNull() {
            addCriterion("job_type is null");
            return this;
        }

        public Criteria andJobTypeIsNotNull() {
            addCriterion("job_type is not null");
            return this;
        }

        public Criteria andJobTypeEqualTo(Integer value) {
            addCriterion("job_type =", value, "jobType");
            return this;
        }

        public Criteria andJobTypeNotEqualTo(Integer value) {
            addCriterion("job_type <>", value, "jobType");
            return this;
        }

        public Criteria andJobTypeGreaterThan(Integer value) {
            addCriterion("job_type >", value, "jobType");
            return this;
        }

        public Criteria andJobTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("job_type >=", value, "jobType");
            return this;
        }

        public Criteria andJobTypeLessThan(Integer value) {
            addCriterion("job_type <", value, "jobType");
            return this;
        }

        public Criteria andJobTypeLessThanOrEqualTo(Integer value) {
            addCriterion("job_type <=", value, "jobType");
            return this;
        }

        public Criteria andJobTypeIn(List<Integer> values) {
            addCriterion("job_type in", values, "jobType");
            return this;
        }

        public Criteria andJobTypeNotIn(List<Integer> values) {
            addCriterion("job_type not in", values, "jobType");
            return this;
        }

        public Criteria andJobTypeBetween(Integer value1, Integer value2) {
            addCriterion("job_type between", value1, value2, "jobType");
            return this;
        }

        public Criteria andJobTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("job_type not between", value1, value2, "jobType");
            return this;
        }

        public Criteria andGidIsNull() {
            addCriterion("gid is null");
            return this;
        }

        public Criteria andGidIsNotNull() {
            addCriterion("gid is not null");
            return this;
        }

        public Criteria andGidEqualTo(Long value) {
            addCriterion("gid =", value, "gid");
            return this;
        }

        public Criteria andGidNotEqualTo(Long value) {
            addCriterion("gid <>", value, "gid");
            return this;
        }

        public Criteria andGidGreaterThan(Long value) {
            addCriterion("gid >", value, "gid");
            return this;
        }

        public Criteria andGidGreaterThanOrEqualTo(Long value) {
            addCriterion("gid >=", value, "gid");
            return this;
        }

        public Criteria andGidLessThan(Long value) {
            addCriterion("gid <", value, "gid");
            return this;
        }

        public Criteria andGidLessThanOrEqualTo(Long value) {
            addCriterion("gid <=", value, "gid");
            return this;
        }

        public Criteria andGidIn(List<Long> values) {
            addCriterion("gid in", values, "gid");
            return this;
        }

        public Criteria andGidNotIn(List<Long> values) {
            addCriterion("gid not in", values, "gid");
            return this;
        }

        public Criteria andGidBetween(Long value1, Long value2) {
            addCriterion("gid between", value1, value2, "gid");
            return this;
        }

        public Criteria andGidNotBetween(Long value1, Long value2) {
            addCriterion("gid not between", value1, value2, "gid");
            return this;
        }
    }
}
