package com.nali.spreader.factory.passive;

import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.exporter.SingleTaskMeta;

public interface SinglePassiveTaskProducer<D> extends PassiveTaskProducer<D, SingleTaskMeta, SingleTaskExporter> {

}