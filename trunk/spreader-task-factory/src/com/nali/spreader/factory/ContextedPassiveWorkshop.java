package com.nali.spreader.factory;

import com.nali.spreader.factory.base.SingleTaskMeta;
import com.nali.spreader.factory.passive.SinglePassiveTaskProducer;
import com.nali.spreader.factory.result.ContextedResultProcessor;

public interface ContextedPassiveWorkshop<D, R> extends SinglePassiveTaskProducer<D>, ContextedResultProcessor<R, SingleTaskMeta> {
}
