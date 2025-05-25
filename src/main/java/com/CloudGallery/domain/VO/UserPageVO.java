package com.CloudGallery.domain.VO;

import com.CloudGallery.domain.DTO.UserListDTO;
import com.CloudGallery.domain.VO.PageVo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPageVO extends PageVo {

    private List<UserListDTO> list;

}
