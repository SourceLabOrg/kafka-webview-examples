package examples.filter;


import org.sourcelab.kafka.webview.ui.plugin.filter.RecordFilter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Example filter that removes low offsets.
 */
public class LowOffsetFilter implements RecordFilter {

    private static final String OPTION_MIN_OFFSET = "Minimum Offset";

    /**
     * Defaults to 600.
     */
    private Long minimumOffset = 600L;

    @Override
    public Set<String> getOptionNames() {
        final Set<String> options = new HashSet<>();
        options.add(OPTION_MIN_OFFSET);
        return options;
    }

    /**
     * Configure this class.
     * @param consumerConfigs Consumer configuration in key/value pairs
     * @param filterOptions User defined filter options.
     */
    @Override
    public void configure(final Map<String, ?> consumerConfigs, final Map<String, String> filterOptions) {
        if (filterOptions.containsKey(OPTION_MIN_OFFSET)) {
            minimumOffset = Long.valueOf(filterOptions.get(OPTION_MIN_OFFSET));
        }
    }

    @Override
    public boolean filter(final String topic, final int partition, final long offset, final Object key, final Object value) {
        return offset > minimumOffset;
    }

    @Override
    public void close() {
        // Not used.
    }
}
