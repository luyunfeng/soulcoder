package tech.soulcoder.model.zzb;

import java.io.Serializable;
import java.util.List;

public class SubjectCodeModel implements Serializable {
    private static final long serialVersionUID = -5474339756984241365L;

    private String code;
    private String name;
    private List<SchoolModel> schoolList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SchoolModel> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List<SchoolModel> schoolList) {
        this.schoolList = schoolList;
    }

    @Override
    public String toString() {
        return "SubjectCode{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", schoolList=" + schoolList +
                '}';
    }
}
