package com.nali.spreader.data;

import com.nali.common.model.Limit;
import com.nali.common.model.Shard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegAddressExample {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_reg_address
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_reg_address
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_reg_address
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    private Shard shard;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_reg_address
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    private Limit limit;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_reg_address
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public RegAddressExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_reg_address
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    protected RegAddressExample(RegAddressExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
        this.limit = example.limit;
        this.shard = example.shard;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_reg_address
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_reg_address
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_reg_address
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_reg_address
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_reg_address
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
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
     * This method corresponds to the database table spreader.tb_reg_address
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_reg_address
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_reg_address
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public void setShard(Shard shard) {
        this.shard = shard;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_reg_address
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_reg_address
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
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

        public Criteria andRegisterIdIsNull() {
            addCriterion("register_id is null");
            return this;
        }

        public Criteria andRegisterIdIsNotNull() {
            addCriterion("register_id is not null");
            return this;
        }

        public Criteria andRegisterIdEqualTo(Long value) {
            addCriterion("register_id =", value, "registerId");
            return this;
        }

        public Criteria andRegisterIdNotEqualTo(Long value) {
            addCriterion("register_id <>", value, "registerId");
            return this;
        }

        public Criteria andRegisterIdGreaterThan(Long value) {
            addCriterion("register_id >", value, "registerId");
            return this;
        }

        public Criteria andRegisterIdGreaterThanOrEqualTo(Long value) {
            addCriterion("register_id >=", value, "registerId");
            return this;
        }

        public Criteria andRegisterIdLessThan(Long value) {
            addCriterion("register_id <", value, "registerId");
            return this;
        }

        public Criteria andRegisterIdLessThanOrEqualTo(Long value) {
            addCriterion("register_id <=", value, "registerId");
            return this;
        }

        public Criteria andRegisterIdIn(List<Long> values) {
            addCriterion("register_id in", values, "registerId");
            return this;
        }

        public Criteria andRegisterIdNotIn(List<Long> values) {
            addCriterion("register_id not in", values, "registerId");
            return this;
        }

        public Criteria andRegisterIdBetween(Long value1, Long value2) {
            addCriterion("register_id between", value1, value2, "registerId");
            return this;
        }

        public Criteria andRegisterIdNotBetween(Long value1, Long value2) {
            addCriterion("register_id not between", value1, value2, "registerId");
            return this;
        }

        public Criteria andNationalityIsNull() {
            addCriterion("nationality is null");
            return this;
        }

        public Criteria andNationalityIsNotNull() {
            addCriterion("nationality is not null");
            return this;
        }

        public Criteria andNationalityEqualTo(String value) {
            addCriterion("nationality =", value, "nationality");
            return this;
        }

        public Criteria andNationalityNotEqualTo(String value) {
            addCriterion("nationality <>", value, "nationality");
            return this;
        }

        public Criteria andNationalityGreaterThan(String value) {
            addCriterion("nationality >", value, "nationality");
            return this;
        }

        public Criteria andNationalityGreaterThanOrEqualTo(String value) {
            addCriterion("nationality >=", value, "nationality");
            return this;
        }

        public Criteria andNationalityLessThan(String value) {
            addCriterion("nationality <", value, "nationality");
            return this;
        }

        public Criteria andNationalityLessThanOrEqualTo(String value) {
            addCriterion("nationality <=", value, "nationality");
            return this;
        }

        public Criteria andNationalityLike(String value) {
            addCriterion("nationality like", value, "nationality");
            return this;
        }

        public Criteria andNationalityNotLike(String value) {
            addCriterion("nationality not like", value, "nationality");
            return this;
        }

        public Criteria andNationalityIn(List<String> values) {
            addCriterion("nationality in", values, "nationality");
            return this;
        }

        public Criteria andNationalityNotIn(List<String> values) {
            addCriterion("nationality not in", values, "nationality");
            return this;
        }

        public Criteria andNationalityBetween(String value1, String value2) {
            addCriterion("nationality between", value1, value2, "nationality");
            return this;
        }

        public Criteria andNationalityNotBetween(String value1, String value2) {
            addCriterion("nationality not between", value1, value2, "nationality");
            return this;
        }

        public Criteria andProvinceIsNull() {
            addCriterion("province is null");
            return this;
        }

        public Criteria andProvinceIsNotNull() {
            addCriterion("province is not null");
            return this;
        }

        public Criteria andProvinceEqualTo(String value) {
            addCriterion("province =", value, "province");
            return this;
        }

        public Criteria andProvinceNotEqualTo(String value) {
            addCriterion("province <>", value, "province");
            return this;
        }

        public Criteria andProvinceGreaterThan(String value) {
            addCriterion("province >", value, "province");
            return this;
        }

        public Criteria andProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("province >=", value, "province");
            return this;
        }

        public Criteria andProvinceLessThan(String value) {
            addCriterion("province <", value, "province");
            return this;
        }

        public Criteria andProvinceLessThanOrEqualTo(String value) {
            addCriterion("province <=", value, "province");
            return this;
        }

        public Criteria andProvinceLike(String value) {
            addCriterion("province like", value, "province");
            return this;
        }

        public Criteria andProvinceNotLike(String value) {
            addCriterion("province not like", value, "province");
            return this;
        }

        public Criteria andProvinceIn(List<String> values) {
            addCriterion("province in", values, "province");
            return this;
        }

        public Criteria andProvinceNotIn(List<String> values) {
            addCriterion("province not in", values, "province");
            return this;
        }

        public Criteria andProvinceBetween(String value1, String value2) {
            addCriterion("province between", value1, value2, "province");
            return this;
        }

        public Criteria andProvinceNotBetween(String value1, String value2) {
            addCriterion("province not between", value1, value2, "province");
            return this;
        }

        public Criteria andCityIsNull() {
            addCriterion("city is null");
            return this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("city is not null");
            return this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("city =", value, "city");
            return this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("city <>", value, "city");
            return this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("city >", value, "city");
            return this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("city >=", value, "city");
            return this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("city <", value, "city");
            return this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("city <=", value, "city");
            return this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("city like", value, "city");
            return this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("city not like", value, "city");
            return this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("city in", values, "city");
            return this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("city not in", values, "city");
            return this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("city between", value1, value2, "city");
            return this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("city not between", value1, value2, "city");
            return this;
        }

        public Criteria andStreetIsNull() {
            addCriterion("street is null");
            return this;
        }

        public Criteria andStreetIsNotNull() {
            addCriterion("street is not null");
            return this;
        }

        public Criteria andStreetEqualTo(String value) {
            addCriterion("street =", value, "street");
            return this;
        }

        public Criteria andStreetNotEqualTo(String value) {
            addCriterion("street <>", value, "street");
            return this;
        }

        public Criteria andStreetGreaterThan(String value) {
            addCriterion("street >", value, "street");
            return this;
        }

        public Criteria andStreetGreaterThanOrEqualTo(String value) {
            addCriterion("street >=", value, "street");
            return this;
        }

        public Criteria andStreetLessThan(String value) {
            addCriterion("street <", value, "street");
            return this;
        }

        public Criteria andStreetLessThanOrEqualTo(String value) {
            addCriterion("street <=", value, "street");
            return this;
        }

        public Criteria andStreetLike(String value) {
            addCriterion("street like", value, "street");
            return this;
        }

        public Criteria andStreetNotLike(String value) {
            addCriterion("street not like", value, "street");
            return this;
        }

        public Criteria andStreetIn(List<String> values) {
            addCriterion("street in", values, "street");
            return this;
        }

        public Criteria andStreetNotIn(List<String> values) {
            addCriterion("street not in", values, "street");
            return this;
        }

        public Criteria andStreetBetween(String value1, String value2) {
            addCriterion("street between", value1, value2, "street");
            return this;
        }

        public Criteria andStreetNotBetween(String value1, String value2) {
            addCriterion("street not between", value1, value2, "street");
            return this;
        }

        public Criteria andSuiteIsNull() {
            addCriterion("suite is null");
            return this;
        }

        public Criteria andSuiteIsNotNull() {
            addCriterion("suite is not null");
            return this;
        }

        public Criteria andSuiteEqualTo(String value) {
            addCriterion("suite =", value, "suite");
            return this;
        }

        public Criteria andSuiteNotEqualTo(String value) {
            addCriterion("suite <>", value, "suite");
            return this;
        }

        public Criteria andSuiteGreaterThan(String value) {
            addCriterion("suite >", value, "suite");
            return this;
        }

        public Criteria andSuiteGreaterThanOrEqualTo(String value) {
            addCriterion("suite >=", value, "suite");
            return this;
        }

        public Criteria andSuiteLessThan(String value) {
            addCriterion("suite <", value, "suite");
            return this;
        }

        public Criteria andSuiteLessThanOrEqualTo(String value) {
            addCriterion("suite <=", value, "suite");
            return this;
        }

        public Criteria andSuiteLike(String value) {
            addCriterion("suite like", value, "suite");
            return this;
        }

        public Criteria andSuiteNotLike(String value) {
            addCriterion("suite not like", value, "suite");
            return this;
        }

        public Criteria andSuiteIn(List<String> values) {
            addCriterion("suite in", values, "suite");
            return this;
        }

        public Criteria andSuiteNotIn(List<String> values) {
            addCriterion("suite not in", values, "suite");
            return this;
        }

        public Criteria andSuiteBetween(String value1, String value2) {
            addCriterion("suite between", value1, value2, "suite");
            return this;
        }

        public Criteria andSuiteNotBetween(String value1, String value2) {
            addCriterion("suite not between", value1, value2, "suite");
            return this;
        }

        public Criteria andPostCodeIsNull() {
            addCriterion("post_code is null");
            return this;
        }

        public Criteria andPostCodeIsNotNull() {
            addCriterion("post_code is not null");
            return this;
        }

        public Criteria andPostCodeEqualTo(String value) {
            addCriterion("post_code =", value, "postCode");
            return this;
        }

        public Criteria andPostCodeNotEqualTo(String value) {
            addCriterion("post_code <>", value, "postCode");
            return this;
        }

        public Criteria andPostCodeGreaterThan(String value) {
            addCriterion("post_code >", value, "postCode");
            return this;
        }

        public Criteria andPostCodeGreaterThanOrEqualTo(String value) {
            addCriterion("post_code >=", value, "postCode");
            return this;
        }

        public Criteria andPostCodeLessThan(String value) {
            addCriterion("post_code <", value, "postCode");
            return this;
        }

        public Criteria andPostCodeLessThanOrEqualTo(String value) {
            addCriterion("post_code <=", value, "postCode");
            return this;
        }

        public Criteria andPostCodeLike(String value) {
            addCriterion("post_code like", value, "postCode");
            return this;
        }

        public Criteria andPostCodeNotLike(String value) {
            addCriterion("post_code not like", value, "postCode");
            return this;
        }

        public Criteria andPostCodeIn(List<String> values) {
            addCriterion("post_code in", values, "postCode");
            return this;
        }

        public Criteria andPostCodeNotIn(List<String> values) {
            addCriterion("post_code not in", values, "postCode");
            return this;
        }

        public Criteria andPostCodeBetween(String value1, String value2) {
            addCriterion("post_code between", value1, value2, "postCode");
            return this;
        }

        public Criteria andPostCodeNotBetween(String value1, String value2) {
            addCriterion("post_code not between", value1, value2, "postCode");
            return this;
        }

        public Criteria andPhoneAreaCodeIsNull() {
            addCriterion("phone_area_code is null");
            return this;
        }

        public Criteria andPhoneAreaCodeIsNotNull() {
            addCriterion("phone_area_code is not null");
            return this;
        }

        public Criteria andPhoneAreaCodeEqualTo(String value) {
            addCriterion("phone_area_code =", value, "phoneAreaCode");
            return this;
        }

        public Criteria andPhoneAreaCodeNotEqualTo(String value) {
            addCriterion("phone_area_code <>", value, "phoneAreaCode");
            return this;
        }

        public Criteria andPhoneAreaCodeGreaterThan(String value) {
            addCriterion("phone_area_code >", value, "phoneAreaCode");
            return this;
        }

        public Criteria andPhoneAreaCodeGreaterThanOrEqualTo(String value) {
            addCriterion("phone_area_code >=", value, "phoneAreaCode");
            return this;
        }

        public Criteria andPhoneAreaCodeLessThan(String value) {
            addCriterion("phone_area_code <", value, "phoneAreaCode");
            return this;
        }

        public Criteria andPhoneAreaCodeLessThanOrEqualTo(String value) {
            addCriterion("phone_area_code <=", value, "phoneAreaCode");
            return this;
        }

        public Criteria andPhoneAreaCodeLike(String value) {
            addCriterion("phone_area_code like", value, "phoneAreaCode");
            return this;
        }

        public Criteria andPhoneAreaCodeNotLike(String value) {
            addCriterion("phone_area_code not like", value, "phoneAreaCode");
            return this;
        }

        public Criteria andPhoneAreaCodeIn(List<String> values) {
            addCriterion("phone_area_code in", values, "phoneAreaCode");
            return this;
        }

        public Criteria andPhoneAreaCodeNotIn(List<String> values) {
            addCriterion("phone_area_code not in", values, "phoneAreaCode");
            return this;
        }

        public Criteria andPhoneAreaCodeBetween(String value1, String value2) {
            addCriterion("phone_area_code between", value1, value2, "phoneAreaCode");
            return this;
        }

        public Criteria andPhoneAreaCodeNotBetween(String value1, String value2) {
            addCriterion("phone_area_code not between", value1, value2, "phoneAreaCode");
            return this;
        }

        public Criteria andPhoneCodeIsNull() {
            addCriterion("phone_code is null");
            return this;
        }

        public Criteria andPhoneCodeIsNotNull() {
            addCriterion("phone_code is not null");
            return this;
        }

        public Criteria andPhoneCodeEqualTo(String value) {
            addCriterion("phone_code =", value, "phoneCode");
            return this;
        }

        public Criteria andPhoneCodeNotEqualTo(String value) {
            addCriterion("phone_code <>", value, "phoneCode");
            return this;
        }

        public Criteria andPhoneCodeGreaterThan(String value) {
            addCriterion("phone_code >", value, "phoneCode");
            return this;
        }

        public Criteria andPhoneCodeGreaterThanOrEqualTo(String value) {
            addCriterion("phone_code >=", value, "phoneCode");
            return this;
        }

        public Criteria andPhoneCodeLessThan(String value) {
            addCriterion("phone_code <", value, "phoneCode");
            return this;
        }

        public Criteria andPhoneCodeLessThanOrEqualTo(String value) {
            addCriterion("phone_code <=", value, "phoneCode");
            return this;
        }

        public Criteria andPhoneCodeLike(String value) {
            addCriterion("phone_code like", value, "phoneCode");
            return this;
        }

        public Criteria andPhoneCodeNotLike(String value) {
            addCriterion("phone_code not like", value, "phoneCode");
            return this;
        }

        public Criteria andPhoneCodeIn(List<String> values) {
            addCriterion("phone_code in", values, "phoneCode");
            return this;
        }

        public Criteria andPhoneCodeNotIn(List<String> values) {
            addCriterion("phone_code not in", values, "phoneCode");
            return this;
        }

        public Criteria andPhoneCodeBetween(String value1, String value2) {
            addCriterion("phone_code between", value1, value2, "phoneCode");
            return this;
        }

        public Criteria andPhoneCodeNotBetween(String value1, String value2) {
            addCriterion("phone_code not between", value1, value2, "phoneCode");
            return this;
        }
    }
}