package tech.soulcoder.dao.mapper.generator;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import tech.soulcoder.dao.pojo.generator.SoulUserDTO;
import tech.soulcoder.dao.pojo.generator.SoulUserDTOExample;

public interface SoulUserDTOMapper {
    long countByExample(SoulUserDTOExample example);

    int deleteByExample(SoulUserDTOExample example);

    int deleteByPrimaryKey(String id);

    int insert(SoulUserDTO record);

    int insertSelective(SoulUserDTO record);

    List<SoulUserDTO> selectByExample(SoulUserDTOExample example);

    SoulUserDTO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SoulUserDTO record, @Param("example") SoulUserDTOExample example);

    int updateByExample(@Param("record") SoulUserDTO record, @Param("example") SoulUserDTOExample example);

    int updateByPrimaryKeySelective(SoulUserDTO record);

    int updateByPrimaryKey(SoulUserDTO record);
}