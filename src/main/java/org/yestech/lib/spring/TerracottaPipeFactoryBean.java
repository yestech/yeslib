/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

/*
 *
 * Author:  Artie Copeland
 * Last Modified Date: $DateTime: $
 */
package org.yestech.lib.spring;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.terracotta.message.pipe.BlockingQueueBasedPipe;
import org.terracotta.message.pipe.Pipe;
import org.terracotta.message.routing.Router;
import org.terracotta.message.topology.DefaultTopology;
import org.terracotta.message.topology.Topology;
import org.terracotta.message.topology.TopologyManager;
import org.terracotta.modules.annotations.Root;
import org.apache.commons.lang.StringUtils;

/**
 * Spring Factory to create a Terracotta Pipe
 * 
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
public class TerracottaPipeFactoryBean implements FactoryBean, InitializingBean {
    @Root
    private Pipe pipe;
    private Router router;
    private String topologyName;
    private String pipeName;
    private int pipeSize = 1000;

    @Override
    public Object getObject() throws Exception {
        return pipe;
    }

    @Override
    public Class getObjectType() {
        return (this.pipe != null ? this.pipe.getClass() : Pipe.class);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public String getTopologyName() {
        return topologyName;
    }

    public String getPipeName() {
        return pipeName;
    }

    public void setPipeName(String pipeName) {
        this.pipeName = pipeName;
    }

    @Required
    public void setTopologyName(String topologyName) {
        this.topologyName = topologyName;
    }

    public int getPipeSize() {
        return pipeSize;
    }

    public void setPipeSize(int pipeSize) {
        this.pipeSize = pipeSize;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("TerracottaPipeFactoryBean pipeSize : " + pipeSize);
        Pipe.Factory pipeFactory = new BlockingQueueBasedPipe.Factory(pipeSize);
        Topology.Factory topologyFactory = new DefaultTopology.Factory(pipeFactory);
        System.out.println("TerracottaPipeFactoryBean topologyName : " + topologyName);
        Topology topology = TopologyManager.getInstance().<String, String>getOrCreateTopology(topologyName, topologyFactory);
//        if (router == null) {
//            router = new LoadBalancingRouter();
//        }

        if (StringUtils.isNotBlank(pipeName)) {
            pipe = topology.getOrCreatePipeFor(pipeName);
        } else {
            pipe = topology.getOrCreatePipeFor(topologyName);
        }
        System.out.println("TerracottaPipeFactoryBean pipe : " + pipe);
    }
}
