package com.ics.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * <p>
 * Title: CommonUtil
 * </p>
 * <p>
 * Description: EXCEL导入工具类
 * </p>
 * 
 * @author yi
 * @date 2017年11月20日 上午10:08:56
 */
public class ExcelImportUtil {

	/**
	 * excel文件解析
	 * 
	 * @param path
	 *            文件路径
	 * @param colNum
	 *            模板列数（不匹配则视为无效模板）
	 * @return
	 * @throws Exception
	 */
	public static JsonResult analy(String path, int colNum) throws Exception {

		JsonResult result = new JsonResult();
		Workbook wb = null;
		Cell cell = null;
		List<String[]> list = new ArrayList<>();
		try {
			wb = Workbook.getWorkbook(new File(path));
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (wb != null) {
			Sheet sheet = wb.getSheet(0);

			if (sheet.getColumns() == colNum) {

				for (int i = 0; i < sheet.getRows(); i++) {
					// 创建数组 存储每列的值
					String[] str = new String[sheet.getColumns()];
					// 列数
					int count = 0;
					for (int j = 0; j < colNum; j++) {
						// 获取第i行，第j列的值
						cell = sheet.getCell(j, i);
						if (cell != null) {
							str[j] = StringUtils.trimToEmpty(cell.getContents());
							if (StringUtils.isBlank(str[j])) {
								count++;
							}
						}
					}
					
					if(count==colNum) {
						//空行过滤且终止读取
						break;
					}
					
					list.add(str);
				}

				if (list != null && list.size() > 0) {

					if (list.size() == 1) {
						result.setFaildMsg("excel内容为空");
					} else {
						result.setData(list);
					}

				} else {
					result.setFaildMsg("excel内容为空");
				}

			} else {
				result.setFaildMsg("excel模板格式不正确,请下载示例模板!");
			}

			wb.close();
		} else {

			result.setFaildMsg("文件读取错误!");
		}

		return result;
	}

}
