package com.baomidou.mybatisplus.core.conditions;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.test.User;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author miemie
 * @since 2021-01-27
 */
class UpdateWrapperTest extends BaseWrapperTest {

    @Test
    void test1() {
        UpdateWrapper<User> wrapper = new UpdateWrapper<User>()
                .set("name", "a", "typeHandler=org.apache.ibatis.type.StringTypeHandler")
                .set("name", "a", "typeHandler=org.apache.ibatis.type.StringTypeHandler,jdbcType=VARCHAR")
                .set("name", "a", "typeHandler=org.apache.ibatis.type.StringTypeHandler,jdbcType=VARCHAR,numericScale=2");
        logSqlSet("ss", wrapper,
                "name=#{ew.paramNameValuePairs.MPGENVAL1,typeHandler=org.apache.ibatis.type.StringTypeHandler}," +
                        "name=#{ew.paramNameValuePairs.MPGENVAL2,typeHandler=org.apache.ibatis.type.StringTypeHandler,jdbcType=VARCHAR}," +
                        "name=#{ew.paramNameValuePairs.MPGENVAL3,typeHandler=org.apache.ibatis.type.StringTypeHandler,jdbcType=VARCHAR,numericScale=2}");
        logParams(wrapper);
    }

    @Test
    void test2() {
        TableInfo tableInfo = TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), Entity.class);
        Assertions.assertEquals("entity", tableInfo.getTableName());

        LambdaUpdateWrapper<Entity> wrapper = new LambdaUpdateWrapper<Entity>()
                .setApply(true, Entity::getName, "JSON_SET(name, '$.name', {0})", "name");
        logSqlSet("ss", wrapper, "username=JSON_SET(name, '$.name', {0})");

    }
}
