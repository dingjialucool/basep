package com.chinobot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.extern.slf4j.Slf4j;

/**
 * excel读写工具类
 */
public class POITest {

	private final static String xls = "xls";
	private final static String xlsx = "xlsx";
	
	private final static Map<String, String> role = new HashMap<String, String>();
	private final static Map<String, String> dept = new HashMap<String, String>();
	static {
		role.put("系统管理员","01");
		role.put("无人机操作员","02");
		role.put("执法车驾驶员","03");
		role.put("组长","08d2e8b0bb4444ec97097a35ddd7d8dc");
		role.put("合同审核","0bad0bf61f6f4d88b4d7b2f41ec6533f");
		role.put("督察员","142ccf702e3d4f7eb3a6382ae5cb4be9");
		role.put("内勤(站级)","1847e6c8b9704e6a849b00f387696f48");
		role.put("督察队长","31c466e1a8a54bdca4d7231f281bd669");
		role.put("一线网格员","3e316332dff5411c9705315f22a437a4");
		role.put("业务员","4480aac27cc34ea5b24c4e1607af074a");
		role.put("副中队长","5213d3c9841a4e9992a05063fd1fad59");
		role.put("岗前人员(站级)","553159d87ace448f86e67f3e04c01ebf");
		role.put("人力资源","5c2d9df76c6f442b80e7bada25179d0e");
		role.put("辅助执法","7c912a638b95491eb26fa0ac6c15c74e");
		role.put("岗前人员(中心级)","88c4ad9a963f4a9c981d97c4d9c6c5e3");
		role.put("文秘","9c8c2cd0046b41aea55637d9c43993dc");
		role.put("代征税开票","a127310085c04c449e6892fcb7b70baf");
		role.put("副组长","aaf212438d0042a584171e307442d27f");
		role.put("财务","bab6fe0c552d4b7a999a6a1ca8aedab1");
		role.put("档案管理","c821c8b6b3cb49ef8c52a1b58be4a6f5");
		role.put("军事教官","d1a6f5644703417988058b139dc0903f");
		role.put("岗位","dbe9b248d18141fbb2e369c3babf53fb");
		role.put("内勤(中心)","e0c007f689f84155a6bab20ac3a06eb8");
		role.put("技术员","e4f19838bc104a2fbb70cc68809ecad0");
		role.put("中队长","ebb7061c09a1468e84a386bf29a867ea");
		role.put("后勤","ed2cff8424e34bed92b04866cadfe005");
		role.put("办事员","ef51e688b2ad4fba9b0928ccabaea384");
		role.put("房屋信息编码员","f7b6c3b6ba154abe83acedaccd9b2f29");
		
		dept.put("应人石社区网格巡查中队","193e31be35db4a0b8bed05858577cd64");
		dept.put("督察队","65ec2d82df6f4baab5a05ad3b317d2e7");
		dept.put("罗租社区网格巡查中队","7f4800793c804f148e9e8cef51a39db9");
		dept.put("官田社区网格巡查中队","9098a5f235474b1584637052da6e11ea");
		dept.put("石岩街道网格巡查大队","909a52e5fdfc40e18da3aa1471bb7e58");
		dept.put("龙腾社区网格巡查中队","97753280e1224cf7ae6c78f69fe295cb");
		dept.put("浪心社区网格巡查中队","b0320b12e79c40a492b62809f7ec9ec0");
		dept.put("石龙社区网格巡查中队","b2e7c5737cc14c3480366beb83dcb950");
		dept.put("上屋社区网格巡查中队","cd89b5b51b224fcd970c34014260e31c");
		dept.put("租赁业务组","d0607780bf5b48bb88c29b38d1abc002");
		dept.put("综合组","d1c773011ece44f78cddbc74062afc82");
		dept.put("浪心社区宝源网格巡查中队","dcc25fb2b64c4a9984dbd25928abd257");
		dept.put("塘头社区网格巡查中队","f3b9c3a977204853921d6882c58e04fb");
		dept.put("水田社区网格巡查中队","f49a13a1456f491cb255c8adce0ab5b1");
	}
	
	public static void main(String[] args) {
		try {
			String pid = "909a52e5fdfc40e18da3aa1471bb7e58";
			int dcode = 11000;
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select uuid from cle_dept where parent_id='"+pid+"' order by LENGTH(dname)");
            while(rs.next()) {
            	String uuid = rs.getString("uuid");
            	dcode += 1;
            	String sql = "update cle_dept set dcode='" + dcode + "' where uuid='" + uuid + "';";
            	System.out.println(sql);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
	}
		
	public static void main2(String[] args) {
		File xls = new File("F:\\\\test.xls");
		Set<String> set = new HashSet<String>();
		try {
			List<String[]> ls = readExcel(xls);
			for (String[] s : ls) {
				if(s[2].equals("石岩")) {
					set.add(s[0] + "-" + s[1] + "-" + s[5] + "-" + s[6] + "-" + s[9]);
				}
			}
			for (String s : set) {
//				System.out.println(s);
				String uuid = UUID.randomUUID().toString().replace("-", "");
//				String[] arr = s.split("-");
//				String personId = person.get(arr[0]);
//				String roleId = role.get(arr[1]);
//				System.out.println("insert into cle_user_role(uuid, person_id, role_id) values(\""+uuid+"\", \""+personId+"\", \""+roleId+"\");");
				
				String[] arr = s.split("-");
				String deptId = dept.get(arr[2]);
				String sql = "";
				try {
					sql = "insert into cle_person(uuid, dept_id, pname, pcode, duties, phone) values(\""+uuid+"\", \""+deptId+"\", \""+arr[0]+"\", \""+arr[1]+"\", \""+arr[3]+"\", \""+arr[4]+"\");";
				} catch (Exception e) {
					// TODO: handle exception
					sql = "insert into cle_person(uuid, dept_id, pname, pcode, duties) values(\""+uuid+"\", \""+deptId+"\", \""+arr[0]+"\", \""+arr[1]+"\", \""+arr[3]+"\");";
				}
				try {
					Connection conn = getConnection();
					Statement stmt = conn.createStatement();
	                System.out.println(sql);
                    stmt.execute(sql);
                    stmt.close();
                    conn.close();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
				
				String uuid2 = UUID.randomUUID().toString().replace("-", "");
				String roleId = role.get(arr[3]);
				sql = "insert into cle_user_role(uuid, person_id, role_id) values(\""+uuid2+"\", \""+uuid+"\", \""+roleId+"\");"; 
				try {
					Connection conn = getConnection();
					Statement stmt = conn.createStatement();
	                System.out.println(sql);
                    stmt.execute(sql);
                    stmt.close();
                    conn.close();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
				
//				System.out.println("insert into cle_dept(uuid, parent_id, dname) values(\""+uuid+"\", \"909a52e5fdfc40e18da3aa1471bb7e58\", \""+s+"\");");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 读入excel文件，解析后返回
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static List<String[]> readExcel(File file) throws IOException {
		// 检查文件
		checkFile(file);
		// 获得Workbook工作薄对象
		Workbook workbook = getWorkBook(file);
		// 创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
		List<String[]> list = new ArrayList<String[]>();
		if (workbook != null) {
			for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
				// 获得当前sheet工作表
				Sheet sheet = workbook.getSheetAt(sheetNum);
				if (sheet == null) {
					continue;
				}
				// 获得当前sheet的开始行
				int firstRowNum = sheet.getFirstRowNum();
				// 获得当前sheet的结束行
				int lastRowNum = sheet.getLastRowNum();
				// 循环除了第一行的所有行
				for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
					// 获得当前行
					Row row = sheet.getRow(rowNum);
					if (row == null) {
						continue;
					}
					// 获得当前行的开始列
					int firstCellNum = row.getFirstCellNum();
					// 获得当前行的列数
					int lastCellNum = row.getPhysicalNumberOfCells();
					String[] cells = new String[row.getPhysicalNumberOfCells()];
					// 循环当前行
					for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
						Cell cell = row.getCell(cellNum);
						cells[cellNum] = getCellValue(cell);
					}
					list.add(cells);
				}
			}
			workbook.close();
		}
		return list;
	}

	public static void checkFile(File file) throws IOException {
		// 判断文件是否存在
		if (null == file) {
			throw new FileNotFoundException("文件不存在！");
		}
		// 获得文件名
		String fileName = file.getName();
		// 判断文件是否是excel文件
		if (!fileName.endsWith(xls) && !fileName.endsWith(xlsx)) {
			throw new IOException(fileName + "不是excel文件");
		}
	}

	public static Workbook getWorkBook(File file) {
		// 获得文件名
		String fileName = file.getName();
		// 创建Workbook工作薄对象，表示整个excel
		Workbook workbook = null;
		try {
			// 获取excel文件的io流
			InputStream is = new FileInputStream(file);
			// 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
			if (fileName.endsWith(xls)) {
				// 2003
				workbook = new HSSFWorkbook(is);
			} else if (fileName.endsWith(xlsx)) {
				// 2007
				workbook = new XSSFWorkbook(is);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}

	public static String getCellValue(Cell cell) {
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		// 把数字当成String来读，避免出现1读成1.0的情况
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		// 判断数据的类型
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: // 数字
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING: // 字符串
			cellValue = String.valueOf(cell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN: // Boolean
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA: // 公式
			cellValue = String.valueOf(cell.getCellFormula());
			break;
		case Cell.CELL_TYPE_BLANK: // 空值
			cellValue = "";
			break;
		case Cell.CELL_TYPE_ERROR: // 故障
			cellValue = "非法字符";
			break;
		default:
			cellValue = "未知类型";
			break;
		}
		return cellValue;
	}
	

    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://192.168.0.10:3306/cityle?user=root&password=123456&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&nullNamePatternMatchesAll=true&remarks=true&useInformationSchema=true";
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }
}