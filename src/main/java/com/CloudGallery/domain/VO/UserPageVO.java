package com.CloudGallery.domain.VO;

import com.CloudGallery.domain.DTO.admin.UserListDTO;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPageVO extends PageVo {

    private List<UserListDTO> list;

}
