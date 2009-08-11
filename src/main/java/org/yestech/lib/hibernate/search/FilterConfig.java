package org.yestech.lib.hibernate.search;

import java.util.HashMap;
import java.util.Map;

/**
 * @author A.J. Wright
 */
public class FilterConfig {

    private String filterName;
    private Map<String,Object> parameters = new HashMap<String, Object>();

    public FilterConfig(String filterName) {
        this.filterName = filterName;
    }

    public FilterConfig(String filterName, Map<String, Object> parameters) {
        this.filterName = filterName;
        this.parameters = parameters;
    }

    public String getFilterName() {
        return filterName;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public FilterConfig addParameter(String key, Object value) {
        parameters.put(key, value);
        return this;
    }
}
