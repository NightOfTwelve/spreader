package com.nali.spreader.factory.regular;

import org.quartz.Trigger;

public interface Schedular<P> {
	void schedule(P params, Trigger trigger);
}
