package com.marketpro.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@EqualsAndHashCode
@Document(collection = "category")
public class Category {

	@ApiModelProperty(hidden = true)
	@Id
	private String category_id;
	private String category_name;
	private String category_avatar;

	@ApiModelProperty(hidden = true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date create_at = new Date();

}
