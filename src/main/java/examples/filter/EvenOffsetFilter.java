package examples.filter;

import com.darksci.kafka.webview.ui.plugin.filter.RecordFilter;

import java.util.Map;

/**
 * Example filter that filters even numbered offsets.
 */
public class EvenOffsetFilter implements RecordFilter {
    @Override
    public boolean filter(final String topic, final int partition, final long offset, final Object key, final Object value) {
        return (offset % 2 == 0);
    }

    @Override
    public void configure(final Map<String, ?> configs) {

    }
}