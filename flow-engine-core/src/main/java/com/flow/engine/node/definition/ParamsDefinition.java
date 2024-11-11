package com.flow.engine.node.definition;

import com.flow.engine.utils.AssertUtil;
import lombok.Data;

/**
 * @author harley.shi
 * @date 2024/10/28
 */
@Data
public class ParamsDefinition implements Validator {
    private String name;

    private String desc;

    private String params;

    @Override
    public void validate() {
        AssertUtil.notBlank(name, String.format("%s params [name] cannot be blank", name));
    }
}
