package com.xu.xnetwork.request;

import com.xu.xnetwork.util.HttpUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by Xu on 2016/11/19.
 */

public final class Header {
    private final String[] namesAndValues;

    private Header(Builder builder) {
        this.namesAndValues = builder.namesAndValues.toArray(new String[builder.namesAndValues.size()]);
    }

    private Header(String[] namesAndValues) {
        this.namesAndValues = namesAndValues;
    }

    public String get(String name) {
        return get(namesAndValues, name);
    }

    public int size() {
        return namesAndValues.length / 2;
    }

    public String name(int index) {
        return namesAndValues[index * 2];
    }

    public String value(int index) {
        return namesAndValues[index * 2 + 1];
    }

    /** Returns an immutable case-insensitive set of header names. */
    public Set<String> names() {
        TreeSet<String> result = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0, size = size(); i < size; i++) {
            result.add(name(i));
        }
        return Collections.unmodifiableSet(result);
    }

    /** Returns an immutable list of the header values for {@code name}. */
    public List<String> values(String name) {
        List<String> result = null;
        for (int i = 0, size = size(); i < size; i++) {
            if (name.equalsIgnoreCase(name(i))) {
                if (result == null) result = new ArrayList<>(2);
                result.add(value(i));
            }
        }
        return result != null
                ? Collections.unmodifiableList(result)
                : Collections.<String>emptyList();
    }

    public Builder newBuilder() {
        Builder result = new Builder();
        Collections.addAll(result.namesAndValues, namesAndValues);
        return result;
    }

    /**
     * Returns true if {@code other} is a {@code Header} object with the same headers, with the same
     * casing, in the same order. Note that two headers instances may be <i>semantically</i> equal
     * but not equal according to this method. In particular, none of the following sets of headers
     * are equal according to this method: <pre>   {@code
     *
     *   1. Original
     *   Content-Type: text/html
     *   Content-Length: 50
     *
     *   2. Different order
     *   Content-Length: 50
     *   Content-Type: text/html
     *
     *   3. Different case
     *   content-type: text/html
     *   content-length: 50
     *
     *   4. Different values
     *   Content-Type: text/html
     *   Content-Length: 050
     * }</pre>
     *
     * Applications that require semantically equal headers should convert them into a canonical form
     * before comparing them for equality.
     */
    @Override public boolean equals(Object other) {
        return other instanceof Header
                && Arrays.equals(((Header) other).namesAndValues, namesAndValues);
    }

    @Override public int hashCode() {
        return Arrays.hashCode(namesAndValues);
    }

    @Override public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0, size = size(); i < size; i++) {
            result.append(name(i)).append(": ").append(value(i)).append("\n");
        }
        return result.toString();
    }

    public Map<String, List<String>> toMultimap() {
        Map<String, List<String>> result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0, size = size(); i < size; i++) {
            String name = name(i).toLowerCase(Locale.US);
            List<String> values = result.get(name);
            if (values == null) {
                values = new ArrayList<>(2);
                result.put(name, values);
            }
            values.add(value(i));
        }
        return result;
    }

    private static String get(String[] namesAndValues, String name) {
        for (int i = namesAndValues.length - 2; i >= 0; i -= 2) {
            if (name.equalsIgnoreCase(namesAndValues[i])) {
                return namesAndValues[i + 1];
            }
        }
        return null;
    }

    /**
     * Returns headers for the alternating header names and values. There must be an even number of
     * arguments, and they must alternate between header names and values.
     */
    public static Header of(String... namesAndValues) {
        if (namesAndValues == null) throw new NullPointerException("namesAndValues == null");
        if (namesAndValues.length % 2 != 0) {
            throw new IllegalArgumentException("Expected alternating header names and values");
        }

        // Make a defensive copy and clean it up.
        namesAndValues = namesAndValues.clone();
        for (int i = 0; i < namesAndValues.length; i++) {
            if (namesAndValues[i] == null) throw new IllegalArgumentException("Header cannot be null");
            namesAndValues[i] = namesAndValues[i].trim();
        }

        // Check for malformed headers.
        for (int i = 0; i < namesAndValues.length; i += 2) {
            String name = namesAndValues[i];
            String value = namesAndValues[i + 1];
            if (name.length() == 0 || name.indexOf('\0') != -1 || value.indexOf('\0') != -1) {
                throw new IllegalArgumentException("Unexpected header: " + name + ": " + value);
            }
        }

        return new Header(namesAndValues);
    }

    /**
     * Returns headers for the header names and values in the {@link Map}.
     */
    public static Header of(Map<String, String> headers) {
        if (headers == null) throw new NullPointerException("headers == null");

        // Make a defensive copy and clean it up.
        String[] namesAndValues = new String[headers.size() * 2];
        int i = 0;
        for (Map.Entry<String, String> header : headers.entrySet()) {
            if (header.getKey() == null || header.getValue() == null) {
                throw new IllegalArgumentException("Header cannot be null");
            }
            String name = header.getKey().trim();
            String value = header.getValue().trim();
            if (name.length() == 0 || name.indexOf('\0') != -1 || value.indexOf('\0') != -1) {
                throw new IllegalArgumentException("Unexpected header: " + name + ": " + value);
            }
            namesAndValues[i] = name;
            namesAndValues[i + 1] = value;
            i += 2;
        }

        return new Header(namesAndValues);
    }

    public static final class Builder {
        private final List<String> namesAndValues = new ArrayList<>(20);

        public Builder add(String line) {
            int index = line.indexOf(":");
            if (index == -1) {
                throw new IllegalArgumentException("Unexpected header: " + line);
            }
            return add(line.substring(0, index).trim(), line.substring(index + 1));
        }

        public Builder add(String name, String value) {
            checkNameAndValue(name, value);
            return addLast(name, value);
        }

        /**
         * Add a field with the specified value without any validation. Only appropriate for headers
         * from the remote peer or cache.
         */
        Builder addLast(String name, String value) {
            namesAndValues.add(name);
            namesAndValues.add(value.trim());
            return this;
        }

        public Builder removeAll(String name) {
            for (int i = 0; i < namesAndValues.size(); i += 2) {
                if (name.equalsIgnoreCase(namesAndValues.get(i))) {
                    namesAndValues.remove(i); // name
                    namesAndValues.remove(i); // value
                    i -= 2;
                }
            }
            return this;
        }

        public Builder set(String name, String value) {
            checkNameAndValue(name, value);
            removeAll(name);
            addLast(name, value);
            return this;
        }

        private void checkNameAndValue(String name, String value) {
            if (name == null) throw new NullPointerException("name == null");
            if (name.isEmpty()) throw new IllegalArgumentException("name is empty");
            for (int i = 0, length = name.length(); i < length; i++) {
                char c = name.charAt(i);
                if (c <= '\u001f' || c >= '\u007f') {
                    throw new IllegalArgumentException(HttpUtils.format(
                            "Unexpected char %#04x at %d in header name: %s", (int) c, i, name));
                }
            }
            if (value == null) throw new NullPointerException("value == null");
            for (int i = 0, length = value.length(); i < length; i++) {
                char c = value.charAt(i);
                if ((c <= '\u001f' && c != '\u0009' /* htab */) || c >= '\u007f') {
                    throw new IllegalArgumentException(HttpUtils.format(
                            "Unexpected char %#04x at %d in %s value: %s", (int) c, i, name, value));
                }
            }
        }

        public Header build() {
            return new Header(this);
        }
    }
}
