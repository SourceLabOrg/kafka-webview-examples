package examples.filter;

import org.sourcelab.kafka.webview.ui.plugin.filter.RecordFilter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Example filter that only passes records that contain a configured String value.
 */
public class StringSearchFilter implements RecordFilter {
    /**
     * Defines the name of our configurable option to define the filter value.
     */
    private final String OPTION_FILTER_VALUE = "FilterValue";

    /**
     * The configured filter value str.
     */
    private String filterValueStr = null;

    /**
     * Define names of configurable options.
     * These names will be passed up to the User Interface and allow the user to define them.
     * When configure() is called below, these same names will be returned, along with the user defined values,
     * in the filterOptions argument.
     *
     * Since the UI provides no validation on these user defined values, best practices dictate that your implementation
     * should gracefully handle when these are not set.
     *
     * @return Set of option names.
     */
    @Override
    public Set<String> getOptionNames() {
        final Set<String> optionNames = new HashSet<>();
        optionNames.add(OPTION_FILTER_VALUE);
        return optionNames;
    }

    /**
     * Configure this class.
     * @param consumerConfigs Consumer configuration in key/value pairs
     * @param filterOptions User defined filter options.
     */
    @Override
    public void configure(final Map<String, ?> consumerConfigs, final Map<String, String> filterOptions) {
        if (filterOptions.containsKey(OPTION_FILTER_VALUE)) {
            filterValueStr = filterOptions.get(OPTION_FILTER_VALUE);
        } else {
            throw new RuntimeException("Unconfigured Filter Value!");
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
    public boolean includeRecord(final String topic, final int partition, final long offset, final Object key, final Object value) {
        // Sanity check.  This filter only works on String values.
        if (value == null || !(value instanceof String)) {
            // Fail safe, pass if it's not of type String.
            return true;
        }

        // If the value contains our string
        if (((String) value).contains(filterValueStr)) {
            // we should show it.
            return true;
        }
        // Otherwise filter it.
        return false;
    }

    /**
     * Called on closing.
     */
    @Override
    public void close() {
        // No cleanup required.
    }
}
