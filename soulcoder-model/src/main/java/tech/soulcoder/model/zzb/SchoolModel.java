package tech.soulcoder.model.zzb;
import java.io.Serializable;
import java.util.List;

/**
 *       文科类
 *       普通批次
 *       1106.0
 *       南京信息工程大学
 *       1.0
 *       三年级
 *       会计学
 *       50.0
 *       5200.0
 *       5605工程管理类 、 6201财政金融类 、 6202财务会计类 、 6203经济贸易类 、 6204市场营销类 、 6205工商管理类 、 6401旅游管理类
 *       读取学校表格
 */
public class SchoolModel implements Serializable {
    private static final long serialVersionUID = -5474669356984241365L;
    /**
     * 学科  例如文科类
     */
    private String subject;
    /**
     * 批次  例如普通批次
     */
    private String batch;
    /**
     * 学院代号  例如1106
     */
    private String code;
    /**
     * 学校名字
     */
    private String shcoolname;
    // 专业代号
    private String subjectCode;

    // 所在年级
    private String grade;
    // 专业名称
    private String subjectName;
    // 计划招生
    private String planNum;
    /**
     * 学费
     */
    private String shcoolFee;
    /**
     * 专业限制
     */
    private String requireString;

    private List<SubjectCodeModel> require;

    public String getRequireString() {
        return requireString;
    }

    public void setRequireString(String requireString) {
        this.requireString = requireString;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShcoolname() {
        return shcoolname;
    }

    public void setShcoolname(String shcoolname) {
        this.shcoolname = shcoolname;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getPlanNum() {
        return planNum;
    }

    public void setPlanNum(String planNum) {
        this.planNum = planNum;
    }

    public String getShcoolFee() {
        return shcoolFee;
    }

    public void setShcoolFee(String shcoolFee) {
        this.shcoolFee = shcoolFee;
    }

    public List<SubjectCodeModel> getRequire() {
        return require;
    }

    public void setRequire(List<SubjectCodeModel> require) {
        this.require = require;
    }

    @Override
    public String toString() {
        return "SchoolModel{" +
                "subject='" + subject + '\'' +
                ", batch='" + batch + '\'' +
                ", code='" + code + '\'' +
                ", shcoolname='" + shcoolname + '\'' +
                ", subjectCode='" + subjectCode + '\'' +
                ", grade='" + grade + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", planNum='" + planNum + '\'' +
                ", shcoolFee='" + shcoolFee + '\'' +
                ", requireString='" + requireString + '\'' +
                ", require=" + require +
                '}';
    }
}
