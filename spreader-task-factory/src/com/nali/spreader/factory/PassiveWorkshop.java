package com.nali.spreader.factory;

import com.nali.spreader.factory.base.SingleTaskMeta;
import com.nali.spreader.factory.passive.SinglePassiveTaskProducer;
import com.nali.spreader.factory.result.SimpleResultProcessor;

public interface PassiveWorkshop<D, R> extends SinglePassiveTaskProducer<D>, SimpleResultProcessor<R, SingleTaskMeta> {
}
