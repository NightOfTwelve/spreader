package com.nali.spreader.model;

import com.nali.common.model.Limit;
import com.nali.common.model.Shard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebContentParametersExample {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:16:39 CST 2011
     */
    public WebContentParametersExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:16:39 CST 2011
     */
    protected WebContentParametersExample(WebContentParametersExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
        this.limit = example.limit;
        this.shard = example.shard;
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    protected List oredCriteria;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    private Shard shard;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    private Limit limit;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
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
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public void setShard(Shard shard) {
        this.shard = shard;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public static class Criteria {

        protected List criteriaWithoutValue;

        protected List criteriaWithSingleValue;

        protected List criteriaWithListValue;

        protected List criteriaWithBetweenValue;

        protected Criteria() {
            super();
            criteriaWithoutValue = new ArrayList();
            criteriaWithSingleValue = new ArrayList();
            criteriaWithListValue = new ArrayList();
            criteriaWithBetweenValue = new ArrayList();
        }

        public boolean isValid() {
            return criteriaWithoutValue.size() > 0 || criteriaWithSingleValue.size() > 0 || criteriaWithListValue.size() > 0 || criteriaWithBetweenValue.size() > 0;
        }

        public List getCriteriaWithoutValue() {
            return criteriaWithoutValue;
        }

        public List getCriteriaWithSingleValue() {
            return criteriaWithSingleValue;
        }

        public List getCriteriaWithListValue() {
            return criteriaWithListValue;
        }

        public List getCriteriaWithBetweenValue() {
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
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            List list = new ArrayList();
            list.add(value1);
            list.add(value2);
            Map map = new HashMap();
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

        public Criteria andIdIn(List values) {
            addCriterion("id in", values, "id");
            return this;
        }

        public Criteria andIdNotIn(List values) {
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

        public Criteria andContentIdIsNull() {
            addCriterion("content_id is null");
            return this;
        }

        public Criteria andContentIdIsNotNull() {
            addCriterion("content_id is not null");
            return this;
        }

        public Criteria andContentIdEqualTo(Long value) {
            addCriterion("content_id =", value, "contentId");
            return this;
        }

        public Criteria andContentIdNotEqualTo(Long value) {
            addCriterion("content_id <>", value, "contentId");
            return this;
        }

        public Criteria andContentIdGreaterThan(Long value) {
            addCriterion("content_id >", value, "contentId");
            return this;
        }

        public Criteria andContentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("content_id >=", value, "contentId");
            return this;
        }

        public Criteria andContentIdLessThan(Long value) {
            addCriterion("content_id <", value, "contentId");
            return this;
        }

        public Criteria andContentIdLessThanOrEqualTo(Long value) {
            addCriterion("content_id <=", value, "contentId");
            return this;
        }

        public Criteria andContentIdIn(List values) {
            addCriterion("content_id in", values, "contentId");
            return this;
        }

        public Criteria andContentIdNotIn(List values) {
            addCriterion("content_id not in", values, "contentId");
            return this;
        }

        public Criteria andContentIdBetween(Long value1, Long value2) {
            addCriterion("content_id between", value1, value2, "contentId");
            return this;
        }

        public Criteria andContentIdNotBetween(Long value1, Long value2) {
            addCriterion("content_id not between", value1, value2, "contentId");
            return this;
        }

        public Criteria andTargetParamIdIsNull() {
            addCriterion("target_param_id is null");
            return this;
        }

        public Criteria andTargetParamIdIsNotNull() {
            addCriterion("target_param_id is not null");
            return this;
        }

        public Criteria andTargetParamIdEqualTo(Long value) {
            addCriterion("target_param_id =", value, "targetParamId");
            return this;
        }

        public Criteria andTargetParamIdNotEqualTo(Long value) {
            addCriterion("target_param_id <>", value, "targetParamId");
            return this;
        }

        public Criteria andTargetParamIdGreaterThan(Long value) {
            addCriterion("target_param_id >", value, "targetParamId");
            return this;
        }

        public Criteria andTargetParamIdGreaterThanOrEqualTo(Long value) {
            addCriterion("target_param_id >=", value, "targetParamId");
            return this;
        }

        public Criteria andTargetParamIdLessThan(Long value) {
            addCriterion("target_param_id <", value, "targetParamId");
            return this;
        }

        public Criteria andTargetParamIdLessThanOrEqualTo(Long value) {
            addCriterion("target_param_id <=", value, "targetParamId");
            return this;
        }

        public Criteria andTargetParamIdIn(List values) {
            addCriterion("target_param_id in", values, "targetParamId");
            return this;
        }

        public Criteria andTargetParamIdNotIn(List values) {
            addCriterion("target_param_id not in", values, "targetParamId");
            return this;
        }

        public Criteria andTargetParamIdBetween(Long value1, Long value2) {
            addCriterion("target_param_id between", value1, value2, "targetParamId");
            return this;
        }

        public Criteria andTargetParamIdNotBetween(Long value1, Long value2) {
            addCriterion("target_param_id not between", value1, value2, "targetParamId");
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

        public Criteria andNameIn(List values) {
            addCriterion("name in", values, "name");
            return this;
        }

        public Criteria andNameNotIn(List values) {
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

        public Criteria andValueIsNull() {
            addCriterion("value is null");
            return this;
        }

        public Criteria andValueIsNotNull() {
            addCriterion("value is not null");
            return this;
        }

        public Criteria andValueEqualTo(String value) {
            addCriterion("value =", value, "value");
            return this;
        }

        public Criteria andValueNotEqualTo(String value) {
            addCriterion("value <>", value, "value");
            return this;
        }

        public Criteria andValueGreaterThan(String value) {
            addCriterion("value >", value, "value");
            return this;
        }

        public Criteria andValueGreaterThanOrEqualTo(String value) {
            addCriterion("value >=", value, "value");
            return this;
        }

        public Criteria andValueLessThan(String value) {
            addCriterion("value <", value, "value");
            return this;
        }

        public Criteria andValueLessThanOrEqualTo(String value) {
            addCriterion("value <=", value, "value");
            return this;
        }

        public Criteria andValueLike(String value) {
            addCriterion("value like", value, "value");
            return this;
        }

        public Criteria andValueNotLike(String value) {
            addCriterion("value not like", value, "value");
            return this;
        }

        public Criteria andValueIn(List values) {
            addCriterion("value in", values, "value");
            return this;
        }

        public Criteria andValueNotIn(List values) {
            addCriterion("value not in", values, "value");
            return this;
        }

        public Criteria andValueBetween(String value1, String value2) {
            addCriterion("value between", value1, value2, "value");
            return this;
        }

        public Criteria andValueNotBetween(String value1, String value2) {
            addCriterion("value not between", value1, value2, "value");
            return this;
        }
    }
}
