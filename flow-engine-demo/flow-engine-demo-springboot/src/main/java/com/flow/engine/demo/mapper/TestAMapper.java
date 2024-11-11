package com.flow.engine.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flow.engine.demo.domain.TestADO;
import org.apache.ibatis.annotations.Mapper;


/**
 *
 * @author harley.shi
 * @date 2024-09-26 11:27:36
 */
@Mapper
public interface TestAMapper extends BaseMapper<TestADO> {
}