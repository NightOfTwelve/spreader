package com.nali.spreader.model;

import com.nali.common.model.BaseModel;

public class TbContentHeadersKey extends BaseModel {

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_content_headers.content_id
     *
     * @ibatorgenerated Thu Mar 31 15:40:02 CST 2011
     */
    private Long contentId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_content_headers.header_id
     *
     * @ibatorgenerated Thu Mar 31 15:40:02 CST 2011
     */
    private Long headerId;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_content_headers.content_id
     *
     * @return the value of spreader.tb_content_headers.content_id
     *
     * @ibatorgenerated Thu Mar 31 15:40:02 CST 2011
     */
    public Long getContentId() {
        return contentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_content_headers.content_id
     *
     * @param contentId the value for spreader.tb_content_headers.content_id
     *
     * @ibatorgenerated Thu Mar 31 15:40:02 CST 2011
     */
    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_content_headers.header_id
     *
     * @return the value of spreader.tb_content_headers.header_id
     *
     * @ibatorgenerated Thu Mar 31 15:40:02 CST 2011
     */
    public Long getHeaderId() {
        return headerId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_content_headers.header_id
     *
     * @param headerId the value for spreader.tb_content_headers.header_id
     *
     * @ibatorgenerated Thu Mar 31 15:40:02 CST 2011
     */
    public void setHeaderId(Long headerId) {
        this.headerId = headerId;
    }
}
