package com.nali.spreader.factory.regular;

import com.nali.spreader.factory.base.TaskMachine;
import com.nali.spreader.factory.base.TaskMeta;
import com.nali.spreader.factory.exporter.Exporter;

public interface RegularTaskProducer<TM extends TaskMeta, E extends Exporter<TM>> extends TaskMachine<TM>, RegularObject {

	void work(E exporter);

}