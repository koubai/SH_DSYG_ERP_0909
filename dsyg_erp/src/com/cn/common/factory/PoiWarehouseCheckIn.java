package com.cn.common.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.cn.common.util.Constants;
import com.cn.common.util.FileUtil;
import com.cn.common.util.PropertiesConfig;

public class PoiWarehouseCheckIn extends Poi2007Base {
	/**
	 * 输出大标题
	 * @param sheet
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		File file = new File("D:\\20180726\\20180728\\warehousercheck20180728171667.xls");
		FileInputStream input = new FileInputStream(file);
//		MultipartFile multipartFile = new MockMultipartFile("file",file.getName(), "text/plain", input);
//		Object bo = upload(multipartFile);
		
	}

	private static final Logger log = LogManager.getLogger(Poi2007Base.class);

	//parameter: filename   name of upload warehouse check file with path
	public List<com.cn.dsyg.dto.WarehouseCheckDto> upload(String filename) throws Exception {

        //得到文件名
		String file_path = PropertiesConfig.getPropertiesValueByKey(Constants.PROPERTIES_FILE_PATH);			
        log.info("file_path:"+ file_path);
		File file = new File(filename);
        log.info("filename:"+ filename);
        String fname=file.getName();        		
        log.info("fname:"+ fname);
		String newfile = FileUtil.uploadFile(file, file_path, fname);
	    File ff = new File(file_path + newfile);
        log.info("ff:"+ file_path + newfile);
	    
	    String fileType = com.cn.common.util.FileTypeUtil.getFileType(ff);
	    //XLS_DOC --》2007之前的excel类型  XLSX_DOCX--》  2007之后的excel类型
	    System.out.println("filename:"+ filename);
	    System.out.println("fileType:"+ fileType);
        log.info("fileType" + fileType);
	    if (fileType.equals("wps")) {
	        log.info("开始解析XLS_DOC文件");
	        FileInputStream fis = new FileInputStream(ff);
	        POIFSFileSystem fs = new POIFSFileSystem(fis);
	        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fs);
	        List<com.cn.dsyg.dto.WarehouseCheckDto> getData = readExcel(hssfWorkbook);
	        if (getData == null) {
	            log.error("解析失败...");
//	            return "文件数据解析失败";
	            return null;
	        }
	        log.info("数据解析成功");	
            fis.close();  
	        return getData;
		} else if (fileType.equals("zip")|| fileType.equals("docx")) {
		        log.info("开始解析XLS_DOC文件");
		        FileInputStream fis = new FileInputStream(ff);
		        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
		        List<com.cn.dsyg.dto.WarehouseCheckDto> getData = readExcel2(xssfWorkbook);
		        if (getData == null) {
		            log.error("解析失败...");
//		            return "文件数据解析失败";
		            return null;
		        }
		        log.info("数据解析成功");	
	            fis.close();  
		        return getData;
		            
	    } else {
	        log.error("上传文件类型不正确");
//	        return "文件类型不正确";
            return null;
	    }

	}
	
	/**保存文件
     * @param stream
     * @param path
     * @param filename
     * @throws IOException
     */
    public void SaveFileFromInputStream(InputStream stream,String path,String filename) throws IOException
    {      
        FileOutputStream fs=new FileOutputStream( path + "/"+ filename);
        byte[] buffer =new byte[1024*1024];
        int bytesum = 0;
        int byteread = 0; 
        while ((byteread=stream.read(buffer))!=-1)
        {
           bytesum+=byteread;
           fs.write(buffer,0,byteread);
           fs.flush();
        } 
        fs.close();
        stream.close();      
    }       

	//处理2007之后的excel
	private List<com.cn.dsyg.dto.WarehouseCheckDto> readExcel(HSSFWorkbook xssfWorkbook) {
	    List<com.cn.dsyg.dto.WarehouseCheckDto> wdt_arry = new ArrayList<com.cn.dsyg.dto.WarehouseCheckDto>();
	    //获得excel第一个工作薄
	    HSSFSheet sheet = xssfWorkbook.getSheetAt(0);
	    //行
	    HSSFRow row = null;
	    //列
	    HSSFCell cell = null;
	    for (int i = sheet.getFirstRowNum(); i <= sheet.getPhysicalNumberOfRows(); i++) {
	    	if (i<3)
	            continue;
	        //获取每一行
	        row = sheet.getRow(i);
	        //判断是否出现空行
            System.out.println("获取" + i + "X");
	        if (row == null) {
//	            log.warn("获取到一个空行-------》》》》》》" + i + "行");
//	            System.out.println("获取到一个空行------------》》》》》》" + i + "行");
	            continue;
	        }
	        Object[] objects = new Object[row.getLastCellNum()];
	        for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
	            cell = row.getCell(j);

	            if (cell == null) {
//	                log.warn("获取到一个空列------------》》》》》》" + j + "列");
//                    System.out.println("获取到一个空列------------》》》》》》" + j + "列");
	                continue;
	            }
	            //第一行数据
	            switch (cell.getCellType()) {
	                case HSSFCell.CELL_TYPE_STRING:
	                    objects[j] = cell.getStringCellValue();
//	                    System.out.println(cell.getStringCellValue());
	                    break;
	                case HSSFCell.CELL_TYPE_BLANK:
	                    objects[j] = "";
	                    break;
	                case HSSFCell.CELL_TYPE_BOOLEAN:
	                    objects[j] = cell.getBooleanCellValue();
//	                    System.out.println(cell.getBooleanCellValue());
	                    break;
	                case HSSFCell.CELL_TYPE_NUMERIC:
	                    //处理double类型的  1.0===》1
	                    DecimalFormat df = new DecimalFormat("0");
	                    String s = df.format(cell.getNumericCellValue());
	                    objects[j] = s;
//	                    System.out.println(s);
	                    break;
	                default:
	                    objects[j] = cell.toString();
	            }

	        }
	        //处理数据
	        if (objects != null) {
	        	com.cn.dsyg.dto.WarehouseCheckDto wdt = new com.cn.dsyg.dto.WarehouseCheckDto();
	        	wdt.setFieldno(objects[1].toString());
	        	wdt.setTradename(objects[3].toString());
	        	wdt.setTypeno(objects[4].toString());
	        	wdt.setColor(objects[5].toString());
	        	wdt.setPackaging(objects[6].toString());
	        	wdt.setItem10(objects[7].toString());
	        	wdt.setUnit(objects[8].toString());
	        	wdt.setMakearea(objects[9].toString());
	        	wdt.setProductid(objects[10].toString());
	        	wdt.setSupplierid(new Long (objects[11].toString()));
	        	//预测库存数量
	        	wdt.setRes01(objects[12].toString());
	        	//上期盘点库存详细数量及入库日期
	        	JSONArray arry_old = createProdHist(objects[13].toString());
	        	if (arry_old != null)
	        		wdt.setRes02(arry_old.toString());
	        	//盘点库存数量
	        	wdt.setWarehouseamount(new BigDecimal(objects[14].toString()));
	        	//盘点库存详细数量及入库日期
	        	JSONArray arry_new = createProdHist(objects[15].toString());
	        	if (arry_new != null)
	        		wdt.setRes03(arry_new.toString());
	        	wdt_arry.add(wdt);
	        }
	    }
	    return wdt_arry;
	}	
	
	//处理2007之后的excel
	private List<com.cn.dsyg.dto.WarehouseCheckDto> readExcel2(XSSFWorkbook xssfWorkbook) {
	    List<com.cn.dsyg.dto.WarehouseCheckDto> wdt_arry = new ArrayList<com.cn.dsyg.dto.WarehouseCheckDto>();
	    //获得excel第一个工作薄
	    XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
	    //行
	    XSSFRow row = null;
	    //列
	    XSSFCell cell = null;
	    for (int i = sheet.getFirstRowNum(); i <= sheet.getPhysicalNumberOfRows(); i++) {
	    	if (i<3)
	            continue;
	        //获取每一行
	        row = sheet.getRow(i);
	        //判断是否出现空行
//            System.out.println("获取" + i + "X");
	        if (row == null) {
//	            log.warn("获取到一个空行-------》》》》》》" + i + "行");
//	            System.out.println("获取到一个空行------------》》》》》》" + i + "行");
	            continue;
	        }
	        Object[] objects = new Object[row.getLastCellNum()];
	        for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
	            cell = row.getCell(j);

	            if (cell == null) {
//	                log.warn("获取到一个空列------------》》》》》》" + j + "列");
//                    System.out.println("获取到一个空列------------》》》》》》" + j + "列");
	                continue;
	            }
	            //第一行数据
	            switch (cell.getCellType()) {
	                case XSSFCell.CELL_TYPE_STRING:
	                    objects[j] = cell.getStringCellValue();
//	                    System.out.println(cell.getStringCellValue());
	                    break;
	                case XSSFCell.CELL_TYPE_BLANK:
	                    objects[j] = "";
	                    break;
	                case XSSFCell.CELL_TYPE_BOOLEAN:
	                    objects[j] = cell.getBooleanCellValue();
//	                    System.out.println(cell.getBooleanCellValue());
	                    break;
	                case XSSFCell.CELL_TYPE_NUMERIC:
	                    //处理double类型的  1.0===》1
	                    DecimalFormat df = new DecimalFormat("0");
	                    String s = df.format(cell.getNumericCellValue());
	                    objects[j] = s;
//	                    System.out.println(s);
	                    break;
	                default:
	                    objects[j] = cell.toString();
	            }

	        }
	        //处理数据
	        if (objects != null) {
	        	com.cn.dsyg.dto.WarehouseCheckDto wdt = new com.cn.dsyg.dto.WarehouseCheckDto();
	        	wdt.setFieldno(objects[1].toString());
	        	wdt.setTradename(objects[3].toString());
	        	wdt.setTypeno(objects[4].toString());
	        	wdt.setColor(objects[5].toString());
	        	wdt.setPackaging(objects[6].toString());
	        	wdt.setItem10(objects[7].toString());
	        	wdt.setUnit(objects[8].toString());
	        	wdt.setMakearea(objects[9].toString());
	        	wdt.setProductid(objects[10].toString());
	        	if (objects[11].toString()!=null && !objects[11].toString().equals(""))
	        		wdt.setSupplierid(new Long (objects[11].toString()));
	        	//预测库存数量
	        	wdt.setRes01(objects[12].toString());
	        	//上期盘点库存详细数量及入库日期
	        	JSONArray arry_old = createProdHist(objects[13].toString());
	        	if (arry_old != null)
	        		wdt.setRes02(arry_old.toString());
	        	//盘点库存数量
	        	if (objects[14].toString()!=null && !objects[14].toString().equals(""))
	        		wdt.setWarehouseamount(new BigDecimal(objects[14].toString()));
	        	//盘点库存详细数量及入库日期
	        	JSONArray arry_new = createProdHist(objects[15].toString());
	        	if (arry_new != null)
	        		wdt.setRes03(arry_new.toString());
	        	wdt_arry.add(wdt);
	        }
	    }
	    return wdt_arry;
	}	
	
	public JSONArray createProdHist(String in_String){
		if ((in_String == null) || (in_String.trim().equals("")))
			return null;
		List<Map<String, String>> lst = new ArrayList<Map<String, String>> ();
    	String[] w_data = in_String.toString().split("\n");
    	if (w_data.length > 0){
    		for (int m = 0 ; m <w_data.length ; m++){
        		com.cn.dsyg.dto.PositionHistDto pp = new com.cn.dsyg.dto.PositionHistDto();
//        		System.out.println("w_data["+m+"]"+ w_data[m]);
        		int ix = w_data[m].toString().indexOf("，");
        		String[] w_dat;
        		if (ix > 0)
        			w_dat = w_data[m].toString().split("，");
        		else 
        			w_dat = w_data[m].toString().split(",");
        		if (w_dat.length > 0){
        			String in_wdate = w_dat[0].trim(); 
    				pp.setIn_wdate(in_wdate);
//	        		System.out.println("in_wdate:"+ in_wdate);	        		
        			if (w_dat.length > 1){
        				int idx= w_dat[1].indexOf(";");
        				String in_quantity ="";
        				if (idx > 0)	
            				in_quantity = w_dat[1].substring(0, idx); 
        				else
        					in_quantity = w_dat[1].trim(); 
        				pp.setIn_quantity(new BigDecimal(in_quantity));
//        				System.out.println("in_quantity:"+ in_quantity);
            			lst.add(pp.getIn_map());
        			}else{
//        				System.out.println( m + "row in_quantity error.");	        				        				
        			}
        		}
    		}    		
    	}
    	if (lst.size() <= 0){
    		return null;    		
    	}
		JSONArray mapArray = JSONArray.fromObject(lst);
//		System.out.println("mapArray" + mapArray.toString());
		return mapArray;
	}
}





