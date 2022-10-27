package cn.house.utils;

import cn.mobile.common.bean.commonoperate.CommonBaseBean;
import cn.mobile.common.bean.commonoperate.operateBean.*;

import java.io.Serializable;

public   class CommonBeanAndOperateUtils implements Serializable {

    public static Object getCommonBeanAndOperate(CommonBaseBean commonBaseBean,Object baseOperate){
        if(baseOperate instanceof AppAd){
            AppAd appAd = (AppAd) baseOperate;
            appAd.setTimeStamp(commonBaseBean.getT());
            appAd.setSv(commonBaseBean.getSv());
            appAd.setOs(commonBaseBean.getOs());
            appAd.setG(commonBaseBean.getG());
            appAd.setMid(commonBaseBean.getMid());
            appAd.setL(commonBaseBean.getL());
            appAd.setVc(commonBaseBean.getVc());
            appAd.setHw(commonBaseBean.getHw());
            appAd.setAr(commonBaseBean.getAr());
            appAd.setUid(commonBaseBean.getUid());
            appAd.setT(commonBaseBean.getT());
            appAd.setMd(commonBaseBean.getMd());
            appAd.setVn(commonBaseBean.getVn());
            appAd.setBa(commonBaseBean.getBa());
            appAd.setSr(commonBaseBean.getSr());
        }else if(baseOperate instanceof AppCar){

            AppCar arrCar = (AppCar) baseOperate;
            arrCar.setTimeStamp(commonBaseBean.getT());
            arrCar.setSv(commonBaseBean.getSv());
            arrCar.setOs(commonBaseBean.getOs());
            arrCar.setG(commonBaseBean.getG());
            arrCar.setMid(commonBaseBean.getMid());
            arrCar.setL(commonBaseBean.getL());
            arrCar.setVc(commonBaseBean.getVc());
            arrCar.setHw(commonBaseBean.getHw());
            arrCar.setAr(commonBaseBean.getAr());
            arrCar.setUid(commonBaseBean.getUid());
            arrCar.setT(commonBaseBean.getT());
            arrCar.setMd(commonBaseBean.getMd());
            arrCar.setVn(commonBaseBean.getVn());
            arrCar.setBa(commonBaseBean.getBa());
            arrCar.setSr(commonBaseBean.getSr());

        }else if(baseOperate instanceof AppComment){
            AppComment appComment = (AppComment) baseOperate;
            appComment.setTimeStamp(commonBaseBean.getT());
            appComment.setSv(commonBaseBean.getSv());
            appComment.setOs(commonBaseBean.getOs());
            appComment.setG(commonBaseBean.getG());
            appComment.setMid(commonBaseBean.getMid());
            appComment.setL(commonBaseBean.getL());
            appComment.setVc(commonBaseBean.getVc());
            appComment.setHw(commonBaseBean.getHw());
            appComment.setAr(commonBaseBean.getAr());
            appComment.setUid(commonBaseBean.getUid());
            appComment.setT(commonBaseBean.getT());
            appComment.setMd(commonBaseBean.getMd());
            appComment.setVn(commonBaseBean.getVn());
            appComment.setBa(commonBaseBean.getBa());
            appComment.setSr(commonBaseBean.getSr());
        }
        else if(baseOperate instanceof AppDisplay){
            AppDisplay appDisplay = (AppDisplay) baseOperate;
            appDisplay.setTimeStamp(commonBaseBean.getT());
            appDisplay.setSv(commonBaseBean.getSv());
            appDisplay.setOs(commonBaseBean.getOs());
            appDisplay.setG(commonBaseBean.getG());
            appDisplay.setMid(commonBaseBean.getMid());
            appDisplay.setL(commonBaseBean.getL());
            appDisplay.setVc(commonBaseBean.getVc());
            appDisplay.setHw(commonBaseBean.getHw());
            appDisplay.setAr(commonBaseBean.getAr());
            appDisplay.setUid(commonBaseBean.getUid());
            appDisplay.setT(commonBaseBean.getT());
            appDisplay.setMd(commonBaseBean.getMd());
            appDisplay.setVn(commonBaseBean.getVn());
            appDisplay.setBa(commonBaseBean.getBa());
            appDisplay.setSr(commonBaseBean.getSr());
        }
        else if(baseOperate instanceof AppFavorites){
            AppFavorites appFavorites = (AppFavorites) baseOperate;
            appFavorites.setTimeStamp(commonBaseBean.getT());
            appFavorites.setSv(commonBaseBean.getSv());
            appFavorites.setOs(commonBaseBean.getOs());
            appFavorites.setG(commonBaseBean.getG());
            appFavorites.setMid(commonBaseBean.getMid());
            appFavorites.setL(commonBaseBean.getL());
            appFavorites.setVc(commonBaseBean.getVc());
            appFavorites.setHw(commonBaseBean.getHw());
            appFavorites.setAr(commonBaseBean.getAr());
            appFavorites.setUid(commonBaseBean.getUid());
            appFavorites.setT(commonBaseBean.getT());
            appFavorites.setMd(commonBaseBean.getMd());
            appFavorites.setVn(commonBaseBean.getVn());
            appFavorites.setBa(commonBaseBean.getBa());
            appFavorites.setSr(commonBaseBean.getSr());
        }
        else if(baseOperate instanceof AppLoading){
            AppLoading appLoading = (AppLoading) baseOperate;
            appLoading.setTimeStamp(commonBaseBean.getT());
            appLoading.setSv(commonBaseBean.getSv());
            appLoading.setOs(commonBaseBean.getOs());
            appLoading.setG(commonBaseBean.getG());
            appLoading.setMid(commonBaseBean.getMid());
            appLoading.setL(commonBaseBean.getL());
            appLoading.setVc(commonBaseBean.getVc());
            appLoading.setHw(commonBaseBean.getHw());
            appLoading.setAr(commonBaseBean.getAr());
            appLoading.setUid(commonBaseBean.getUid());
            appLoading.setT(commonBaseBean.getT());
            appLoading.setMd(commonBaseBean.getMd());
            appLoading.setVn(commonBaseBean.getVn());
            appLoading.setBa(commonBaseBean.getBa());
            appLoading.setSr(commonBaseBean.getSr());
        }
        else if(baseOperate instanceof AppPraise){
            AppPraise appPraise = (AppPraise) baseOperate;
            appPraise.setTimeStamp(commonBaseBean.getT());
            appPraise.setSv(commonBaseBean.getSv());
            appPraise.setOs(commonBaseBean.getOs());
            appPraise.setG(commonBaseBean.getG());
            appPraise.setMid(commonBaseBean.getMid());
            appPraise.setL(commonBaseBean.getL());
            appPraise.setVc(commonBaseBean.getVc());
            appPraise.setHw(commonBaseBean.getHw());
            appPraise.setAr(commonBaseBean.getAr());
            appPraise.setUid(commonBaseBean.getUid());
            appPraise.setT(commonBaseBean.getT());
            appPraise.setMd(commonBaseBean.getMd());
            appPraise.setVn(commonBaseBean.getVn());
            appPraise.setBa(commonBaseBean.getBa());
            appPraise.setSr(commonBaseBean.getSr());
        }

        return baseOperate;



    }


}
