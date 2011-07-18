package com.nali.spreader.model;

import com.nali.common.model.Limit;
import com.nali.common.model.Shard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionStepExample {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_action_step
     *
     * @ibatorgenerated Mon Jul 18 15:02:49 CST 2011
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_action_step
     *
     * @ibatorgenerated Mon Jul 18 15:02:49 CST 2011
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_action_step
     *
     * @ibatorgenerated Mon Jul 18 15:02:49 CST 2011
     */
    private Shard shard;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_action_step
     *
     * @ibatorgenerated Mon Jul 18 15:02:49 CST 2011
     */
    private Limit limit;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_action_step
     *
     * @ibatorgenerated Mon Jul 18 15:02:49 CST 2011
     */
    public ActionStepExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_action_step
     *
     * @ibatorgenerated Mon Jul 18 15:02:49 CST 2011
     */
    protected ActionStepExample(ActionStepExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
        this.limit = example.limit;
        this.shard = example.shard;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_action_step
     *
     * @ibatorgenerated Mon Jul 18 15:02:49 CST 2011
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_action_step
     *
     * @ibatorgenerated Mon Jul 18 15:02:49 CST 2011
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_action_step
     *
     * @ibatorgenerated Mon Jul 18 15:02:49 CST 2011
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_action_step
     *
     * @ibatorgenerated Mon Jul 18 15:02:49 CST 2011
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_action_step
     *
     * @ibatorgenerated Mon Jul 18 15:02:49 CST 2011
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
     * This method corresponds to the database table spreader.tb_action_step
     *
     * @ibatorgenerated Mon Jul 18 15:02:49 CST 2011
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_action_step
     *
     * @ibatorgenerated Mon Jul 18 15:02:49 CST 2011
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_action_step
     *
     * @ibatorgenerated Mon Jul 18 15:02:49 CST 2011
     */
    public void setShard(Shard shard) {
        this.shard = shard;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_action_step
     *
     * @ibatorgenerated Mon Jul 18 15:02:49 CST 2011
     */
    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_action_step
     *
     * @ibatorgenerated Mon Jul 18 15:02:49 CST 2011
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
            return criteriaWithoutValue.size() > 0
                || criteriaWithSingleValue.size() > 0
                || criteriaWithListValue.size() > 0
                || criteriaWithBetweenValue.size() > 0;
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

        public Criteria andActionIdIsNull() {
            addCriterion("action_id is null");
            return this;
        }

        public Criteria andActionIdIsNotNull() {
            addCriterion("action_id is not null");
            return this;
        }

        public Criteria andActionIdEqualTo(Long value) {
            addCriterion("action_id =", value, "actionId");
            return this;
        }

        public Criteria andActionIdNotEqualTo(Long value) {
            addCriterion("action_id <>", value, "actionId");
            return this;
        }

        public Criteria andActionIdGreaterThan(Long value) {
            addCriterion("action_id >", value, "actionId");
            return this;
        }

        public Criteria andActionIdGreaterThanOrEqualTo(Long value) {
            addCriterion("action_id >=", value, "actionId");
            return this;
        }

        public Criteria andActionIdLessThan(Long value) {
            addCriterion("action_id <", value, "actionId");
            return this;
        }

        public Criteria andActionIdLessThanOrEqualTo(Long value) {
            addCriterion("action_id <=", value, "actionId");
            return this;
        }

        public Criteria andActionIdIn(List<Long> values) {
            addCriterion("action_id in", values, "actionId");
            return this;
        }

        public Criteria andActionIdNotIn(List<Long> values) {
            addCriterion("action_id not in", values, "actionId");
            return this;
        }

        public Criteria andActionIdBetween(Long value1, Long value2) {
            addCriterion("action_id between", value1, value2, "actionId");
            return this;
        }

        public Criteria andActionIdNotBetween(Long value1, Long value2) {
            addCriterion("action_id not between", value1, value2, "actionId");
            return this;
        }

        public Criteria andUrlConfigIsNull() {
            addCriterion("url_config is null");
            return this;
        }

        public Criteria andUrlConfigIsNotNull() {
            addCriterion("url_config is not null");
            return this;
        }

        public Criteria andUrlConfigEqualTo(String value) {
            addCriterion("url_config =", value, "urlConfig");
            return this;
        }

        public Criteria andUrlConfigNotEqualTo(String value) {
            addCriterion("url_config <>", value, "urlConfig");
            return this;
        }

        public Criteria andUrlConfigGreaterThan(String value) {
            addCriterion("url_config >", value, "urlConfig");
            return this;
        }

        public Criteria andUrlConfigGreaterThanOrEqualTo(String value) {
            addCriterion("url_config >=", value, "urlConfig");
            return this;
        }

        public Criteria andUrlConfigLessThan(String value) {
            addCriterion("url_config <", value, "urlConfig");
            return this;
        }

        public Criteria andUrlConfigLessThanOrEqualTo(String value) {
            addCriterion("url_config <=", value, "urlConfig");
            return this;
        }

        public Criteria andUrlConfigLike(String value) {
            addCriterion("url_config like", value, "urlConfig");
            return this;
        }

        public Criteria andUrlConfigNotLike(String value) {
            addCriterion("url_config not like", value, "urlConfig");
            return this;
        }

        public Criteria andUrlConfigIn(List<String> values) {
            addCriterion("url_config in", values, "urlConfig");
            return this;
        }

        public Criteria andUrlConfigNotIn(List<String> values) {
            addCriterion("url_config not in", values, "urlConfig");
            return this;
        }

        public Criteria andUrlConfigBetween(String value1, String value2) {
            addCriterion("url_config between", value1, value2, "urlConfig");
            return this;
        }

        public Criteria andUrlConfigNotBetween(String value1, String value2) {
            addCriterion("url_config not between", value1, value2, "urlConfig");
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

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return this;
        }
    }
}