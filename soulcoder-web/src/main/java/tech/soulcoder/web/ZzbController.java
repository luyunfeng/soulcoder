package tech.soulcoder.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.soulcoder.model.zzb.SchoolModel;
import tech.soulcoder.model.zzb.SubjectCodeModel;
import tech.soulcoder.service.ZzbService;
import tech.soulcoder.zzb.ExcelTool;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/zzb")
@Api("海豚科技")
public class ZzbController {
    private static final Logger logger = LoggerFactory.getLogger(ZzbController.class);
    @Autowired
    private ExcelTool excelTool;

    @Autowired
    private ZzbService zzbService;

    @ApiOperation(value = "获取所有专业分类", notes = "0000表示二级类目")
    @GetMapping("/getAllClass")
    public Map getAllClass() {
        try {
            return excelTool.getAllsubjectCode();
        } catch (Exception e) {
            logger.error("系统异常", e);
        }
        return null;
    }

    @ApiOperation(value = "获取所有二级分类", notes = "例如计算机类的二级分类，二级分类code是四位数")
    @GetMapping("/getAllSecondClass")
    public List<SubjectCodeModel> getAllSecondClass() {
        try {
            return zzbService.getAllSecondClass();
        } catch (Exception e) {
            logger.error("系统异常", e);
        }
        return null;
    }

    @ApiOperation(value = "根据二级分类查询三级分类", notes = "如根据计算机类，传入计算机类的二级代码，返回所有三级分类")
    @GetMapping("/get3ClassBySecondClass/{secondClassCode}")
    public List<SubjectCodeModel> get3ClassBySecondClass(
            @PathVariable String secondClassCode) {
        try {
            return zzbService.get3ClassBySecondClass(secondClassCode);
        } catch (Exception e) {
            logger.error("系统异常", e);
        }
        return null;
    }


    @ApiOperation(value = "根据三级分类查询所有可报考的学校", notes = "入参是三级分类classCode")
    @GetMapping("/getAvailableSchoolBy3Class/{classCode}")
    public List<SchoolModel> getAvailableSchoolBy3Class(
            @PathVariable String classCode) {
        try {
            return zzbService.getAvailableSchoolBy3Class(classCode);
        } catch (Exception e) {
            logger.error("系统异常", e);
        }
        return null;
    }

}