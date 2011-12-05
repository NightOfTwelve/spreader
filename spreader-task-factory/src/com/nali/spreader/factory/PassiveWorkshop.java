package com.nali.spreader.factory;

import com.nali.spreader.factory.exporter.SingleTaskMeta;
import com.nali.spreader.factory.passive.SinglePassiveTaskProducer;
import com.nali.spreader.factory.result.ResultProcessor;

public interface PassiveWorkshop<D, R> extends ResultProcessor<R, SingleTaskMeta>, SinglePassiveTaskProducer<D> {
}
