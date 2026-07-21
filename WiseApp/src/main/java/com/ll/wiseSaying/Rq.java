package com.ll.wiseSaying;

import java.util.HashMap;
import java.util.regex.Matcher;

public class Rq {
    private final RegexPatterns regexPatterns;
    private Command cmd;
    private HashMap<String, String> params;

    public Rq(String inputStr) {
        this.regexPatterns = new RegexPatterns();
        String[] args = inputStr.split("\\?");

        this.cmd = Command.changeNameToCmd(args[0]);
        if(cmd.isCmdErrored()) return;

        if (args.length >= 2) {
            String[] argsWithoutCmd = args[1].split("&");
            this.params = getParams(argsWithoutCmd);
        }
    }

    public Command getCmd() { return cmd; }

    public boolean doesRequestContainsParam(String key) {
        if (params != null) return params.containsKey(key);
        return false;
    }
    public String getStringParam(String key) {
        return getStringParam(key, "");
    }
    public int getIntParam(String key) {
        return switch(key) {
            case "id" -> Integer.parseInt(getStringParam(key, "-1"));
            case "page" -> Integer.parseInt(getStringParam(key, "1"));
            default -> throw new IllegalStateException("Unexpected value: " + key);
        };
    }

    private String getStringParam(String key, String defaultValue) {
        if (params != null) return params.getOrDefault(key, defaultValue);
        return defaultValue;
    }
    private HashMap<String, String> getParams(String[] args) {
        HashMap<String, String> newParams = new HashMap<>();

        for (String arg : args) {
            for (String key : regexPatterns.getKeyList()) {
                Matcher matcher = regexPatterns.getPatternMatcher(key, arg);
                boolean isMapContainsKey = newParams.containsKey(key);
                var founded = matcher.find();

                if (isMapContainsKey && founded) {
                    cmd = Command.ERROR;
                    return null;
                }
                if (!isMapContainsKey && founded)
                    newParams.put(matcher.group(1), matcher.group(2));
            }
        }
        return newParams;
    }
}
