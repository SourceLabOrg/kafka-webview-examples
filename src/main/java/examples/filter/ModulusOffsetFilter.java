package examples.filter;

import org.sourcelab.kafka.webview.ui.plugin.filter.RecordFilter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Example filter that filters messages by looking at the offset.  It can take in an optional
 * filterOptions configuration value and use it to calculate the modulus of the offset.
 */
public class ModulusOffsetFilter implements RecordFilter {
    private static final String OPTION_MODULUS = "modulus";

    /**
     * By default filter even.
     */
    private Integer modulus = 2;

    @Override
    public Set<String> getOptionNames() {
        final Set<String> options = new HashSet<>();
        options.add(OPTION_MODULUS);
        return options;
    }

    /**
     * Configure this class.
     * @param consumerConfigs Consumer configuration in key/value pairs
     * @param filterOptions User defined filter options.
     */
    @Override
    public void configure(final Map<String, ?> consumerConfigs, final Map<String, String> filterOptions) {
        if (filterOptions.containsKey(OPTION_MODULUS)) {
            modulus = Integer.valueOf(filterOptions.get(OPTION_MODULUS));
        }
    }

    /**
     * Define the filter behavior.
     * A return value of TRUE means the record WILL be shown.
     * A return value of FALSE means the record will NOT be shown.
     *
     * @param topic Name of topic the message came from.
     * @param partition Partition the message came from.
     * @param offset Offset the message came from.
     * @param key Deserialized Key object.
     * @param value Deserialized Value object.
     * @return True means the record WILL be shown.  False means the record will NOT be shown.
     */
    @Override
    public boolean filter(final String topic, final int partition, final long offset, final Object key, final Object value) {
        return offset % modulus == 0;
    }

    /**
     * Called on closing.
     */
    @Override
    public void close() {
        // Nothing to do here!
    }
}
