package com.csiqi.utils;
public class GJJUtil {
	public static double getAllResult(int beforeMonthCount,double beforeEveryMonth,int afterMonthCount,double afterEveryMonthMoney){
		double result=0;
		double allResult=0;
		for(int i=0;i<beforeMonthCount+afterMonthCount;i++){
			if(i+1<=beforeMonthCount){
				result=result+beforeEveryMonth;
				if(beforeMonthCount+afterMonthCount>12 && i+1<=beforeMonthCount+afterMonthCount-12){//

				}else{
					allResult=allResult+result;
				}
			}else{
				result=result+afterEveryMonthMoney;
				if(beforeMonthCount+afterMonthCount>12 && i+1<=beforeMonthCount+afterMonthCount-12){//

				}else{
					allResult=allResult+result;
				}
			}
		}
		return allResult;
	}
	public static double everyMonth(int beforeMonthCount,double beforeEveryMonth,int afterMonthCount){
		double afterEveryMonthMoney=1000;
		double all=500000;
		double allResult=0;
		while(allResult/12*15<all){
			afterEveryMonthMoney++;
			allResult=getAllResult(beforeMonthCount,beforeEveryMonth,afterMonthCount,afterEveryMonthMoney);
		}
		return afterEveryMonthMoney;
	}
	public static double kdMoney(int beforeMonthCount,double beforeEveryMonth,int afterMonthCount,double afterEveryMonthMoney){
		double allResult=getAllResult(beforeMonthCount,beforeEveryMonth,afterMonthCount,afterEveryMonthMoney);
		if(beforeMonthCount+afterMonthCount>12){
			return allResult/12*15;
		}else{
			return allResult/(beforeMonthCount+afterMonthCount)*15;
		}
	}
	public static void main(String[] args) {//公积金可带额度计算
		//System.out.println(everyMonth(7,482,25));
		System.out.println(kdMoney(7,482,12,5000));
	}
}
