package com.nali.spreader.model;

import com.nali.common.model.BaseModel;

public class TbUserContentKey extends BaseModel {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user_content.content_id
     *
     * @ibatorgenerated Fri Jul 15 17:17:13 CST 2011
     */
    private Long contentId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user_content.uid
     *
     * @ibatorgenerated Fri Jul 15 17:17:13 CST 2011
     */
    private Long uid;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user_content.content_id
     *
     * @return the value of spreader.tb_user_content.content_id
     *
     * @ibatorgenerated Fri Jul 15 17:17:13 CST 2011
     */
    public Long getContentId() {
        return contentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user_content.content_id
     *
     * @param contentId the value for spreader.tb_user_content.content_id
     *
     * @ibatorgenerated Fri Jul 15 17:17:13 CST 2011
     */
    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user_content.uid
     *
     * @return the value of spreader.tb_user_content.uid
     *
     * @ibatorgenerated Fri Jul 15 17:17:13 CST 2011
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user_content.uid
     *
     * @param uid the value for spreader.tb_user_content.uid
     *
     * @ibatorgenerated Fri Jul 15 17:17:13 CST 2011
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }
}