package com.flow.engine.demo.functions;

import com.alibaba.fastjson2.JSON;
import com.flow.engine.demo.context.OrderContext;
import com.flow.engine.demo.domain.TestADO;
import com.flow.engine.demo.domain.TestBDO;
import com.flow.engine.demo.domain.TestCDO;
import com.flow.engine.demo.functions.params.ParamsCheck;
import com.flow.engine.demo.mapper.TestAMapper;
import com.flow.engine.demo.mapper.TestBMapper;
import com.flow.engine.demo.mapper.TestCMapper;
import com.flow.engine.operator.ComponentFn;
import com.flow.engine.operator.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;


@Service
public class OrderFunctions {

    @Autowired
    private TestAMapper testAMapper;
    @Autowired
    private TestBMapper testBMapper;
    @Autowired
    private TestCMapper testCMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @ComponentFn(name = "saveOrderInfo")
    public Operator<OrderContext> saveOrderInfo(String config){
        return (ctx) -> {
            transactionTemplate.execute(status -> {
                testAMapper.insert(new TestADO("testA"));
                testBMapper.insert(new TestBDO("testB"));
                int i = 1 / 0;
                testCMapper.insert(new TestCDO("testC"));

                return true;
            });

        };
    }

//    @ComponentFn(name = "saveOrderInfo")
//    @Transactional
//    public Operator<Boolean, OrderContext> saveOrderInfo(String config){
//        return (ctx) -> {
//            System.out.println("invoke saveOrderInfo");
//            testAMapper.insert(new TestADO("testA"));
//            testBMapper.insert(new TestBDO("testB"));
//            int i = 1/0;
//            testCMapper.insert(new TestCDO("testC"));
//            
//        };
//    }

    @ComponentFn(name = "rollbackParamsCheck")
    public Operator<OrderContext> rollbackParamsCheck(ParamsCheck params){
        System.out.println("rollbackParamsCheck 初始化配置参数："+ JSON.toJSONString(params));
        return  (ctx) -> {
            System.out.println("invoke rollbackParamsCheck");
            
        };
    }

    @ComponentFn(name = "paramsCheck")
    public Operator<OrderContext> paramsCheck(ParamsCheck params){
        System.out.println("paramsCheck 初始化配置参数："+ JSON.toJSONString(params));
        return  (ctx) -> {
            System.out.println("invoke paramsCheck");
            
        };
    }

    @ComponentFn(name = "queryGoodsInfo")
    public Operator<OrderContext> queryGoodsInfo(String config){
        return (ctx) -> {
            System.out.println("invoke queryGoodsInfo");
            
        };
    }

    @ComponentFn(name = "rollbackQueryGoodsInfo")
    public Operator<OrderContext> rollbackQueryGoodsInfo(String config){
        return (ctx) -> {
            System.out.println("invoke rollbackQueryGoodsInfo");
            
        };
    }

    @ComponentFn(name = "queryShipping")
    public Operator<OrderContext> queryShipping(String config){
        return (ctx) -> {
            System.out.println("invoke queryShipping");
            
        };
    }

    @ComponentFn(name = "queryUserAddress")
    public Operator<OrderContext> queryUserAddress(String config){
        return (ctx) -> {
            System.out.println("invoke queryUserAddress");
            
        };
    }

    @ComponentFn(name = "queryStoreInfo")
    public Operator<OrderContext> queryStoreInfo(String config){
        return (ctx) -> {
            System.out.println("invoke queryStoreInfo");
            
        };
    }

    @ComponentFn(name = "deductStock")
    public Operator<OrderContext> deductStock(String config){
        return (ctx) -> {
            System.out.println("invoke deductStock");
            
        };
    }

    @ComponentFn(name = "releaseStock")
    public Operator<OrderContext> releaseStock(String config){
        return (ctx) -> {
            System.out.println("invoke releaseStock");
            
        };
    }

    @ComponentFn(name = "builderOrderInfo")
    public Operator<OrderContext> builderOrderInfo(String config){
        return (ctx) -> {
            System.out.println("invoke builderOrderInfo");
            
        };
    }

    @ComponentFn(name = "syncOrderToEs")
    public Operator<OrderContext> syncOrderToEs(String config){
        return (ctx) -> {
            System.out.println("invoke syncOrderToEs");
            
        };
    }


    @ComponentFn(name = "saveOrderLog")
    public Operator<OrderContext> saveOrderLog(String config){
        return (ctx) -> {
            System.out.println("invoke saveOrderLog");
            
        };
    }


    @ComponentFn(name = "removeCart")
    public Operator<OrderContext> removeCart(String config){
        return (ctx) -> {
            System.out.println("invoke removeCart");
            
        };
    }


    @ComponentFn(name = "sendDelayMq")
    public Operator<OrderContext> sendDelayMq(String config){
        return (ctx) -> {
            System.out.println("invoke sendDelayMq");
            
        };
    }

    @ComponentFn(name = "orderFromShoppingCart")
    public Operator<OrderContext> orderFromShoppingCart(String config){
        return (ctx) -> {
            System.out.println("invoke orderFromShoppingCart");
            
        };
    }
}
