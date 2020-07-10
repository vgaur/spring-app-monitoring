package com.vgaur.monitoring.util;

public enum FileIdentifier {

    LARGE("LARGE_FILE.csv"), SMALL("SMALL_FILE.csv");

    String classpathResourceName;

    FileIdentifier(String identifier) {
        this.classpathResourceName = identifier;
    }

    public String getClasspathResourceName() {
        return classpathResourceName;
    }

    public static String lookUp(String type) {

        for (FileIdentifier identifier : FileIdentifier.values()) {
            if (identifier.name().equalsIgnoreCase(type)) {
                return identifier.getClasspathResourceName();
            }
        }
        throw new IllegalArgumentException(String.format("Unknow type %s", type));
    }
}
