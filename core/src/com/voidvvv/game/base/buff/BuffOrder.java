package com.voidvvv.game.base.buff;

public enum BuffOrder {
    ATTR_PLUS("属性增减",1),

    ;

    private final int order;
    private final String des;

    public String getDes() {
        return des;
    }

    public int getOrder() {
        return order;
    }

    BuffOrder(String des, int order) {
        this.des = des;
        this.order = order;
    }
}
