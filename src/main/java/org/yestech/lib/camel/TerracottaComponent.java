/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */
package org.yestech.lib.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.apache.commons.lang.StringUtils;
import org.terracotta.message.pipe.BlockingQueueBasedPipe;
import org.terracotta.message.pipe.Pipe;
import org.terracotta.message.topology.DefaultTopology;
import org.terracotta.message.topology.Topology;
import org.terracotta.message.topology.TopologyManager;

import java.util.Map;

/**
 * An implementation of a camel component for asynchronous PIPE exchanges on a
 * {@link org.terracotta.message.pipe.Pipe} within a CamelContext
 * <br/>
 * Options:
 * <ul>
 * <li>size - size of the pipe to create if it doesnt exist (Optional)</li>
 * <li>topologyName - name of the Pipe topology (Required) must match producer</li>
 * <li>pipeName - name of the Pipe to create or use (Optional) must match producer if supplied, if not supplied topologyName is used</li>
 * <li>concurrentConsumers - number of concurrentConsumers to listen to pipe (Optional) default = 1</li>
 * </ul>
 * 
 * A component based on camel SEDA component.
 */
@SuppressWarnings("unchecked")
public class TerracottaComponent extends DefaultComponent {

    public Pipe<Object> createPipe(String uri, Map parameters) {
        int pipeSize = (Integer)getAndRemoveParameter(parameters, "size", Integer.class, 1000);

        String topologyName = (String)getAndRemoveParameter(parameters, "topologyName", String.class);

        String pipeName = (String)getAndRemoveParameter(parameters, "pipeName", String.class);
        Pipe<Object> pipe;
        Pipe.Factory pipeFactory = new BlockingQueueBasedPipe.Factory(pipeSize);
        Topology.Factory topologyFactory = new DefaultTopology.Factory(pipeFactory);
        Topology topology = TopologyManager.getInstance().<String, String>getOrCreateTopology(topologyName, topologyFactory);
//        if (router == null) {
//            router = new LoadBalancingRouter();
//        }

        if (StringUtils.isNotBlank(pipeName)) {
            pipe = topology.getOrCreatePipeFor(pipeName);
        } else {
            pipe = topology.getOrCreatePipeFor(topologyName);
        }
        return pipe;
    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map parameters) throws Exception {
        int consumers = (Integer)getAndRemoveParameter(parameters, "concurrentConsumers", Integer.class, 1);
        return new TerracottaEndpoint(uri, this, createPipe(uri, parameters), consumers);
    }
}