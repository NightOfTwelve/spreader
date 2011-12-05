package com.nali.spreader.factory;

import com.nali.spreader.factory.exporter.MultiTaskMeta;
import com.nali.spreader.factory.passive.MultiPassiveTaskProducer;
import com.nali.spreader.factory.result.ResultProcessor;

public interface MultiTypeTaskPassiveWorkshop<D, R> extends ResultProcessor<R, MultiTaskMeta>, MultiPassiveTaskProducer<D> {
}
