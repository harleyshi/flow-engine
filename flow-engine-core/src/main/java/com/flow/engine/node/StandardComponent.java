package com.flow.engine.node;

import com.flow.engine.model.FlowCtx;
import com.flow.engine.operator.Operator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 标准组件构造器
 * @author harley.shi
 * @date 2024/7/1
 */
@Slf4j
@Getter
@Setter
public class StandardComponent<C extends FlowCtx> extends AbstractComponent<C> {
    /**
     * 组件描述
     */
    private String desc;

    /**
     * 算子
     */
    private Operator<C> operator;

    /**
     * 回滚组件
     */
    private Operator<C> rollback;

    public StandardComponent(String name) {
        super(name);
    }

    public StandardComponent(String name, Operator<C> operator) {
        super(name);
        this.operator = operator;
    }

    @Override
    public void doExecute(C context) {
        try {
            operator.exec(context);
        }finally {
            if(rollback != null){
                context.addRollback(rollback);
            }
        }
    }
}
