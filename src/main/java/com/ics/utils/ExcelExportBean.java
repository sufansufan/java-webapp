package com.ics.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableFont.FontName;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * <p>
 * Title: CommonUtil
 * </p>
 * <p>
 * Description: EXCEL导出公共类
 * </p>
 * 
 * @author yi
 * @date 2017年11月20日 上午10:08:56
 */
public class ExcelExportBean {

	/**
	 * 输出的文件名
	 */
	private String fileName;

	/**
	 * 主标题
	 */
	private String title;

	/**
	 * 列名
	 */
	private String[] colNames;

	/**
	 * 导出文件保存的文件夹名称
	 */
	private String folderName = "exportFile";
	
	private HttpServletRequest request;
	
	/**
	 * 数据
	 */
	private List<Object[]> dataList = new ArrayList<>();

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 
	 * @param fileName 导出文件名，自动加上时间戳
	 * @param title 内部单元格标题
	 * @param colNames 列标题
	 * @param dataList 数据
	 */
	public ExcelExportBean(String fileName, String title, String[] colNames, List<Object[]> dataList,HttpServletRequest request) {
		this.fileName = fileName;
		this.title = title;
		this.colNames = colNames;
		this.dataList = dataList;
		this.request = request;
	}

	public String export() throws Exception {
		
		//文件名加上时间戳和随机数
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");  
        String sdate = simpleDateFormat.format(new Date());  
        int rannum = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数  
        String str = sdate+rannum;
		
        //设置保存路径
        String savePath = request.getSession().getServletContext().getRealPath("/") ;
        
        String relativeName = folderName + "/" + fileName + str + ".xls";
        String realfileName = savePath + relativeName;
		
        //文件不存在就创建
        File saveDirFile = new File(savePath+folderName);
        if (!saveDirFile.exists()) {
        	saveDirFile.mkdirs();
        }
        
        WritableWorkbook wwb = null;  
        try {  
            // 首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象  
            wwb = Workbook.createWorkbook(new File(realfileName));  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        if (wwb != null) {
            
            WritableCellFormat titleFormat = this.getTitleStyle();
            WritableCellFormat contentFormat = this.getContentStyle();
            
            // 创建一个可写入的工作表  
            // Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置  
            WritableSheet ws = wwb.createSheet("Sheet1", 0);  
            try {
                ws.mergeCells(0, 0, colNames.length-1, 0);//设置第一列、第一行和 第11列、第一行合并
                Label headerTitle = new Label(0, 0, title,titleFormat);
                ws.addCell(headerTitle);
                ws.setColumnView(0, 10);// 设置宽度
                
                //创建表头
                for (int i = 0; i < colNames.length; i++) {
                    String head = colNames[i];
                    ws.addCell(new Label(i, 1, head,titleFormat));
                    ws.setColumnView(i, 20);// 设置宽度
                }
                
            } catch (RowsExceededException e1) {
                e1.printStackTrace();
            } catch (WriteException e1) {
                e1.printStackTrace();
            }
          
          if(dataList!=null && dataList.size()>0){
              for(int i = 0;i< dataList.size();i++){
            	  Object[] obj = (Object[]) dataList.get(i);
                      
                      
                      for(int j=0; j<obj.length; j++){  
                    	  
                    	  if(obj[j] instanceof Date) {
                    		  //日期格式化
                    		  obj[j] = dateFormat.format(obj[j]);
                    	  }
                    	  
                    	  Label labelC = new Label(j, i+2, String.valueOf(obj[j]), contentFormat);//第一行，第二列  
                    	  ws.addCell(labelC);
                    	  
                      }

              }
          }
        }

        try {  
            // 从内存中写入文件中  
            wwb.write();  
            // 关闭资源，释放内存  
            wwb.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (WriteException e) {  
            e.printStackTrace();  
        } 
		return relativeName;
	}
	
	/**
	 * 获取标题单元格样式
	 * @return
	 */
	public WritableCellFormat getTitleStyle() {  
		
		//设置表头文字样式
        FontName defaultFont = WritableFont.createFont("宋体");
        // 表头字体
        WritableFont titleFont = new WritableFont(defaultFont, 11, WritableFont.BOLD);
        WritableCellFormat headerFormat = new WritableCellFormat(titleFont);

        try {
            // 水平居中
            headerFormat.setAlignment(Alignment.CENTRE);
            // 竖直方向居中对齐
            headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        } catch (WriteException e) {
            e.printStackTrace();
        }
        
        return headerFormat;
	}
	
	/**
	 * 获取普通单元格样式
	 * @return
	 */
	public WritableCellFormat getContentStyle() {  
		
		//设置表头文字样式
        FontName defaultFont = WritableFont.createFont("宋体");
        // 普通字体
        WritableFont normalFont = new WritableFont(defaultFont, 10, WritableFont.NO_BOLD);
        WritableCellFormat contentFormat = new WritableCellFormat(normalFont);

        try {
            contentFormat.setAlignment(Alignment.CENTRE);
            contentFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        } catch (WriteException e) {
            e.printStackTrace();
        }
        
		return contentFormat;
	}


	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
}
