package com.nali.spreader.factory.passive;

import com.nali.spreader.factory.exporter.Exporter;
import com.nali.spreader.factory.exporter.TaskMeta;
import com.nali.spreader.factory.exporter.TaskMachine;

public interface PassiveTaskProducer<D, TM extends TaskMeta, E extends Exporter<TM>> extends TaskMachine<TM>, PassiveObject {

	void work(D data, E exporter);

}