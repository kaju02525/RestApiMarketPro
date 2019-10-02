package com.marketpro.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "gallery")
public class GalleryModel {

    @ApiModelProperty(hidden = true)
    @Id
    private String avatar_id;
    private String vender_id;
    private String gallery_avatar;
}

