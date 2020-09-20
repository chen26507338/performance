
package com.stylefeng.guns.modular.system.dict;

import com.stylefeng.guns.common.constant.dictmap.base.AbstractDictMap;

public class OtherInfoDict extends AbstractDictMap {
    public OtherInfoDict() {
    }

    public void init() {
        this.put("id", "id");
        this.put("otherKey", "键名");
        this.put("otherValue", "值");
        this.put("beizhu", "备注");
    }

    protected void initBeWrapped() {
    }
}
