package com.nali.spreader.data;

import com.nali.common.model.BaseModel;
import java.io.Serializable;

public class UserTag extends BaseModel implements Serializable {

    private static final long serialVersionUID = -2110710999054496359L;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user_tag.id
     *
     * @ibatorgenerated Tue May 15 13:27:26 CST 2012
     */
    private Long id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user_tag.uid
     *
     * @ibatorgenerated Tue May 15 13:27:26 CST 2012
     */
    private Long uid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user_tag.tag
     *
     * @ibatorgenerated Tue May 15 13:27:26 CST 2012
     */
    private String tag;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user_tag.tag_id
     *
     * @ibatorgenerated Tue May 15 13:27:26 CST 2012
     */
    private Long tagId;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user_tag.id
     *
     * @return the value of spreader.tb_user_tag.id
     *
     * @ibatorgenerated Tue May 15 13:27:26 CST 2012
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user_tag.id
     *
     * @param id the value for spreader.tb_user_tag.id
     *
     * @ibatorgenerated Tue May 15 13:27:26 CST 2012
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user_tag.uid
     *
     * @return the value of spreader.tb_user_tag.uid
     *
     * @ibatorgenerated Tue May 15 13:27:26 CST 2012
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user_tag.uid
     *
     * @param uid the value for spreader.tb_user_tag.uid
     *
     * @ibatorgenerated Tue May 15 13:27:26 CST 2012
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user_tag.tag
     *
     * @return the value of spreader.tb_user_tag.tag
     *
     * @ibatorgenerated Tue May 15 13:27:26 CST 2012
     */
    public String getTag() {
        return tag;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user_tag.tag
     *
     * @param tag the value for spreader.tb_user_tag.tag
     *
     * @ibatorgenerated Tue May 15 13:27:26 CST 2012
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user_tag.tag_id
     *
     * @return the value of spreader.tb_user_tag.tag_id
     *
     * @ibatorgenerated Tue May 15 13:27:26 CST 2012
     */
    public Long getTagId() {
        return tagId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user_tag.tag_id
     *
     * @param tagId the value for spreader.tb_user_tag.tag_id
     *
     * @ibatorgenerated Tue May 15 13:27:26 CST 2012
     */
    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
