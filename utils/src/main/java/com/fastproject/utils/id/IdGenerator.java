package com.fastproject.utils.id;

public class IdGenerator {

    private static final SnowflakeIdGenerator snowflake =
            new SnowflakeIdGenerator(1, 1);

    public static long nextId() {
        return snowflake.nextId();
    }
}