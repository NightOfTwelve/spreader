package com.nali.spreader.factory;

import com.nali.spreader.factory.regular.RegularTaskProducer;
import com.nali.spreader.factory.result.ResultProcessor;

public interface RegularWorkshop<R> extends ResultProcessor<R>,RegularTaskProducer {
}
