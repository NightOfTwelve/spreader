package com.nali.spreader.factory.passive;

import com.nali.spreader.factory.base.TaskMachine;
import com.nali.spreader.factory.base.TaskMeta;
import com.nali.spreader.factory.exporter.Exporter;

public interface PassiveTaskProducer<D, TM extends TaskMeta, E extends Exporter<TM>> extends TaskMachine<TM>, PassiveObject {

	void work(D data, E exporter);

}