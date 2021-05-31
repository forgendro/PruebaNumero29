package com.gfi.PruebaNumero29.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModelProperty;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class PruebaNumero29Dto {
	//TODO: IMPLEMENTAR CAMPOS CORRECTOS
	@ApiModelProperty(notes = "field1", example="valor campo 1")
	private String field1;
	@ApiModelProperty(notes = "field2", example="valor campo 2")
	private String field2;
	@ApiModelProperty(notes = "field3", example="valor campo 3")
	private String field3;

}
