package com.nali.spreader.factory.regular;

import com.nali.spreader.factory.exporter.SingleTaskComponent;
import com.nali.spreader.factory.exporter.TaskExporter;

public interface RegularTaskProducer extends SingleTaskComponent, RegularObject {

	void work(TaskExporter exporter);

}