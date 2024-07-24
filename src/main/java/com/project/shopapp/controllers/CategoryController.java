package com.project.shopapp.controllers;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.dtos.response.ApiResponse;
import com.project.shopapp.models.Category;
import com.project.shopapp.services.CategoryService;
import com.project.shopapp.services.ICategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    ICategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                            BindingResult result
    ){
        //check validation
        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors().stream() //can than getFieldError va getFieldErrors
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }

        Category category =categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                .result(category)
                .message("category created successfully")
                .build());
    }
    @GetMapping("")
    public ResponseEntity<List<Category>> getCategories(){
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable long id){
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable long id,@Valid @RequestBody CategoryDTO categoryDTO){
        Category category = categoryService.updateCategory(id,categoryDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                .result(category)
                .message("category updated successfully")
                .build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("item has been deleted");
    }
}
