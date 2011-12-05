package com.nali.spreader.factory.passive;

import com.nali.spreader.factory.exporter.MultiTaskExporter;
import com.nali.spreader.factory.exporter.MultiTaskMeta;

public interface MultiPassiveTaskProducer<D> extends PassiveTaskProducer<D, MultiTaskMeta, MultiTaskExporter> {

}