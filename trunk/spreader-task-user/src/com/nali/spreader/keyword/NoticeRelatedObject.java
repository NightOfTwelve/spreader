package com.nali.spreader.keyword;

import com.nali.spreader.factory.config.ConfigableType;
import com.nali.spreader.factory.config.SpecialConfigable;
import com.nali.spreader.factory.config.SpecialConfigableType;

/**
 * 消息的调度需要实现的接口，以便在专门的列表也查看
 *
 */
@SpecialConfigableType(ConfigableType.noticeRelated)
public interface NoticeRelatedObject extends SpecialConfigable {
}
