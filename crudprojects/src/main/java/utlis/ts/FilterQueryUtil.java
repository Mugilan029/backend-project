package utlis.ts;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilterQueryUtil {

    /**
     * Parses a list of filterQuery strings into a Map of key -> list of values.
     * Example input: ["query:[chatgpt]", "level:[BEGINNER,INTERMEDIATE]"]
     * Example Usage:
     *
     *   Map<String, List<String>> filters = FilterQueryUtil.parseFilterQuery(filterQuery);

     // Single-value usage
     String query = filters.getOrDefault("query", List.of("")).get(0);

     // Multi-value usage
     List<String> levels = filters.getOrDefault("level", List.of());
     List<String> ratings = filters.getOrDefault("averageRating", List.of());

     */
    public static Map<String, List<String>> parseFilterQuery(List<String> filterQuery) {
        Map<String, List<String>> filters = new HashMap<>();

        if (filterQuery == null) return filters;

        for (String filter : filterQuery) {
            if (filter == null || !filter.contains(":")) continue;

            String[] parts = filter.split(":", 2);
            if (parts.length != 2) continue;

            String key = parts[0].trim();
            String valueRaw = parts[1].trim().replaceAll("[\\[\\]]", ""); // remove brackets

            List<String> values = Arrays.stream(valueRaw.split(","))
                    .map(String::trim)
                    .filter(v -> !v.isEmpty())
                    .toList();

            filters.put(key, values);
        }

        return filters;
    }

    /**
     * Parses a single filterQuery String (from a query param) into a Map of key -> list of values.
     *
     * Expected URL-encoded input format:
     *   filterQuery=name:%5Bram%5D%7CdepartmentId:%5Buuid1,uuid2%5D
     *
     * Which decodes to:
     *   "name:[ram]|departmentId:[uuid1,uuid2]"
     *
     * Each filter is separated by "|" (pipe), and values are enclosed in square brackets.
     * Multiple values are comma-separated within the brackets.
     *
     * Example output:
     *   {
     *     "name": ["ram"],
     *     "departmentId": ["uuid1", "uuid2"]
     *   }
     *
     * Map<String, List<String>> filters = FilterQueryUtil.parseFilterQuery(filterQuery);

     // Single-value usage
     String query = filters.getOrDefault("query", List.of("")).get(0);

     // Multi-value usage
     List<String> levels = filters.getOrDefault("level", List.of());
     List<String> ratings = filters.getOrDefault("averageRating", List.of());
     *
     * @param filterQuery the raw query string from the request (after URL-decoding)
     * @return a Map<String, List<String>> representing parsed filters and their values
     */
    public static Map<String, List<String>> parseFilterQuery(String filterQuery) {
        Map<String, List<String>> filters = new HashMap<>();

        // Handle null or blank input early
        if (filterQuery == null || filterQuery.isBlank()) {
            return filters;
        }

        // Split the string by "|" to extract individual filters like "key:[value1,value2]"
        String[] filterArray = filterQuery.split("\\|");

        for (String filter : filterArray) {
            if (filter == null || !filter.contains(":")) continue;

            // Split into key and value using the first colon
            String[] parts = filter.split(":", 2);
            if (parts.length != 2) continue;

            String key = parts[0].trim();

            // Remove square brackets from the value part
            String valueRaw = parts[1].trim().replaceAll("[\\[\\]]", "");

            // Split by comma to get multiple values, then trim and filter empty ones
            List<String> values = Arrays.stream(valueRaw.split(","))
                    .map(String::trim)
                    .filter(v -> !v.isEmpty())
                    .toList();

            filters.put(key, values);
        }

        return filters;
    }

    /**
     * Convert From -> List<String> TO => [string, string]
     */
    public static String listToStringArrayLiteral(List<String> items) {
        return "[" + items.stream()
                .collect(Collectors.joining(", ")) + "]";
    }

}
