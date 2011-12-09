package com.nali.spreader.factory;

import com.nali.spreader.factory.base.SingleTaskMeta;
import com.nali.spreader.factory.passive.SinglePassiveTaskProducer;
import com.nali.spreader.factory.result.ResultProcessor;

public interface PassiveWorkshop<D, R> extends SinglePassiveTaskProducer<D>, ResultProcessor<R, SingleTaskMeta> {
}
