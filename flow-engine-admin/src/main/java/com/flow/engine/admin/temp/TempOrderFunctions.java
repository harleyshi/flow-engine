package com.flow.engine.admin.temp;

import org.springframework.stereotype.Service;


@Service
public class TempOrderFunctions {

    public void saveOrderInfo(String config){
//        return (ctx) -> {
//            System.out.println("invoke saveOrderInfo");
//        };
    }
    public void paramsCheck(){

//        return  (ctx) -> {
//            System.out.println("invoke paramsCheck");
//            
//        };
    }

    public void queryGoodsInfo(String config){
//        return (ctx) -> {
//            System.out.println("invoke queryGoodsInfo");
//            
//        };
    }

    public void rollbackQueryGoodsInfo(String config){
//        return (ctx) -> {
//            System.out.println("invoke rollbackQueryGoodsInfo");
//            
//        };
    }

    public void queryShipping(String config){
//        return (ctx) -> {
//            System.out.println("invoke queryShipping");
//            
//        };
    }

    
    public void queryUserAddress(String config){
//        return (ctx) -> {
//            System.out.println("invoke queryUserAddress");
//            
//        };
    }

    public void queryStoreInfo(String config){
//        return (ctx) -> {
//            System.out.println("invoke queryStoreInfo");
//            
//        };
    }

    public void deductStock(String config){
//        return (ctx) -> {
//            System.out.println("invoke deductStock");
//            
//        };
    }

    public void releaseStock(String config){
//        return (ctx) -> {
//            System.out.println("invoke releaseStock");
//            
//        };
    }

    public void builderOrderInfo(String config){
//        return (ctx) -> {
//            System.out.println("invoke builderOrderInfo");
//            
//        };
    }
    public void syncOrderToEs(String config){
//        return (ctx) -> {
//            System.out.println("invoke syncOrderToEs");
//            
//        };
    }

//    @ComponentFn(name = "isSyncOrderToEs")
//    public Condition<Boolean, FlowCtx> isSyncOrderToEs(){
//        return (ctx) -> {
//            System.out.println("invoke isSyncOrderToEs");
//            return true;
//        };
//    }

    public void saveOrderLog(String config){
//        return (ctx) -> {
//            System.out.println("invoke saveOrderLog");
//            
//        };
    }


    public void removeCart(String config){
//        return (ctx) -> {
//            System.out.println("invoke removeCart");
//            
//        };
    }


    public void sendDelayMq(String config){
//        return (ctx) -> {
//            System.out.println("invoke sendDelayMq");
//            
//        };
    }
    public void orderFromShoppingCart(String config){
//        return (ctx) -> {
//            System.out.println("invoke orderFromShoppingCart");
//            
//        };
    }
}
