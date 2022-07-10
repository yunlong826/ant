package com.ant.yun.core.env;

import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;
import com.ant.yun.util.StringUtils;

import java.util.*;
import java.util.function.Predicate;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 14:03
 */
final class ProfilesParser {
    private ProfilesParser() {
    }

    static Profiles parse(String... expressions) {
        Assert.notEmpty(expressions, "Must specify at least one profile");
        Profiles[] parsed = new Profiles[expressions.length];

        for(int i = 0; i < expressions.length; ++i) {
            parsed[i] = parseExpression(expressions[i]);
        }

        return new ProfilesParser.ParsedProfiles(expressions, parsed);
    }

    private static Profiles parseExpression(String expression) {
        Assert.hasText(expression, () -> {
            return "Invalid profile expression [" + expression + "]: must contain text";
        });
        StringTokenizer tokens = new StringTokenizer(expression, "()&|!", true);
        return parseTokens(expression, tokens);
    }

    private static Profiles parseTokens(String expression, StringTokenizer tokens) {
        return parseTokens(expression, tokens, ProfilesParser.Context.NONE);
    }

    private static Profiles parseTokens(String expression, StringTokenizer tokens, ProfilesParser.Context context) {
        List<Profiles> elements = new ArrayList();
        ProfilesParser.Operator operator = null;

        while(true) {
            while(true) {
                String token;
                do {
                    if (!tokens.hasMoreTokens()) {
                        return merge(expression, elements, operator);
                    }

                    token = tokens.nextToken().trim();
                } while(token.isEmpty());

                byte var7 = -1;
                switch(token.hashCode()) {
                    case 33:
                        if (token.equals("!")) {
                            var7 = 3;
                        }
                        break;
                    case 38:
                        if (token.equals("&")) {
                            var7 = 1;
                        }
                        break;
                    case 40:
                        if (token.equals("(")) {
                            var7 = 0;
                        }
                        break;
                    case 41:
                        if (token.equals(")")) {
                            var7 = 4;
                        }
                        break;
                    case 124:
                        if (token.equals("|")) {
                            var7 = 2;
                        }
                }

                switch(var7) {
                    case 0:
                        Profiles contents = parseTokens(expression, tokens, ProfilesParser.Context.BRACKET);
                        if (context == ProfilesParser.Context.INVERT) {
                            return contents;
                        }

                        elements.add(contents);
                        break;
                    case 1:
                        assertWellFormed(expression, operator == null || operator == ProfilesParser.Operator.AND);
                        operator = ProfilesParser.Operator.AND;
                        break;
                    case 2:
                        assertWellFormed(expression, operator == null || operator == ProfilesParser.Operator.OR);
                        operator = ProfilesParser.Operator.OR;
                        break;
                    case 3:
                        elements.add(not(parseTokens(expression, tokens, ProfilesParser.Context.INVERT)));
                        break;
                    case 4:
                        Profiles merged = merge(expression, elements, operator);
                        if (context == ProfilesParser.Context.BRACKET) {
                            return merged;
                        }

                        elements.clear();
                        elements.add(merged);
                        operator = null;
                        break;
                    default:
                        Profiles value = equals(token);
                        if (context == ProfilesParser.Context.INVERT) {
                            return value;
                        }

                        elements.add(value);
                }
            }
        }
    }

    private static Profiles merge(String expression, List<Profiles> elements, @Nullable ProfilesParser.Operator operator) {
        assertWellFormed(expression, !elements.isEmpty());
        if (elements.size() == 1) {
            return (Profiles)elements.get(0);
        } else {
            Profiles[] profiles = (Profiles[])elements.toArray(new Profiles[0]);
            return operator == ProfilesParser.Operator.AND ? and(profiles) : or(profiles);
        }
    }

    private static void assertWellFormed(String expression, boolean wellFormed) {
        Assert.isTrue(wellFormed, () -> {
            return "Malformed profile expression [" + expression + "]";
        });
    }

    private static Profiles or(Profiles... profiles) {
        return (activeProfile) -> {
            return Arrays.stream(profiles).anyMatch(isMatch(activeProfile));
        };
    }

    private static Profiles and(Profiles... profiles) {
        return (activeProfile) -> {
            return Arrays.stream(profiles).allMatch(isMatch(activeProfile));
        };
    }

    private static Profiles not(Profiles profiles) {
        return (activeProfile) -> {
            return !profiles.matches(activeProfile);
        };
    }

    private static Profiles equals(String profile) {
        return (activeProfile) -> {
            return activeProfile.test(profile);
        };
    }

    private static Predicate<Profiles> isMatch(Predicate<String> activeProfile) {
        return (profiles) -> {
            return profiles.matches(activeProfile);
        };
    }

    private static class ParsedProfiles implements Profiles {
        private final Set<String> expressions = new LinkedHashSet();
        private final Profiles[] parsed;

        ParsedProfiles(String[] expressions, Profiles[] parsed) {
            Collections.addAll(this.expressions, expressions);
            this.parsed = parsed;
        }

        public boolean matches(Predicate<String> activeProfiles) {
            Profiles[] var2 = this.parsed;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Profiles candidate = var2[var4];
                if (candidate.matches(activeProfiles)) {
                    return true;
                }
            }

            return false;
        }

        public int hashCode() {
            return this.expressions.hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            } else if (obj == null) {
                return false;
            } else if (this.getClass() != obj.getClass()) {
                return false;
            } else {
                ProfilesParser.ParsedProfiles that = (ProfilesParser.ParsedProfiles)obj;
                return this.expressions.equals(that.expressions);
            }
        }

        public String toString() {
            return StringUtils.collectionToDelimitedString(this.expressions, " or ");
        }
    }

    private static enum Context {
        NONE,
        INVERT,
        BRACKET;

        private Context() {
        }
    }

    private static enum Operator {
        AND,
        OR;

        private Operator() {
        }
    }
}
