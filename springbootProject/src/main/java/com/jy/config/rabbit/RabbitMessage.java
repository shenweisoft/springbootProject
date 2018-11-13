package com.jy.config.rabbit;

public class RabbitMessage {

    /**
     * 约定的几个消息源名称
     */
    private String source;
    /**
     * 主键
     */
    private String primaryKey;
    /**
     * 消息实体bean
     */
    private Object message;


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RabbitMessage{" +
                "source='" + source + '\'' +
                ", primaryKey='" + primaryKey + '\'' +
                ", message=" + message +
                '}';
    }
}
