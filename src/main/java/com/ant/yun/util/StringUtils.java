package com.ant.yun.util;

import com.ant.yun.lang.Nullable;

import java.util.*;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 10:55
 */
public abstract class StringUtils {

    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    public static String trimAllWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        } else {
            int len = str.length();
            StringBuilder sb = new StringBuilder(str.length());

            for(int i = 0; i < len; ++i) {
                char c = str.charAt(i);
                if (!Character.isWhitespace(c)) {
                    sb.append(c);
                }
            }

            return sb.toString();
        }
    }

    public static TimeZone parseTimeZoneString(String timeZoneString) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
        if ("GMT".equals(timeZone.getID()) && !timeZoneString.startsWith("GMT")) {
            throw new IllegalArgumentException("Invalid time zone specification '" + timeZoneString + "'");
        } else {
            return timeZone;
        }
    }

    @Nullable
    public static Locale parseLocaleString(String localeString) {
        return parseLocaleTokens(localeString, tokenizeLocaleSource(localeString));
    }
    private static String[] tokenizeLocaleSource(String localeSource) {
        return tokenizeToStringArray(localeSource, "_ ", false, false);
    }

    @Nullable
    private static Locale parseLocaleTokens(String localeString, String[] tokens) {
        String language = tokens.length > 0 ? tokens[0] : "";
        String country = tokens.length > 1 ? tokens[1] : "";
        validateLocalePart(language);
        validateLocalePart(country);
        String variant = "";
        if (tokens.length > 2) {
            int endIndexOfCountryCode = localeString.indexOf(country, language.length()) + country.length();
            variant = trimLeadingWhitespace(localeString.substring(endIndexOfCountryCode));
            if (variant.startsWith("_")) {
                variant = trimLeadingCharacter(variant, '_');
            }
        }

        if (variant.isEmpty() && country.startsWith("#")) {
            variant = country;
            country = "";
        }

        return language.length() > 0 ? new Locale(language, country, variant) : null;
    }

    public static String trimLeadingWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        } else {
            StringBuilder sb = new StringBuilder(str);

            while(sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
                sb.deleteCharAt(0);
            }

            return sb.toString();
        }
    }

    private static void validateLocalePart(String localePart) {
        for(int i = 0; i < localePart.length(); ++i) {
            char ch = localePart.charAt(i);
            if (ch != ' ' && ch != '_' && ch != '-' && ch != '#' && !Character.isLetterOrDigit(ch)) {
                throw new IllegalArgumentException("Locale part \"" + localePart + "\" contains invalid characters");
            }
        }

    }
    public static String trimLeadingCharacter(String str, char leadingCharacter) {
        if (!hasLength(str)) {
            return str;
        } else {
            StringBuilder sb = new StringBuilder(str);

            while(sb.length() > 0 && sb.charAt(0) == leadingCharacter) {
                sb.deleteCharAt(0);
            }

            return sb.toString();
        }
    }

    public static boolean hasLength(@Nullable String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean hasText(@Nullable String str) {
        return str != null && !str.isEmpty() && containsText(str);
    }
    private static boolean containsText(CharSequence str) {
        int strLen = str.length();

        for(int i = 0; i < strLen; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }
    public static String replace(String inString, String oldPattern, @Nullable String newPattern) {
        if (hasLength(inString) && hasLength(oldPattern) && newPattern != null) {
            int index = inString.indexOf(oldPattern);
            if (index == -1) {
                return inString;
            } else {
                int capacity = inString.length();
                if (newPattern.length() > oldPattern.length()) {
                    capacity += 16;
                }

                StringBuilder sb = new StringBuilder(capacity);
                int pos = 0;

                for(int patLen = oldPattern.length(); index >= 0; index = inString.indexOf(oldPattern, pos)) {
                    sb.append(inString, pos, index);
                    sb.append(newPattern);
                    pos = index + patLen;
                }

                sb.append(inString, pos, inString.length());
                return sb.toString();
            }
        } else {
            return inString;
        }
    }

    public static String cleanPath(String path) {
        if (!hasLength(path)) {
            return path;
        } else {
            String pathToUse = replace(path, "\\", "/");
            if (pathToUse.indexOf(46) == -1) {
                return pathToUse;
            } else {
                int prefixIndex = pathToUse.indexOf(58);
                String prefix = "";
                if (prefixIndex != -1) {
                    prefix = pathToUse.substring(0, prefixIndex + 1);
                    if (prefix.contains("/")) {
                        prefix = "";
                    } else {
                        pathToUse = pathToUse.substring(prefixIndex + 1);
                    }
                }

                if (pathToUse.startsWith("/")) {
                    prefix = prefix + "/";
                    pathToUse = pathToUse.substring(1);
                }

                String[] pathArray = delimitedListToStringArray(pathToUse, "/");
                Deque<String> pathElements = new ArrayDeque();
                int tops = 0;

                int i;
                for(i = pathArray.length - 1; i >= 0; --i) {
                    String element = pathArray[i];
                    if (!".".equals(element)) {
                        if ("..".equals(element)) {
                            ++tops;
                        } else if (tops > 0) {
                            --tops;
                        } else {
                            pathElements.addFirst(element);
                        }
                    }
                }

                if (pathArray.length == pathElements.size()) {
                    return prefix + pathToUse;
                } else {
                    for(i = 0; i < tops; ++i) {
                        pathElements.addFirst("..");
                    }

                    if (pathElements.size() == 1 && ((String)pathElements.getLast()).isEmpty() && !prefix.endsWith("/")) {
                        pathElements.addFirst(".");
                    }

                    return prefix + collectionToDelimitedString(pathElements, "/");
                }
            }
        }
    }
    public static String[] delimitedListToStringArray(@Nullable String str, @Nullable String delimiter) {
        return delimitedListToStringArray(str, delimiter, (String)null);
    }

    public static String[] delimitedListToStringArray(@Nullable String str, @Nullable String delimiter, @Nullable String charsToDelete) {
        if (str == null) {
            return EMPTY_STRING_ARRAY;
        } else if (delimiter == null) {
            return new String[]{str};
        } else {
            List<String> result = new ArrayList();
            int pos;
            if (delimiter.isEmpty()) {
                for(pos = 0; pos < str.length(); ++pos) {
                    result.add(deleteAny(str.substring(pos, pos + 1), charsToDelete));
                }
            } else {
                int delPos;
                for(pos = 0; (delPos = str.indexOf(delimiter, pos)) != -1; pos = delPos + delimiter.length()) {
                    result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
                }

                if (str.length() > 0 && pos <= str.length()) {
                    result.add(deleteAny(str.substring(pos), charsToDelete));
                }
            }

            return toStringArray((Collection)result);
        }
    }
    public static String deleteAny(String inString, @Nullable String charsToDelete) {
        if (hasLength(inString) && hasLength(charsToDelete)) {
            int lastCharIndex = 0;
            char[] result = new char[inString.length()];

            for(int i = 0; i < inString.length(); ++i) {
                char c = inString.charAt(i);
                if (charsToDelete.indexOf(c) == -1) {
                    result[lastCharIndex++] = c;
                }
            }

            if (lastCharIndex == inString.length()) {
                return inString;
            } else {
                return new String(result, 0, lastCharIndex);
            }
        } else {
            return inString;
        }
    }
    public static String collectionToDelimitedString(@Nullable Collection<?> coll, String delim) {
        return collectionToDelimitedString(coll, delim, "", "");
    }

    public static String collectionToDelimitedString(@Nullable Collection<?> coll, String delim, String prefix, String suffix) {
        if (CollectionUtils.isEmpty(coll)) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            Iterator it = coll.iterator();

            while(it.hasNext()) {
                sb.append(prefix).append(it.next()).append(suffix);
                if (it.hasNext()) {
                    sb.append(delim);
                }
            }

            return sb.toString();
        }
    }

    public static String[] toStringArray(@Nullable Collection<String> collection) {
        return !CollectionUtils.isEmpty(collection) ? (String[])collection.toArray(EMPTY_STRING_ARRAY) : EMPTY_STRING_ARRAY;
    }
    public static String applyRelativePath(String path, String relativePath) {
        int separatorIndex = path.lastIndexOf("/");
        if (separatorIndex != -1) {
            String newPath = path.substring(0, separatorIndex);
            if (!relativePath.startsWith("/")) {
                newPath = newPath + "/";
            }

            return newPath + relativePath;
        } else {
            return relativePath;
        }
    }

    @Nullable
    public static String getFilename(@Nullable String path) {
        if (path == null) {
            return null;
        } else {
            int separatorIndex = path.lastIndexOf("/");
            return separatorIndex != -1 ? path.substring(separatorIndex + 1) : path;
        }
    }
    public static String arrayToDelimitedString(@Nullable Object[] arr, String delim) {
        if (ObjectUtils.isEmpty(arr)) {
            return "";
        } else if (arr.length == 1) {
            return ObjectUtils.nullSafeToString(arr[0]);
        } else {
            StringJoiner sj = new StringJoiner(delim);
            Object[] var3 = arr;
            int var4 = arr.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Object o = var3[var5];
                sj.add(String.valueOf(o));
            }

            return sj.toString();
        }
    }
    public static String arrayToCommaDelimitedString(@Nullable Object[] arr) {
        return arrayToDelimitedString(arr, ",");
    }

    public static String[] tokenizeToStringArray(String localeSource, String s) {
        return tokenizeToStringArray(localeSource, "_ ", false, false);
    }
    public static String[] tokenizeToStringArray(@Nullable String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {
        if (str == null) {
            return EMPTY_STRING_ARRAY;
        } else {
            StringTokenizer st = new StringTokenizer(str, delimiters);
            ArrayList tokens = new ArrayList();

            while(true) {
                String token;
                do {
                    if (!st.hasMoreTokens()) {
                        return toStringArray((Collection)tokens);
                    }

                    token = st.nextToken();
                    if (trimTokens) {
                        token = token.trim();
                    }
                } while(ignoreEmptyTokens && token.length() <= 0);

                tokens.add(token);
            }
        }
    }
    public static String[] commaDelimitedListToStringArray(@Nullable String str) {
        return delimitedListToStringArray(str, ",");
    }

    public static Set<String> commaDelimitedListToSet(@Nullable String str) {
        String[] tokens = commaDelimitedListToStringArray(str);
        return new LinkedHashSet(Arrays.asList(tokens));
    }
    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }
    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (!hasLength(str)) {
            return str;
        } else {
            char baseChar = str.charAt(0);
            char updatedChar;
            if (capitalize) {
                updatedChar = Character.toUpperCase(baseChar);
            } else {
                updatedChar = Character.toLowerCase(baseChar);
            }

            if (baseChar == updatedChar) {
                return str;
            } else {
                char[] chars = str.toCharArray();
                chars[0] = updatedChar;
                return new String(chars, 0, chars.length);
            }
        }
    }
    public static String collectionToCommaDelimitedString(@Nullable Collection<?> coll) {
        return collectionToDelimitedString(coll, ",");
    }

    public static String[] trimArrayElements(String[] array) {
        if (ObjectUtils.isEmpty(array)) {
            return array;
        } else {
            String[] result = new String[array.length];

            for(int i = 0; i < array.length; ++i) {
                String element = array[i];
                result[i] = element != null ? element.trim() : null;
            }

            return result;
        }
    }
}
