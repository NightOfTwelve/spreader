package com.nali.spreader.factory.passive;

import com.nali.spreader.factory.exporter.SingleTaskProducer;
import com.nali.spreader.factory.exporter.TaskExporter;

public interface PassiveTaskProducer<D> extends SingleTaskProducer {

	void work(D data, TaskExporter exporter);

}