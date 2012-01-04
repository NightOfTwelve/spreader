package com.nali.spreader.factory;

import com.nali.spreader.factory.base.MultiTaskMeta;
import com.nali.spreader.factory.passive.MultiPassiveTaskProducer;
import com.nali.spreader.factory.result.SimpleResultProcessor;

public interface MultiTypeTaskPassiveWorkshop<D, R> extends MultiPassiveTaskProducer<D>, SimpleResultProcessor<R, MultiTaskMeta> {
}
