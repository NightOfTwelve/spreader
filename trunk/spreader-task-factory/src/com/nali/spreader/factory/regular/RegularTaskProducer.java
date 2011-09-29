package com.nali.spreader.factory.regular;

import com.nali.spreader.factory.exporter.SingleTaskProducer;
import com.nali.spreader.factory.exporter.TaskExporter;

public interface RegularTaskProducer extends SingleTaskProducer {

	void work(TaskExporter exporter);

}