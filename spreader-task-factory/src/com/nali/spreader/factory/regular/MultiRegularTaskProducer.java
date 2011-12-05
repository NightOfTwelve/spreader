package com.nali.spreader.factory.regular;

import com.nali.spreader.factory.exporter.MultiTaskExporter;
import com.nali.spreader.factory.exporter.MultiTaskMeta;

public interface MultiRegularTaskProducer extends RegularTaskProducer<MultiTaskMeta, MultiTaskExporter> {

	void work(MultiTaskExporter exporter);

}