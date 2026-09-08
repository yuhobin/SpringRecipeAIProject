package com.sist.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *   id        | bigint      |           | not null | nextval('recipe_vector_id_seq'::regclass)
	 recipe_id | bigint      |           | not null |
	 content   | text        |           | not null |
	 embedding | vector(768) |           |          |
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*
 * 	RecipeVectorVO vo=RecipeVectorVO.builder()
 * 						.recipeId(100)
 * 						.content()
 * 						.embedding()
 * 						.build()
 * 		  Oracle
 * 		  	|
 * 		PostgreSQL
 * 			|
 * 		-------------------
 * 		|				  |
 * 		recipe 원본		recipe_vector
 * 						  |
 * 						Content
 * 						  |
 * 						Embedding
 * 						  |
 * 						Vector
 * 						  |
 * 						유사 레시피 검색 => 어떤식으로 검색
 * 						  |
 * 						부족한 재료 계산
 * 						  |
 * 						Gemini
 * 						  |
 * 						최종 레시피 생성
 * 						  
 */
public class RecipeVectorVO {
	private Long id;
	private Long recipe_id;
	private String content;
	private String embedding;
}
