package com.ll.wiseSaying;

import java.util.HashMap;
import java.util.regex.Matcher;

public class Rq {
    private final RegexPatterns regex_ptns;
    private Command cmd;
    private HashMap<String, String> params;

    public Rq(String input_str) {
        this.regex_ptns = new RegexPatterns();
        String[] args = input_str.split("\\?");

        this.cmd = Command.changeNameToCmd(args[0]);
        if(cmd.isCmdErrored()) return;

        if (args.length >= 2) {
            String[] args_without_cmd = args[1].split("&");
            this.params = getParams(args_without_cmd);
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

    private String getStringParam(String key, String DEFAULT_VALUE) {
        if (params != null) return params.getOrDefault(key, DEFAULT_VALUE);
        return DEFAULT_VALUE;
    }
    private HashMap<String, String> getParams(String[] args) {
        HashMap<String, String> new_params = new HashMap<>();

        for (String arg : args) {
            for (String key : regex_ptns.getKeyList()) {
                Matcher matcher = regex_ptns.getPatternMatcher(key, arg);
                boolean isMapContainsKey = new_params.containsKey(key);
                var founded = matcher.find();

                if (isMapContainsKey && founded) {
                    cmd = Command.ERROR;
                    return null;
                }
                if (!isMapContainsKey && founded)
                    new_params.put(matcher.group(1), matcher.group(2));
            }
        }
        return new_params;
    }
}
