package tech.soulcoder.service;

import tech.soulcoder.model.zzb.SchoolModel;
import tech.soulcoder.model.zzb.SubjectCodeModel;

import java.util.List;
import java.util.Map;

public interface  ZzbService {

    /**
     * 获取所有二级学科
     */
    List<SubjectCodeModel> getAllSecondClass() throws Exception;

    /**
     * 根据二级分类获取所有三级分类
     * @param secondClassCode
     * @return
     */
    List<SubjectCodeModel> get3ClassBySecondClass(String secondClassCode) throws Exception;

    /**
     * 根据三级类目获取所有的 可报考学校
     * @param classCode
     * @return
     */
    List<SchoolModel> getAvailableSchoolBy3Class(String classCode) throws Exception;
}
