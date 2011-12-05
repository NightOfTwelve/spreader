package com.nali.spreader.factory.regular;

import com.nali.spreader.factory.exporter.Exporter;
import com.nali.spreader.factory.exporter.TaskMachine;
import com.nali.spreader.factory.exporter.TaskMeta;

public interface RegularTaskProducer<TM extends TaskMeta, E extends Exporter<TM>> extends TaskMachine<TM>, RegularObject {

	void work(E exporter);

}