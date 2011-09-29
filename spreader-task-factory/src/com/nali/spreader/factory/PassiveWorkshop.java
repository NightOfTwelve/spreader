package com.nali.spreader.factory;

import com.nali.spreader.factory.passive.PassiveTaskProducer;
import com.nali.spreader.factory.result.ResultProcessor;

public interface PassiveWorkshop<D, R> extends ResultProcessor<R>,PassiveTaskProducer<D> {
}
