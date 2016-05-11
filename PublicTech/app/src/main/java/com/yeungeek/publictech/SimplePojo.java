package com.yeungeek.publictech;

import com.yeungeek.annotation.PojoString;

/**
 * Created by yeungeek on 2016/4/27.
 */
@PojoString
public class SimplePojo {
    public String s1;
    public String s2;

    public SimplePojo(String s1, String s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    @Override
    public String toString() {
        return StringUtil.createString(this);
    }
}
