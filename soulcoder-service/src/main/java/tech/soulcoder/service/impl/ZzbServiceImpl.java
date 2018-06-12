package tech.soulcoder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.soulcoder.model.zzb.SchoolModel;
import tech.soulcoder.model.zzb.SubjectCodeModel;
import tech.soulcoder.service.ZzbService;
import tech.soulcoder.zzb.ExcelTool;

import java.util.List;
import java.util.Map;

/**
 * Map<String, List<SchoolModel>>
 * Map<String, List<SubjectCodeModel>>
 */
@Service
public class ZzbServiceImpl implements ZzbService {
    @Autowired
    private ExcelTool excelTool;

    /**
     * 获取所有二级学科
     */
    @Override
    public List<SubjectCodeModel> getAllSecondClass() throws Exception {
        return excelTool.getAllsubjectCode().get("0000");
    }

    /**
     * 根据二级分类获取所有三级分类
     *
     * @param secondClassCode
     * @return
     */
    @Override
    public List<SubjectCodeModel> get3ClassBySecondClass(String secondClassCode) throws Exception {
        return excelTool.getAllsubjectCode().get(secondClassCode);
    }

    /**
     * 根据三级类目获取所有的 可报考学校
     *
     * @param secondClassCode
     * @return
     */
    @Override
    public List<SchoolModel> getAvailableSchoolBy3Class(String secondClassCode) throws Exception {
        return excelTool.getAll().get(secondClassCode);
    }
}
