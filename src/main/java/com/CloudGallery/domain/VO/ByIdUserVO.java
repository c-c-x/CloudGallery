package com.CloudGallery.domain.VO;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;


@Data
@TableName("cg_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ByIdUserVO implements Serializable {

    private Long id;

    private String userName;

    private String nickName;

    private String phone;

    private String email;

    private Integer gender;

    private String idNumber;

    private Integer status;

}
