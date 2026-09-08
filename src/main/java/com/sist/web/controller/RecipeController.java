package com.sist.web.controller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.web.service.RecipeService;
import com.sist.web.service.RecipeService;

import lombok.RequiredArgsConstructor;

/*
 * 	1. 전체 동작 과정
 * 		<브라우저> : HTML / JavaScript(바닐라JS)
 * 			| = 재료 선택
 * 		ThymeLeaf
 * 			| post/recipe/recommand
 * 		RecipeController
 * 			|	@GetMapping("/recipe/recommand")
 * 			|	@PostMapping("/recipe/recommand")	
 * 				@ResponseBody => 문자열 / JSON 전송
 * 					=> @RestController로 변경
 * 			|	ingredients 전달 (재료)
 * 		RecipeService
 * 			|
 * 			1) 재료 존재 여부 확인
 * 			2) 검색문장 생성
 * 			3) EmbeddingModel 생성
 * 			4) String => float[] 변경
 * 						 ------- vector
 * 			5) PostgresSQL+pgVector => 유사 검색
 * 			   -------------------- Like
 * 			6) 레시피에서 content 추출
 * 			7) 냉장고 => 레시피 재료 비교
 * 			8) 재료 상태 결정 (부족, 전체 만족)
 * 			9) 재료 충족률 계산
 * 		추천 레시피 List => Limit 5
 * 		------------------------
 * 			| = 보유재료
 * 			| = 부족 재료
 * 			| = 재료 충족률
 * 			| = 레시피명
 * 			| = 조리방법
 * 			| = 요리 종류
 * 			| = 조리 과정
 * 
 * 		------------------------
 * 		ThymeLeaf 화면
 * 			=> HTML = Controller = RecipeService
 * 				------------------- Spring AI
 * 				= EmbeddingModel = PostgresSQL + pgVector 
 * 				= 유사 레시피 검색
 * 				= 재료 확인 
 * 				--------------------------HTML에서 출력
 * 				
 * 		=>  1. JavaScript : Pinia
 * 			2. @ResponseBody: @RestController
 * 			3. @Tool => Tool Calling
 * 						------------ 프롬프트 (검색)
 * 			4. 기능별 분리 : MCP	
 * 			
 */
@Controller
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {


    /*
     * Vector 검색 Service
     */
    private final RecipeService recipeVectorService;


    /**
     * ========================================================
     * 레시피 추천 화면
     * ========================================================
     *
     * GET
     *
     * http://localhost:8080/recipe/recommand
     */
    @GetMapping("/recommand")
    public String recommandPage(Model model) {

        /*
         * 처음에는 검색 결과가 없도록 설정
         */
        model.addAttribute(
                "recipes",
                Collections.emptyList()
        );

        return "recipe/recommand";
    }


    /**
     * ========================================================
     * 레시피 Vector 검색
     * ========================================================
     *
     * POST
     *
     * /recipe/recommand
     *
     * JSON
     *
     * {
     *   "ingredients": [
     *      "김치",
     *      "돼지고기",
     *      "두부"
     *   ]
     * }
     */
    @PostMapping("/recommand")
    @ResponseBody
    public Map<String, Object> recommand(
            @RequestBody Map<String, Object> request) {

        Map<String, Object> response =
                new HashMap<>();


        try {

            /*
             * JSON에서 ingredients 추출
             */
            Object ingredientObject =
                    request.get("ingredients");

            /*
             * 재료가 없는 경우
             */
            if (ingredientObject == null) {

                response.put(
                        "success",
                        false
                );

                response.put(
                        "message",
                        "재료를 선택해주세요."
                );

                response.put(
                        "recipes",
                        Collections.emptyList()
                );

                return response;
            }


            /*
             * JSON 배열 → List<String>
             */
            List<String> ingredients =
                    new ArrayList<>();

            if (ingredientObject instanceof List<?>) {

                List<?> list =
                        (List<?>) ingredientObject;

                for (Object value : list) {

                    if (value != null) {

                        String ingredient =
                                value.toString().trim();

                        if (!ingredient.isEmpty()) {

                            ingredients.add(
                                    ingredient
                            );
                        }
                    }
                }
            }


            /*
             * 선택 재료가 없는 경우
             */
            if (ingredients.isEmpty()) {

                response.put(
                        "success",
                        false
                );

                response.put(
                        "message",
                        "재료를 한 개 이상 선택해주세요."
                );

                response.put(
                        "recipes",
                        Collections.emptyList()
                );

                return response;
            }


            /*
             * =================================================
             * Vector 검색
             * =================================================
             */
            List<Map<String, Object>> recipes =
                    recipeVectorService.recommandRecipes(
                            ingredients
                    );


            /*
             * 정상 응답
             */
            response.put(
                    "success",
                    true
            );

            response.put(
                    "message",
                    recipes.isEmpty()
                            ? "추천 레시피가 없습니다."
                            : "레시피 추천이 완료되었습니다."
            );

            response.put(
                    "recipes",
                    recipes
            );


            /*
             * 사용자가 선택한 재료도 반환
             */
            response.put(
                    "selectedIngredients",
                    ingredients
            );


            return response;


        } catch (Exception e) {

            /*
             * 서버 로그
             */
            e.printStackTrace();


            /*
             * 오류 응답
             */
            response.put(
                    "success",
                    false
            );

            response.put(
                    "message",
                    "레시피 검색 중 오류가 발생했습니다."
            );

            response.put(
                    "recipes",
                    Collections.emptyList()
            );

            return response;
        }
    }
}

