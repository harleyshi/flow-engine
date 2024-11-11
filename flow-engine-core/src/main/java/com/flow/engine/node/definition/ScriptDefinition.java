package com.flow.engine.node.definition;

import com.flow.engine.utils.AssertUtil;
import lombok.Data;

/**
 * @author harley.shi
 * @date 2024/10/28
 */
@Data
public class ScriptDefinition implements Validator {
    private String name;

    private String type;

    private String script;

    @Override
    public void validate() {
        AssertUtil.notBlank(name, String.format("%s pipeline [name] cannot be blank", name));
        AssertUtil.notBlank(script, String.format("%s script [script] cannot be blank", name));
    }
}
