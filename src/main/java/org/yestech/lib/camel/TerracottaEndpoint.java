/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */
package org.yestech.lib.camel;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.camel.Component;
import org.apache.camel.Consumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.terracotta.message.pipe.Pipe;
import org.terracotta.modules.annotations.Root;

/**
 * An implementation of the asynchronous Pipe exchanges on a {@link org.terracotta.message.pipe.Pipe} within a CamelContext
 *
 * A component based on camel SEDA component.
 */
public class TerracottaEndpoint extends DefaultEndpoint {
    @Root
    private Pipe<Object> pipe;
    private int concurrentConsumers = 1;
    private Set<TerracottaProducer> producers = new CopyOnWriteArraySet<TerracottaProducer>();
    private Set<TerracottaConsumer> consumers = new CopyOnWriteArraySet<TerracottaConsumer>();

    public TerracottaEndpoint() {
    }

    public TerracottaEndpoint(String endpointUri, Component component, Pipe<Object> pipe) {
        this(endpointUri, component, pipe, 1);
    }

    public TerracottaEndpoint(String endpointUri, Component component, Pipe<Object> pipe, int concurrentConsumers) {
        super(endpointUri, component);
        this.pipe = pipe;
        this.concurrentConsumers = concurrentConsumers;
    }

    public TerracottaEndpoint(String endpointUri, Pipe<Object> pipe) {
        this(endpointUri, pipe, 1);
    }

    public TerracottaEndpoint(String endpointUri, Pipe<Object> pipe, int concurrentConsumers) {
        super(endpointUri);
        this.pipe = pipe;
        this.concurrentConsumers = concurrentConsumers;
    }

    public Producer createProducer() throws Exception {
        return new TerracottaProducer(this, getPipe());
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        return new TerracottaConsumer(this, processor);
    }

    public Pipe<Object> getPipe() {
        return pipe;
    }

    public void setPipe(Pipe<Object> queue) {
        this.pipe = queue;
    }

    public void setConcurrentConsumers(int concurrentConsumers) {
        this.concurrentConsumers = concurrentConsumers;
    }

    public int getConcurrentConsumers() {
        return concurrentConsumers;
    }

    public boolean isSingleton() {
        return true;
    }

    /**
     * Returns the current active consumers on this endpoint
     */
    public Set<TerracottaConsumer> getConsumers() {
        return new HashSet<TerracottaConsumer>(consumers);
    }

    /**
     * Returns the current active producers on this endpoint
     */
    public Set<TerracottaProducer> getProducers() {
        return new HashSet<TerracottaProducer>(producers);
    }

    void onStarted(TerracottaProducer producer) {
        producers.add(producer);
    }

    void onStopped(TerracottaProducer producer) {
        producers.remove(producer);
    }

    void onStarted(TerracottaConsumer consumer) {
        consumers.add(consumer);
    }

    void onStopped(TerracottaConsumer consumer) {
        consumers.remove(consumer);
    }
}