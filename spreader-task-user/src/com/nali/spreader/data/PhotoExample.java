package com.nali.spreader.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nali.common.model.Limit;
import com.nali.common.model.Shard;

public class PhotoExample {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_photo
     *
     * @ibatorgenerated Tue Nov 29 11:08:15 CST 2011
     */
    public PhotoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_photo
     *
     * @ibatorgenerated Tue Nov 29 11:08:15 CST 2011
     */
    protected PhotoExample(PhotoExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
        this.limit = example.limit;
        this.shard = example.shard;
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_photo
     *
     * @ibatorgenerated Thu Dec 15 11:45:43 CST 2011
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_photo
     *
     * @ibatorgenerated Thu Dec 15 11:45:43 CST 2011
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_photo
     *
     * @ibatorgenerated Thu Dec 15 11:45:43 CST 2011
     */
    private Shard shard;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_photo
     *
     * @ibatorgenerated Thu Dec 15 11:45:43 CST 2011
     */
    private Limit limit;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_photo
     *
     * @ibatorgenerated Thu Dec 15 11:45:43 CST 2011
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_photo
     *
     * @ibatorgenerated Thu Dec 15 11:45:43 CST 2011
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_photo
     *
     * @ibatorgenerated Thu Dec 15 11:45:43 CST 2011
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_photo
     *
     * @ibatorgenerated Thu Dec 15 11:45:43 CST 2011
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_photo
     *
     * @ibatorgenerated Thu Dec 15 11:45:43 CST 2011
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
     * This method corresponds to the database table spreader.tb_photo
     *
     * @ibatorgenerated Thu Dec 15 11:45:43 CST 2011
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_photo
     *
     * @ibatorgenerated Thu Dec 15 11:45:43 CST 2011
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_photo
     *
     * @ibatorgenerated Thu Dec 15 11:45:43 CST 2011
     */
    public void setShard(Shard shard) {
        this.shard = shard;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_photo
     *
     * @ibatorgenerated Thu Dec 15 11:45:43 CST 2011
     */
    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_photo
     *
     * @ibatorgenerated Thu Dec 15 11:45:43 CST 2011
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

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return this;
        }

        public Criteria andGenderEqualTo(Integer value) {
            addCriterion("gender =", value, "gender");
            return this;
        }

        public Criteria andGenderNotEqualTo(Integer value) {
            addCriterion("gender <>", value, "gender");
            return this;
        }

        public Criteria andGenderGreaterThan(Integer value) {
            addCriterion("gender >", value, "gender");
            return this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(Integer value) {
            addCriterion("gender >=", value, "gender");
            return this;
        }

        public Criteria andGenderLessThan(Integer value) {
            addCriterion("gender <", value, "gender");
            return this;
        }

        public Criteria andGenderLessThanOrEqualTo(Integer value) {
            addCriterion("gender <=", value, "gender");
            return this;
        }

        public Criteria andGenderIn(List<Integer> values) {
            addCriterion("gender in", values, "gender");
            return this;
        }

        public Criteria andGenderNotIn(List<Integer> values) {
            addCriterion("gender not in", values, "gender");
            return this;
        }

        public Criteria andGenderBetween(Integer value1, Integer value2) {
            addCriterion("gender between", value1, value2, "gender");
            return this;
        }

        public Criteria andGenderNotBetween(Integer value1, Integer value2) {
            addCriterion("gender not between", value1, value2, "gender");
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

        public Criteria andPicTypeIsNull() {
            addCriterion("pic_type is null");
            return this;
        }

        public Criteria andPicTypeIsNotNull() {
            addCriterion("pic_type is not null");
            return this;
        }

        public Criteria andPicTypeEqualTo(String value) {
            addCriterion("pic_type =", value, "picType");
            return this;
        }

        public Criteria andPicTypeNotEqualTo(String value) {
            addCriterion("pic_type <>", value, "picType");
            return this;
        }

        public Criteria andPicTypeGreaterThan(String value) {
            addCriterion("pic_type >", value, "picType");
            return this;
        }

        public Criteria andPicTypeGreaterThanOrEqualTo(String value) {
            addCriterion("pic_type >=", value, "picType");
            return this;
        }

        public Criteria andPicTypeLessThan(String value) {
            addCriterion("pic_type <", value, "picType");
            return this;
        }

        public Criteria andPicTypeLessThanOrEqualTo(String value) {
            addCriterion("pic_type <=", value, "picType");
            return this;
        }

        public Criteria andPicTypeLike(String value) {
            addCriterion("pic_type like", value, "picType");
            return this;
        }

        public Criteria andPicTypeNotLike(String value) {
            addCriterion("pic_type not like", value, "picType");
            return this;
        }

        public Criteria andPicTypeIn(List<String> values) {
            addCriterion("pic_type in", values, "picType");
            return this;
        }

        public Criteria andPicTypeNotIn(List<String> values) {
            addCriterion("pic_type not in", values, "picType");
            return this;
        }

        public Criteria andPicTypeBetween(String value1, String value2) {
            addCriterion("pic_type between", value1, value2, "picType");
            return this;
        }

        public Criteria andPicTypeNotBetween(String value1, String value2) {
            addCriterion("pic_type not between", value1, value2, "picType");
            return this;
        }

        public Criteria andPicUrlIsNull() {
            addCriterion("pic_url is null");
            return this;
        }

        public Criteria andPicUrlIsNotNull() {
            addCriterion("pic_url is not null");
            return this;
        }

        public Criteria andPicUrlEqualTo(String value) {
            addCriterion("pic_url =", value, "picUrl");
            return this;
        }

        public Criteria andPicUrlNotEqualTo(String value) {
            addCriterion("pic_url <>", value, "picUrl");
            return this;
        }

        public Criteria andPicUrlGreaterThan(String value) {
            addCriterion("pic_url >", value, "picUrl");
            return this;
        }

        public Criteria andPicUrlGreaterThanOrEqualTo(String value) {
            addCriterion("pic_url >=", value, "picUrl");
            return this;
        }

        public Criteria andPicUrlLessThan(String value) {
            addCriterion("pic_url <", value, "picUrl");
            return this;
        }

        public Criteria andPicUrlLessThanOrEqualTo(String value) {
            addCriterion("pic_url <=", value, "picUrl");
            return this;
        }

        public Criteria andPicUrlLike(String value) {
            addCriterion("pic_url like", value, "picUrl");
            return this;
        }

        public Criteria andPicUrlNotLike(String value) {
            addCriterion("pic_url not like", value, "picUrl");
            return this;
        }

        public Criteria andPicUrlIn(List<String> values) {
            addCriterion("pic_url in", values, "picUrl");
            return this;
        }

        public Criteria andPicUrlNotIn(List<String> values) {
            addCriterion("pic_url not in", values, "picUrl");
            return this;
        }

        public Criteria andPicUrlBetween(String value1, String value2) {
            addCriterion("pic_url between", value1, value2, "picUrl");
            return this;
        }

        public Criteria andPicUrlNotBetween(String value1, String value2) {
            addCriterion("pic_url not between", value1, value2, "picUrl");
            return this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createtime is null");
            return this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createtime is not null");
            return this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createtime =", value, "createtime");
            return this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createtime <>", value, "createtime");
            return this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createtime >", value, "createtime");
            return this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createtime >=", value, "createtime");
            return this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createtime <", value, "createtime");
            return this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createtime <=", value, "createtime");
            return this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createtime in", values, "createtime");
            return this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createtime not in", values, "createtime");
            return this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createtime between", value1, value2, "createtime");
            return this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createtime not between", value1, value2, "createtime");
            return this;
        }

        public Criteria andAvatarflgIsNull() {
            addCriterion("avatarflg is null");
            return this;
        }

        public Criteria andAvatarflgIsNotNull() {
            addCriterion("avatarflg is not null");
            return this;
        }

        public Criteria andAvatarflgEqualTo(Boolean value) {
            addCriterion("avatarflg =", value, "avatarflg");
            return this;
        }

        public Criteria andAvatarflgNotEqualTo(Boolean value) {
            addCriterion("avatarflg <>", value, "avatarflg");
            return this;
        }

        public Criteria andAvatarflgGreaterThan(Boolean value) {
            addCriterion("avatarflg >", value, "avatarflg");
            return this;
        }

        public Criteria andAvatarflgGreaterThanOrEqualTo(Boolean value) {
            addCriterion("avatarflg >=", value, "avatarflg");
            return this;
        }

        public Criteria andAvatarflgLessThan(Boolean value) {
            addCriterion("avatarflg <", value, "avatarflg");
            return this;
        }

        public Criteria andAvatarflgLessThanOrEqualTo(Boolean value) {
            addCriterion("avatarflg <=", value, "avatarflg");
            return this;
        }

        public Criteria andAvatarflgIn(List<Boolean> values) {
            addCriterion("avatarflg in", values, "avatarflg");
            return this;
        }

        public Criteria andAvatarflgNotIn(List<Boolean> values) {
            addCriterion("avatarflg not in", values, "avatarflg");
            return this;
        }

        public Criteria andAvatarflgBetween(Boolean value1, Boolean value2) {
            addCriterion("avatarflg between", value1, value2, "avatarflg");
            return this;
        }

        public Criteria andAvatarflgNotBetween(Boolean value1, Boolean value2) {
            addCriterion("avatarflg not between", value1, value2, "avatarflg");
            return this;
        }

        public Criteria andPhotolibflgIsNull() {
            addCriterion("photolibflg is null");
            return this;
        }

        public Criteria andPhotolibflgIsNotNull() {
            addCriterion("photolibflg is not null");
            return this;
        }

        public Criteria andPhotolibflgEqualTo(Boolean value) {
            addCriterion("photolibflg =", value, "photolibflg");
            return this;
        }

        public Criteria andPhotolibflgNotEqualTo(Boolean value) {
            addCriterion("photolibflg <>", value, "photolibflg");
            return this;
        }

        public Criteria andPhotolibflgGreaterThan(Boolean value) {
            addCriterion("photolibflg >", value, "photolibflg");
            return this;
        }

        public Criteria andPhotolibflgGreaterThanOrEqualTo(Boolean value) {
            addCriterion("photolibflg >=", value, "photolibflg");
            return this;
        }

        public Criteria andPhotolibflgLessThan(Boolean value) {
            addCriterion("photolibflg <", value, "photolibflg");
            return this;
        }

        public Criteria andPhotolibflgLessThanOrEqualTo(Boolean value) {
            addCriterion("photolibflg <=", value, "photolibflg");
            return this;
        }

        public Criteria andPhotolibflgIn(List<Boolean> values) {
            addCriterion("photolibflg in", values, "photolibflg");
            return this;
        }

        public Criteria andPhotolibflgNotIn(List<Boolean> values) {
            addCriterion("photolibflg not in", values, "photolibflg");
            return this;
        }

        public Criteria andPhotolibflgBetween(Boolean value1, Boolean value2) {
            addCriterion("photolibflg between", value1, value2, "photolibflg");
            return this;
        }

        public Criteria andPhotolibflgNotBetween(Boolean value1, Boolean value2) {
            addCriterion("photolibflg not between", value1, value2, "photolibflg");
            return this;
        }
    }
}
