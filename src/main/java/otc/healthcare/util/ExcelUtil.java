package otc.healthcare.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import otc.healthcare.pojo.FieldInfo;
import otc.healthcare.pojo.TableInfo;

public class ExcelUtil {
	@SuppressWarnings("resource")
	public static List<TableInfo> read(InputStream stream,String type){
		List<TableInfo> tableInfoList = new ArrayList<TableInfo>();
		Workbook wb = null;
		try {
			if (type.equals("xls")) {
				wb = new HSSFWorkbook(stream);
			} else if (type.equals("xlsx")) {
				wb = new XSSFWorkbook(stream);
			} else {
				System.out.println("您输入的excel格式不正确");
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int sheetNumber = wb.getNumberOfSheets();
		//System.out.println("表格的数量：" + wb.getNumberOfSheets());
		for (int i = 0; i < sheetNumber; i++) {
			System.out.print("遍历第"+(i+1)+"个表格");
			Sheet sheet = wb.getSheetAt(i);
			int rowStart = sheet.getFirstRowNum();
			int rowEnd = sheet.getLastRowNum();
			if(rowStart==rowEnd){ 
				System.out.println("，表格内容为空，跳过该表格");
				break;
			}
			System.out.println();
			// Row firstRow=sheet.getRow(rowStart);
			TableInfo tableInfo = new TableInfo();
			tableInfo.setName(sheet.getRow(rowStart + 1).getCell(0).getStringCellValue());
			tableInfo.setZhcnname(sheet.getRow(rowStart + 1).getCell(1).getStringCellValue());
			tableInfo.setComments(sheet.getRow(rowStart + 1).getCell(2).getStringCellValue());
			tableInfo.setFieldlist(new ArrayList<FieldInfo>());
			for (int j = rowStart + 4; j <=rowEnd; j++) {// 从相对位置的第4行开始
				FieldInfo fieldInfo = new FieldInfo();
				sheet.getRow(j).getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				sheet.getRow(j).getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				sheet.getRow(j).getCell(2).setCellType(Cell.CELL_TYPE_STRING);
				sheet.getRow(j).getCell(3).setCellType(Cell.CELL_TYPE_STRING);
				fieldInfo.setName(sheet.getRow(j).getCell(0).getStringCellValue());
				fieldInfo.setZhcnname(sheet.getRow(j).getCell(1).getStringCellValue());
				fieldInfo.setComments(sheet.getRow(j).getCell(2).getStringCellValue());
				fieldInfo.setDatadictionary(sheet.getRow(j).getCell(3).getStringCellValue());
				tableInfo.getFieldlist().add(fieldInfo);// 将属于这个表的列信息加入
			}
			tableInfoList.add(tableInfo);
		}
		return tableInfoList;
	}
	public static List<TableInfo> read(String filePath) {
		String type=filePath.substring(filePath.indexOf(".") + 1);
		
		try {
			return read(new FileInputStream(filePath),type);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
//		List<TableInfo> tableInfoList = new ArrayList<TableInfo>();
//		InputStream stream;
//		Workbook wb = null;
//		try {
//			stream = new FileInputStream(filePath);
//			if (filePath.substring(filePath.indexOf(".") + 1).equals("xls")) {
//				wb = new HSSFWorkbook(stream);
//			} else if (filePath.substring(filePath.indexOf(".") + 1).equals("xlsx")) {
//				wb = new XSSFWorkbook(stream);
//			} else {
//				System.out.println("您输入的excel格式不正确");
//				return null;
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		int sheetNumber = wb.getNumberOfSheets();
//		//System.out.println("表格的数量：" + wb.getNumberOfSheets());
//		for (int i = 0; i < sheetNumber; i++) {
//			System.out.print("遍历第"+(i+1)+"个表格");
//			Sheet sheet = wb.getSheetAt(i);
//			int rowStart = sheet.getFirstRowNum();
//			int rowEnd = sheet.getLastRowNum();
//			if(rowStart==rowEnd){ 
//				System.out.println("，表格内容为空，跳过该表格");
//				break;
//			}
//			System.out.println();
//			// Row firstRow=sheet.getRow(rowStart);
//			TableInfo tableInfo = new TableInfo();
//			tableInfo.setName(sheet.getRow(rowStart + 1).getCell(0).getStringCellValue());
//			tableInfo.setComments(sheet.getRow(rowStart + 1).getCell(1).getStringCellValue());
//			tableInfo.setFieldlist(new ArrayList<FieldInfo>());
//			for (int j = rowStart + 4; j <=rowEnd; j++) {// 从相对位置的第4行开始
//				FieldInfo fieldInfo = new FieldInfo();
//				sheet.getRow(j).getCell(0).setCellType(Cell.CELL_TYPE_STRING);
//				sheet.getRow(j).getCell(1).setCellType(Cell.CELL_TYPE_STRING);
//				sheet.getRow(j).getCell(2).setCellType(Cell.CELL_TYPE_STRING);
//				sheet.getRow(j).getCell(3).setCellType(Cell.CELL_TYPE_STRING);
//				sheet.getRow(j).getCell(4).setCellType(Cell.CELL_TYPE_STRING);
//				fieldInfo.setName(sheet.getRow(j).getCell(0).getStringCellValue());
//				fieldInfo.setDatatype(sheet.getRow(j).getCell(1).getStringCellValue());
//				fieldInfo.setComments(sheet.getRow(j).getCell(2).getStringCellValue());
//				fieldInfo.setNullable(sheet.getRow(j).getCell(3).getStringCellValue());
//				fieldInfo.setDatalength(sheet.getRow(j).getCell(4).getStringCellValue());
//				tableInfo.getFieldlist().add(fieldInfo);// 将属于这个表的列信息加入
//			}
//			tableInfoList.add(tableInfo);
//		}
//		return tableInfoList;
	}

	public static void main(String args[]) {
		System.out.println(ExcelUtil.read("C:\\Users\\xingkong\\Desktop\\1.xls"));
	}
}
