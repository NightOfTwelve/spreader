package com.nali.spreader.model;

import com.nali.common.model.Limit;
import com.nali.common.model.Shard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldRelationExample {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sat Mar 12 17:16:40 CST 2011
     */
    public WorldRelationExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sat Mar 12 17:16:40 CST 2011
     */
    protected WorldRelationExample(WorldRelationExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
        this.limit = example.limit;
        this.shard = example.shard;
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    protected List oredCriteria;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    private Shard shard;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    private Limit limit;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
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
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    public void setShard(Shard shard) {
        this.shard = shard;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
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

        public Criteria andFromUidIsNull() {
            addCriterion("from_uid is null");
            return this;
        }

        public Criteria andFromUidIsNotNull() {
            addCriterion("from_uid is not null");
            return this;
        }

        public Criteria andFromUidEqualTo(Long value) {
            addCriterion("from_uid =", value, "fromUid");
            return this;
        }

        public Criteria andFromUidNotEqualTo(Long value) {
            addCriterion("from_uid <>", value, "fromUid");
            return this;
        }

        public Criteria andFromUidGreaterThan(Long value) {
            addCriterion("from_uid >", value, "fromUid");
            return this;
        }

        public Criteria andFromUidGreaterThanOrEqualTo(Long value) {
            addCriterion("from_uid >=", value, "fromUid");
            return this;
        }

        public Criteria andFromUidLessThan(Long value) {
            addCriterion("from_uid <", value, "fromUid");
            return this;
        }

        public Criteria andFromUidLessThanOrEqualTo(Long value) {
            addCriterion("from_uid <=", value, "fromUid");
            return this;
        }

        public Criteria andFromUidIn(List values) {
            addCriterion("from_uid in", values, "fromUid");
            return this;
        }

        public Criteria andFromUidNotIn(List values) {
            addCriterion("from_uid not in", values, "fromUid");
            return this;
        }

        public Criteria andFromUidBetween(Long value1, Long value2) {
            addCriterion("from_uid between", value1, value2, "fromUid");
            return this;
        }

        public Criteria andFromUidNotBetween(Long value1, Long value2) {
            addCriterion("from_uid not between", value1, value2, "fromUid");
            return this;
        }

        public Criteria andFromUidTypeIsNull() {
            addCriterion("from_uid_type is null");
            return this;
        }

        public Criteria andFromUidTypeIsNotNull() {
            addCriterion("from_uid_type is not null");
            return this;
        }

        public Criteria andFromUidTypeEqualTo(Byte value) {
            addCriterion("from_uid_type =", value, "fromUidType");
            return this;
        }

        public Criteria andFromUidTypeNotEqualTo(Byte value) {
            addCriterion("from_uid_type <>", value, "fromUidType");
            return this;
        }

        public Criteria andFromUidTypeGreaterThan(Byte value) {
            addCriterion("from_uid_type >", value, "fromUidType");
            return this;
        }

        public Criteria andFromUidTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("from_uid_type >=", value, "fromUidType");
            return this;
        }

        public Criteria andFromUidTypeLessThan(Byte value) {
            addCriterion("from_uid_type <", value, "fromUidType");
            return this;
        }

        public Criteria andFromUidTypeLessThanOrEqualTo(Byte value) {
            addCriterion("from_uid_type <=", value, "fromUidType");
            return this;
        }

        public Criteria andFromUidTypeIn(List values) {
            addCriterion("from_uid_type in", values, "fromUidType");
            return this;
        }

        public Criteria andFromUidTypeNotIn(List values) {
            addCriterion("from_uid_type not in", values, "fromUidType");
            return this;
        }

        public Criteria andFromUidTypeBetween(Byte value1, Byte value2) {
            addCriterion("from_uid_type between", value1, value2, "fromUidType");
            return this;
        }

        public Criteria andFromUidTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("from_uid_type not between", value1, value2, "fromUidType");
            return this;
        }

        public Criteria andToUidIsNull() {
            addCriterion("to_uid is null");
            return this;
        }

        public Criteria andToUidIsNotNull() {
            addCriterion("to_uid is not null");
            return this;
        }

        public Criteria andToUidEqualTo(Long value) {
            addCriterion("to_uid =", value, "toUid");
            return this;
        }

        public Criteria andToUidNotEqualTo(Long value) {
            addCriterion("to_uid <>", value, "toUid");
            return this;
        }

        public Criteria andToUidGreaterThan(Long value) {
            addCriterion("to_uid >", value, "toUid");
            return this;
        }

        public Criteria andToUidGreaterThanOrEqualTo(Long value) {
            addCriterion("to_uid >=", value, "toUid");
            return this;
        }

        public Criteria andToUidLessThan(Long value) {
            addCriterion("to_uid <", value, "toUid");
            return this;
        }

        public Criteria andToUidLessThanOrEqualTo(Long value) {
            addCriterion("to_uid <=", value, "toUid");
            return this;
        }

        public Criteria andToUidIn(List values) {
            addCriterion("to_uid in", values, "toUid");
            return this;
        }

        public Criteria andToUidNotIn(List values) {
            addCriterion("to_uid not in", values, "toUid");
            return this;
        }

        public Criteria andToUidBetween(Long value1, Long value2) {
            addCriterion("to_uid between", value1, value2, "toUid");
            return this;
        }

        public Criteria andToUidNotBetween(Long value1, Long value2) {
            addCriterion("to_uid not between", value1, value2, "toUid");
            return this;
        }

        public Criteria andToUidTypeIsNull() {
            addCriterion("to_uid_type is null");
            return this;
        }

        public Criteria andToUidTypeIsNotNull() {
            addCriterion("to_uid_type is not null");
            return this;
        }

        public Criteria andToUidTypeEqualTo(Byte value) {
            addCriterion("to_uid_type =", value, "toUidType");
            return this;
        }

        public Criteria andToUidTypeNotEqualTo(Byte value) {
            addCriterion("to_uid_type <>", value, "toUidType");
            return this;
        }

        public Criteria andToUidTypeGreaterThan(Byte value) {
            addCriterion("to_uid_type >", value, "toUidType");
            return this;
        }

        public Criteria andToUidTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("to_uid_type >=", value, "toUidType");
            return this;
        }

        public Criteria andToUidTypeLessThan(Byte value) {
            addCriterion("to_uid_type <", value, "toUidType");
            return this;
        }

        public Criteria andToUidTypeLessThanOrEqualTo(Byte value) {
            addCriterion("to_uid_type <=", value, "toUidType");
            return this;
        }

        public Criteria andToUidTypeIn(List values) {
            addCriterion("to_uid_type in", values, "toUidType");
            return this;
        }

        public Criteria andToUidTypeNotIn(List values) {
            addCriterion("to_uid_type not in", values, "toUidType");
            return this;
        }

        public Criteria andToUidTypeBetween(Byte value1, Byte value2) {
            addCriterion("to_uid_type between", value1, value2, "toUidType");
            return this;
        }

        public Criteria andToUidTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("to_uid_type not between", value1, value2, "toUidType");
            return this;
        }

        public Criteria andRelationTypeIsNull() {
            addCriterion("relation_type is null");
            return this;
        }

        public Criteria andRelationTypeIsNotNull() {
            addCriterion("relation_type is not null");
            return this;
        }

        public Criteria andRelationTypeEqualTo(Integer value) {
            addCriterion("relation_type =", value, "relationType");
            return this;
        }

        public Criteria andRelationTypeNotEqualTo(Integer value) {
            addCriterion("relation_type <>", value, "relationType");
            return this;
        }

        public Criteria andRelationTypeGreaterThan(Integer value) {
            addCriterion("relation_type >", value, "relationType");
            return this;
        }

        public Criteria andRelationTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("relation_type >=", value, "relationType");
            return this;
        }

        public Criteria andRelationTypeLessThan(Integer value) {
            addCriterion("relation_type <", value, "relationType");
            return this;
        }

        public Criteria andRelationTypeLessThanOrEqualTo(Integer value) {
            addCriterion("relation_type <=", value, "relationType");
            return this;
        }

        public Criteria andRelationTypeIn(List values) {
            addCriterion("relation_type in", values, "relationType");
            return this;
        }

        public Criteria andRelationTypeNotIn(List values) {
            addCriterion("relation_type not in", values, "relationType");
            return this;
        }

        public Criteria andRelationTypeBetween(Integer value1, Integer value2) {
            addCriterion("relation_type between", value1, value2, "relationType");
            return this;
        }

        public Criteria andRelationTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("relation_type not between", value1, value2, "relationType");
            return this;
        }
    }
}
