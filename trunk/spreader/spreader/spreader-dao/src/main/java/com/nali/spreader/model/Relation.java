package com.nali.spreader.model;

import java.io.Serializable;

public class Relation implements Serializable {

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_relation.relation_id
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    private Integer relationId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_relation.relation_name
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    private String relationName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_relation.relation_level
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    private Integer relationLevel;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_relation.relation_id
     *
     * @return the value of spreader.tb_relation.relation_id
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public Integer getRelationId() {
        return relationId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_relation.relation_id
     *
     * @param relationId the value for spreader.tb_relation.relation_id
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_relation.relation_name
     *
     * @return the value of spreader.tb_relation.relation_name
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public String getRelationName() {
        return relationName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_relation.relation_name
     *
     * @param relationName the value for spreader.tb_relation.relation_name
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_relation.relation_level
     *
     * @return the value of spreader.tb_relation.relation_level
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public Integer getRelationLevel() {
        return relationLevel;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_relation.relation_level
     *
     * @param relationLevel the value for spreader.tb_relation.relation_level
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public void setRelationLevel(Integer relationLevel) {
        this.relationLevel = relationLevel;
    }
}
