package tech.soulcoder.zzb;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.soulcoder.StringUtil;
import tech.soulcoder.model.zzb.SchoolModel;
import tech.soulcoder.model.zzb.SubjectCodeModel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 1.用户选择三级类目
 * 2.取出前面四位数就是二级类目
 * 3.查出用户所有能报名的 三级类目
 * <p>
 * school 表的处理
 * 1. 读取文件的得到的数据    一个学校 ---- 多个二级类目(三级)
 * 2.   归并 之后        一个 三级类目---  对应多个学校
 * <p>
 * 查询方式输入 三级()类目   去掉后面两位  形成二级类目  ，找出二级类目下面的所有三级类目符合的学校
 */
@Component
@Scope("singleton")
public class ExcelTool {
    private static final Logger logger = LoggerFactory.getLogger(ExcelTool.class);

    @Value("${localFilePath}")
    private String path;
    private String excelPathSchool = "school.xlsx";
    private String excelPathSub = "p.xlsx";
    private Map<String, List<SubjectCodeModel>> allsubjectCode;
    /**
     * 一个三级类目 对应的 所有学校
     */
    private Map<String, List<SchoolModel>> all;

    /**
     * 读取学科表
     * "5101":[{"code":"510101","name":"作物生产技术"}
     *
     * @return
     * @throws Exception
     */
    private Map<String, List<SubjectCodeModel>> readExcelSub() throws Exception {
        File excelP = new File(path + excelPathSub);
        List<SubjectCodeModel> subjectCodes;
        List<SubjectCodeModel> secondSubjectCodes = new ArrayList<>();
        Map<String, List<SubjectCodeModel>> map = new HashMap<>();
        Workbook wb = new XSSFWorkbook(excelP);
        Sheet sheet = wb.getSheetAt(0);
        int firstRowIndex = sheet.getFirstRowNum();
        int lastRowIndex = sheet.getLastRowNum();
        SubjectCodeModel subjectCode;
        for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
            Row row = sheet.getRow(rIndex);
            if (row != null && !StringUtil.isEmpty(row.getCell(0).toString())
                    && !StringUtil.isEmpty(row.getCell(1).toString())) {
                subjectCode = new SubjectCodeModel();
                subjectCode.setCode(row.getCell(0).toString().replace(" ", "").replace(".0", ""));
                subjectCode.setName(row.getCell(1).toString().replace(" ", ""));
                if (subjectCode.getCode().length() > 4) {
                    String second = subjectCode.getCode().substring(0, 4);
                    if (map.get(second) == null) {
                        subjectCodes = new ArrayList<>();
                    } else {
                        subjectCodes = map.get(second);
                    }
                    subjectCodes.add(subjectCode);
                    map.put(second, subjectCodes);
                    subjectCodes = null;
                } else if (subjectCode.getCode().length() == 4) {
                    // 二级 统一 用 0000
                    secondSubjectCodes.add(subjectCode);
                }
            }
        }
        // 二级学科 key 为0000
        map.put("0000", secondSubjectCodes);
        return map;
    }

    /**
     * 文科类
     * 普通批次
     * 1106.0
     * 南京信息工程大学
     * 1.0
     * 三年级
     * 会计学
     * 50.0
     * 5200.0
     * 5605工程管理类 、 6201财政金融类 、 6202财务会计类 、 6203经济贸易类 、 6204市场营销类 、 6205工商管理类 、 6401旅游管理类
     * 读取学校表格
     *
     * @return
     */
    private Map<String, List<SchoolModel>> readExcelSchool() throws Exception {
        File excelSchool = new File(path + excelPathSchool);
        Workbook wb = new XSSFWorkbook(excelSchool);
        Sheet sheet = wb.getSheetAt(0);
        int firstRowIndex = sheet.getFirstRowNum() + 2;
        int lastRowIndex = sheet.getLastRowNum();
        // 这里存放的是 一个学校多个学科
        //List<SchoolModel> schoolModels = new ArrayList<>();
        Map<String, List<SchoolModel>> resMap = new HashMap<>();
        // 先遍历行后遍历列
        SchoolModel schoolModel = null;
        String pattern = "[A-Za-z0-9]+";
        Pattern r = Pattern.compile(pattern);
        for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
            Row row = sheet.getRow(rIndex);
            if (row != null) {
                int firstCellIndex = row.getFirstCellNum();
                int lastCellIndex = row.getLastCellNum();
                schoolModel = new SchoolModel();
                // 循环遍历 行
                for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {
                    Cell cell = row.getCell(cIndex);
                    switch (cIndex) {
                        case 0:
                            schoolModel.setSubject(cell.toString());
                            break;
                        case 1:
                            schoolModel.setBatch(cell.toString());
                            break;
                        case 2:
                            schoolModel.setCode(cell.toString().replace(".0", ""));
                            break;
                        case 3:
                            schoolModel.setShcoolname(cell.toString());
                            break;
                        case 4:
                            schoolModel.setSubjectCode(cell.toString().replace(".0", ""));
                            break;
                        case 5:
                            schoolModel.setGrade(cell.toString());
                            break;
                        case 6:
                            schoolModel.setSubjectName(cell.toString());
                            break;
                        case 7:
                            schoolModel.setPlanNum(cell.toString().replace(".0", ""));
                            break;
                        case 8:
                            schoolModel.setShcoolFee(cell.toString());
                            break;
                        case 9:
                            schoolModel.setRequireString(cell.toString());
                            break;
                        default:
                            break;
                    }
                }
                // 开始处理 学校的学科限制
                String[] requireStringArray = schoolModel.getRequireString().split(" 、 ");
                for (int x = 0; x < requireStringArray.length; x++) {
                    SubjectCodeModel subjectCode = new SubjectCodeModel();
                    Matcher m = r.matcher(requireStringArray[x]);
                    m.find();
                    if (m.group(0).length() > 4) {
                        subjectCode.setCode(m.group(0));
                        subjectCode.setName(requireStringArray[x].replaceAll(m.group(0), ""));
                        List<SchoolModel> schoolList = resMap.get(subjectCode.getCode());
                        if (schoolList == null) {
                            schoolList = new ArrayList<>();
                            schoolList.add(schoolModel);
                        }
                        boolean flag = false;
                        for (SchoolModel s : schoolList) {
                            // 去除重复
                            if (!s.getCode().equals(schoolModel.getCode()) &&
                                    !s.getSubjectCode().equals(schoolModel.getSubjectCode())) {
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            schoolList.add(schoolModel);
                        }
                        resMap.put(subjectCode.getCode(), schoolList);
                    } else {
                        // 二级限制的 需要把二级转换成三级
                        List<SubjectCodeModel> temp = this.getAllsubjectCode().get(m.group(0));
                        if (temp != null) {
                            for (SubjectCodeModel subjectCode1 : temp) {
                                subjectCode.setCode(subjectCode1.getCode());
                                subjectCode.setName(subjectCode1.getName());
                                List<SchoolModel> schoolList = resMap.get(subjectCode.getCode());
                                if (schoolList == null) {
                                    schoolList = new ArrayList<>();
                                    schoolList.add(schoolModel);
                                }
                                boolean flag = false;
                                for (SchoolModel s : schoolList) {
                                    // 去除重复
                                    if (!(s.getCode().equals(schoolModel.getCode()) &&
                                            s.getSubjectCode().equals(schoolModel.getSubjectCode()))) {
                                        flag = true;
                                        break;
                                    }
                                }
                                if (flag) {
                                    schoolList.add(schoolModel);
                                }
                                resMap.put(subjectCode.getCode(), schoolList);
                            }
                        }
                    }
                }
            }
        }

        return resMap;
    }

    public Map<String, List<SubjectCodeModel>> getAllsubjectCode() throws Exception {
        if (allsubjectCode == null) {
            allsubjectCode = this.readExcelSub();
        }
        return allsubjectCode;
    }
    public Map<String, List<SchoolModel>> getAll() throws Exception {
        if (this.all == null) {
            all = this.readExcelSchool();
        }
        return all;
    }


    public static void main(String[] args) {
//        try {
//            FileWriter writer = new FileWriter("test.json");
//            Gson gson = new Gson();
//            Long time=System.currentTimeMillis();
//            writer.write(gson.toJson(readExcelSchool()));
//            Long last=System.currentTimeMillis()-time;
//            System.out.println(last);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        // 按指定模式在字符串查找
//        String line = "5605工程管理类";
//        String pattern = "[A-Za-z0-9]+";
//
//
//        // 创建 Pattern 对象
//        Pattern r = Pattern.compile(pattern);
//        // 现在创建 matcher 对象
//        Matcher m = r.matcher(line);
//        //if (m.find()) {
//            System.out.println("Found value: " + m.group(0));
////        } else {
////            System.out.println("NO MATCH");
////        }

    }
}
