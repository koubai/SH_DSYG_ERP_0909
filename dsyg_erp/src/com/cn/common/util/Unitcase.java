package com.cn.common.util;

import java.math.BigDecimal;

public class Unitcase {
	/* Unitcase 包装分解CLASS */

	private String unitstring;     /* 包装文字列            */
	private String unitStrSub1;    /* 包装文字列分歧1*/
	private String unitStrSub2;    /* 包装文字列分歧2*/
	private String unitName;       /* 长度单位名             */
	private String unitQuantity;   /* 包装单位数量        */
	private String boxNameA;       /* 盒装单位名A    */
	private String boxNameB;       /* 盒装单位名B    */
	private String boxQuantity;    /* 盒装单位数量        */
	private String unitAmount;     /* 盒装数量                  */
	private String boxAmout;       /* 盒装数量                  */
	
	public Unitcase(String unitstring) {
		this.unitstring="";
		this.unitStrSub1="";
		this.unitStrSub2="";
		this.unitName="";
		this.unitQuantity="";
		this.boxNameA="";
		this.boxNameB="";
		this.boxQuantity="";
		
		if ( unitstring!=null && !"".equals(unitstring)){
			this.unitstring = unitstring;
			
			/* 按*进行左右分割 */
			String[] str_arr = unitstring.split("\\*");
			System.out.println("str_arr0:" + str_arr[0]);
			/* 例：10PC/箱     */
			if (str_arr.length ==1){
				boxQuantity = splitUnitData(str_arr[0]);  /* 10PC/箱 */
				unitName = getUnitName(unitStrSub2);				
			}
			/* 例：1000米*4卷/箱     */
			if (str_arr.length ==2){
				System.out.println("str_arr1:" + str_arr[1]);
				unitQuantity = splitUnitData(str_arr[0]);
				unitName = getUnitName(unitStrSub2);   /* 分割1000米 */
				boxQuantity = splitUnitData(str_arr[1]);
				unitName = getUnitName(unitStrSub2);	/* 分割4卷/箱 */
			}					
		}
	}

	public String getUnitstring() {
		return unitstring;
	}

	public void setUnitstring(String unitstring) {
		this.unitstring = unitstring;
	}

	public String getUnitStrSub1() {
		return unitStrSub1;
	}

	public void setUnitStrSub1(String unitStrSub1) {
		this.unitStrSub1 = unitStrSub1;
	}

	public String getUnitStrSub2() {
		return unitStrSub2;
	}

	public void setUnitStrSub2(String unitStrSub2) {
		this.unitStrSub2 = unitStrSub2;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getBoxNameA() {
		return boxNameA;
	}

	public void setBoxNameA(String boxNameA) {
		this.boxNameA = boxNameA;
	}

	public String getBoxNameB() {
		return boxNameB;
	}

	public void setBoxNameB(String boxNameB) {
		this.boxNameB = boxNameB;
	}

	public String getUnitQuantity() {
		return unitQuantity;
	}

	public void setUnitQuantity(String unitQuantity) {
		this.unitQuantity = unitQuantity;
	}

	public String getBoxQuantity() {
		return boxQuantity;
	}

	public void setBoxQuantity(String boxQuantity) {
		this.boxQuantity = boxQuantity;
	}
	
	public String getUnitAmount() {
		return unitAmount;
	}
	public String getUnitAmount(String in_amount) {
		BigDecimal bd_unitquantity;
		BigDecimal bd_in_amount = new BigDecimal(in_amount) ;
		BigDecimal bd_amount = new BigDecimal(0);
		try {
			if (unitQuantity !=null && !"".equals(unitQuantity)){
				bd_unitquantity = new BigDecimal(unitQuantity);
				bd_amount = bd_in_amount.divide(bd_unitquantity) ;
			} 
			if (!bd_amount.equals(new BigDecimal(0))) 
				return bd_amount.toString();
			else
				return "";
		} catch (ArithmeticException e){
			return "";
		}
	}

	public void setUnitAmount(String unitAmount) {
		this.unitAmount = unitAmount;
	}

	public String getBoxAmout() {
		return boxAmout;
	}
	public String getBoxAmout(String in_amount) {
		BigDecimal bd_unitquantity;
		BigDecimal bd_boxquantity;
		BigDecimal bd_in_amount = new BigDecimal(in_amount) ;
		BigDecimal bd_amount = new BigDecimal(0);
		try {
			if (unitQuantity !=null && !"".equals(unitQuantity)){
				bd_unitquantity = new BigDecimal(unitQuantity);
				bd_amount = bd_in_amount.divide(bd_unitquantity) ;
			} else {
				bd_amount = bd_in_amount ;
			}
			if (boxQuantity !=null && !"".equals(boxQuantity)){
				bd_boxquantity = new BigDecimal(boxQuantity);
				bd_amount = bd_amount.divide(bd_boxquantity) ;
			}
			if (!bd_amount.equals(new BigDecimal(0))) 
				return bd_amount.toString();
			else
				return "";
		} catch (ArithmeticException e){
			return "";
		}
	}

	public void setBoxAmout(String boxAmout) {
		this.boxAmout = boxAmout;
	}

	public String splitUnitData(String orgdata) {
		String str_unitName="";
		String str_unit_quantity="";		
		orgdata=orgdata.trim();
		if (orgdata != null && !"".equals(orgdata)){
			for(int i=0;i<orgdata.length();i++){
				if(orgdata.charAt(i)>=48 && orgdata.charAt(i)<=57){
					// this is number area
					str_unit_quantity +=orgdata.charAt(i);
				}else {
					str_unitName=orgdata.substring(i);
					break;
				}
			}
			setUnitStrSub1(str_unit_quantity);
			setUnitStrSub2(str_unitName);
		}
		return str_unit_quantity;
	}
	
	public String getUnitName(String orgdata) {
		orgdata=orgdata.trim();
		if (orgdata != null && !"".equals(orgdata)){
			String[] str_arr = orgdata.split("\\/");
			if (str_arr.length ==2){
				setBoxNameA(str_arr[0]);				
				setBoxNameB(str_arr[1]);				
			}
			if (str_arr.length ==1){
				setBoxNameB(str_arr[0]);				
			}			
		}
		return orgdata;
	}
	
}
