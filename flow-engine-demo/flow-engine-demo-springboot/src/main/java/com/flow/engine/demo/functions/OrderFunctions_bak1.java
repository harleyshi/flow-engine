//package com.flow.engine.demo.functions;
//
//import com.alibaba.fastjson2.JSON;
//import com.flow.engine.operator.ComponentFn;
//import com.flow.engine.operator.Operator;
//import com.flow.engine.demo.context.OrderContext;
//import com.flow.engine.demo.domain.TestADO;
//import com.flow.engine.demo.domain.TestBDO;
//import com.flow.engine.demo.domain.TestCDO;
//import com.flow.engine.demo.functions.params.ParamsCheck;
//import com.flow.engine.demo.mapper.TestAMapper;
//import com.flow.engine.demo.mapper.TestBMapper;
//import com.flow.engine.demo.mapper.TestCMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.support.TransactionTemplate;
//
//
//@Service
//public class OrderFunctions {
//
//    @Autowired
//    private TestAMapper testAMapper;
//    @Autowired
//    private TestBMapper testBMapper;
//    @Autowired
//    private TestCMapper testCMapper;
//    @Autowired
//    private TransactionTemplate transactionTemplate;
//
//    @ComponentFn(name = "saveOrderInfo")
//    public Operator<Boolean, OrderContext> saveOrderInfo(String config){
//        return (ctx) -> {
////            transactionTemplate.execute(status -> {
////                testAMapper.insert(new TestADO("testA"));
////                testBMapper.insert(new TestBDO("testB"));
//////                int i = 1 / 0;
////                testCMapper.insert(new TestCDO("testC"));
////                return true;
////            });
//            return true;
//        };
//    }
//
//    @ComponentFn(name = "rollbackParamsCheck")
//    public Operator<Void, OrderContext> rollbackParamsCheck(ParamsCheck params){
//        System.out.println("rollbackParamsCheck 初始化配置参数："+ JSON.toJSONString(params));
//        return  (ctx) -> {
//            return null;
//        };
//    }
//
//    @ComponentFn(name = "paramsCheck")
//    public Operator<Void, OrderContext> paramsCheck(ParamsCheck params){
//        System.out.println("paramsCheck 初始化配置参数："+ JSON.toJSONString(params));
//        return  (ctx) -> {
//            return null;
//        };
//    }
//
//    @ComponentFn(name = "queryGoodsInfo")
//    public Operator<Boolean, OrderContext> queryGoodsInfo(String config){
//        return (ctx) -> {
//            return true;
//        };
//    }
//
//    @ComponentFn(name = "rollbackQueryGoodsInfo")
//    public Operator<Boolean, OrderContext> rollbackQueryGoodsInfo(String config){
//        return (ctx) -> {
//            return true;
//        };
//    }
//
//    @ComponentFn(name = "queryShipping")
//    public Operator<Boolean, OrderContext> queryShipping(String config){
//        return (ctx) -> {
//            return true;
//        };
//    }
//
//    @ComponentFn(name = "queryUserAddress")
//    public Operator<Boolean, OrderContext> queryUserAddress(String config){
//        return (ctx) -> {
//            return true;
//        };
//    }
//
//    @ComponentFn(name = "queryStoreInfo")
//    public Operator<Boolean, OrderContext> queryStoreInfo(String config){
//        return (ctx) -> {
//            try {
//                Thread.sleep(500);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            return true;
//        };
//    }
//    @ComponentFn(name = "deductStock")
//    public Operator<Boolean, OrderContext> deductStock(String config){
//        return (ctx) -> {
//            return true;
//        };
//    }
//
//    @ComponentFn(name = "releaseStock")
//    public Operator<Boolean, OrderContext> releaseStock(String config){
//        return (ctx) -> {
//            return true;
//        };
//    }
//
//    @ComponentFn(name = "builderOrderInfo")
//    public Operator<Boolean, OrderContext> builderOrderInfo(String config){
//        return (ctx) -> {
//            return true;
//        };
//    }
//
//    @ComponentFn(name = "syncOrderToEs")
//    public Operator<Boolean, OrderContext> syncOrderToEs(String config){
//        return (ctx) -> {
//            return true;
//        };
//    }
//
//
//    @ComponentFn(name = "saveOrderLog")
//    public Operator<Boolean, OrderContext> saveOrderLog(String config){
//        return (ctx) -> {
//            return true;
//        };
//    }
//
//
//    @ComponentFn(name = "removeCart")
//    public Operator<Boolean, OrderContext> removeCart(String config){
//        return (ctx) -> {
//            return true;
//        };
//    }
//
//
//    @ComponentFn(name = "sendDelayMq")
//    public Operator<Boolean, OrderContext> sendDelayMq(String config){
//        return (ctx) -> {
//            return true;
//        };
//    }
//
//    @ComponentFn(name = "orderFromShoppingCart")
//    public Operator<Boolean, OrderContext> orderFromShoppingCart(String config){
//        return (ctx) -> {
//            return true;
//        };
//    }
//}
