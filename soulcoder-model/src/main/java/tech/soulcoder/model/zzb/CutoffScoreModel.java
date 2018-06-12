package tech.soulcoder.model.zzb;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "CutoffScoreModel", description = "每年分数线")
public class CutoffScoreModel {

    @ApiModelProperty(value = "类别比如文科 理科")
    private String type;

    @ApiModelProperty(value = "学校编号")
    private String schoolCode;

    @ApiModelProperty(value = "学校名字")
    private String schoolName;

    @ApiModelProperty(value = "专业编号")
    private String subjectCode;

    @ApiModelProperty(value = "专业名字")
    private String subjectName;

    @ApiModelProperty(value = "分数线")
    private String cutoffScore;

    @ApiModelProperty(value = "年份")
    private String year;

}
