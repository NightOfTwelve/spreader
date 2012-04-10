package com.nali.spreader.data;

import com.nali.common.model.BaseModel;
import java.io.Serializable;
import java.util.Date;

public class WeiboAppeal extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1158526819194552692L;
    public static final Integer STATUS_INIT=0;
    public static final Integer STATUS_START=1;
    public static final Integer STATUS_FAIL=2;

	/**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_weibo_appeal.uid
     *
     * @ibatorgenerated Tue Apr 10 10:15:26 CST 2012
     */
    private Long uid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_weibo_appeal.start_time
     *
     * @ibatorgenerated Tue Apr 10 10:15:26 CST 2012
     */
    private Date startTime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_weibo_appeal.status
     *
     * @ibatorgenerated Tue Apr 10 10:15:26 CST 2012
     */
    private Integer status;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_weibo_appeal.create_time
     *
     * @ibatorgenerated Tue Apr 10 10:15:26 CST 2012
     */
    private Date createTime;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_weibo_appeal.uid
     *
     * @return the value of spreader.tb_weibo_appeal.uid
     *
     * @ibatorgenerated Tue Apr 10 10:15:26 CST 2012
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_weibo_appeal.uid
     *
     * @param uid the value for spreader.tb_weibo_appeal.uid
     *
     * @ibatorgenerated Tue Apr 10 10:15:26 CST 2012
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_weibo_appeal.start_time
     *
     * @return the value of spreader.tb_weibo_appeal.start_time
     *
     * @ibatorgenerated Tue Apr 10 10:15:26 CST 2012
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_weibo_appeal.start_time
     *
     * @param startTime the value for spreader.tb_weibo_appeal.start_time
     *
     * @ibatorgenerated Tue Apr 10 10:15:26 CST 2012
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_weibo_appeal.status
     *
     * @return the value of spreader.tb_weibo_appeal.status
     *
     * @ibatorgenerated Tue Apr 10 10:15:26 CST 2012
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_weibo_appeal.status
     *
     * @param status the value for spreader.tb_weibo_appeal.status
     *
     * @ibatorgenerated Tue Apr 10 10:15:26 CST 2012
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_weibo_appeal.create_time
     *
     * @return the value of spreader.tb_weibo_appeal.create_time
     *
     * @ibatorgenerated Tue Apr 10 10:15:26 CST 2012
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_weibo_appeal.create_time
     *
     * @param createTime the value for spreader.tb_weibo_appeal.create_time
     *
     * @ibatorgenerated Tue Apr 10 10:15:26 CST 2012
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}