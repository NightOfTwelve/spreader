package com.nali.spreader.factory.regular;

import com.nali.spreader.factory.base.MultiTaskMeta;
import com.nali.spreader.factory.exporter.MultiTaskExporter;

public interface MultiRegularTaskProducer extends RegularTaskProducer<MultiTaskMeta, MultiTaskExporter> {

	String work(MultiTaskExporter exporter);

}