package com.nali.spreader.factory.passive;

import com.nali.spreader.factory.exporter.SingleTaskComponent;
import com.nali.spreader.factory.exporter.TaskExporter;

public interface PassiveTaskProducer<D> extends SingleTaskComponent, PassiveObject {

	void work(D data, TaskExporter exporter);

}