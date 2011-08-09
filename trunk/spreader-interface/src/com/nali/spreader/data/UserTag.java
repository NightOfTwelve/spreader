package com.nali.spreader.data;

import com.nali.common.model.BaseModel;
import java.io.Serializable;

public class UserTag extends BaseModel implements Serializable {

    private static final long serialVersionUID = -2905125794014057502L;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user_tag.id
     *
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
     */
    private Long id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user_tag.uid
     *
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
     */
    private Long uid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user_tag.tag
     *
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
     */
    private String tag;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user_tag.id
     *
     * @return the value of spreader.tb_user_tag.id
     *
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
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
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
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
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
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
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
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
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
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
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
     */
    public void setTag(String tag) {
        this.tag = tag;
    }
}
