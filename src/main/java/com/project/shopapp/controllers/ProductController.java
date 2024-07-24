package com.project.shopapp.controllers;

import com.github.javafaker.Faker;
import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.dtos.response.ApiResponse;
import com.project.shopapp.dtos.response.ProductListResponse;
import com.project.shopapp.dtos.response.ProductResponse;
import com.project.shopapp.mapper.ProductMapper;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.services.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class ProductController {
    ProductService productService;
    ProductMapper productMapper;
    @GetMapping("/paged")
    public ResponseEntity<ProductListResponse> getPagedProducts(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="10") int limit
    ){
        //create Pageable from page and limit
        PageRequest pageRequest = PageRequest.of(page, limit,
                Sort.by("createdAt").descending()
                );
        Page<ProductResponse> productPage = productService.getProductsPaginated(pageRequest);
        //get total pageRequest
        int totalPages = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse.builder()
                .products(products)
                .totalPages(totalPages)
                .build());
    }
    @GetMapping("")
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable long id) {
        try {
            Product product = productService.getProductById(id);
            ProductResponse productResponse = productMapper.toProductResponse(product);
            return ResponseEntity.ok(ApiResponse.builder()
                    .result(productResponse)
                    .message("Product found")
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.builder()
                            .message("Product not found")
                            .build());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody ProductDTO productDTO,
            BindingResult result
    ){
        try {
            //check validation
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream() //can than getFieldError va getFieldErrors
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Product newProduct = productService.createProduct(productDTO);

            return ResponseEntity.ok(ApiResponse.builder()
                    .result(newProduct)
                    .message("Product created successfully")
                    .build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping(value = "/uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @PathVariable("id") Long  productId,
            @ModelAttribute("files") List<MultipartFile> files
    ){
        try{
            Product existProduct = productService.getProductById(productId);
            //check files is null
            files = files == null ? new ArrayList<MultipartFile>() : files;
            if(files.size() >5){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Maximum 5 images are allowed");
            }
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file:files) {
                if(file.isEmpty()){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("File is empty!");
                }
                //check size file
                if(file.getSize()==0){
                    continue;
                }
                //check size file and format
                if(file.getSize() > 10*1024*1024){
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is to large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")){
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
                //save file and update thumbnail
                String filename = storeFile((file));
                //save product to db
                ProductImage productImage = productService.createProductImage(
                        existProduct.getId(),
                        ProductImageDTO.builder()
                        .imageUrl(filename)
                        .build());
                productImages.add(productImage);
            }
            return ResponseEntity.ok(productImages);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    private String storeFile(MultipartFile file) throws IOException{
        if(file.getOriginalFilename() == null){
            throw new IOException("File is empty!");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        //add UUID into filename image
        String uniqueFilename = UUID.randomUUID().toString()+"_"+filename;
        //path folder save image
        Path uploadDir = Paths.get("uploads");
        //check and create folder if folder not exits
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        // path full to file
        Path destination = Paths.get(uploadDir.toString(),uniqueFilename);
        //copy file to folder
        Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable long id, @Valid @RequestBody ProductDTO productDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            Product updatedProduct = productService.updateProduct(id, productDTO);
            ProductResponse productResponse = productMapper.toProductResponse(updatedProduct);

            return ResponseEntity.ok(ApiResponse.builder()
                    .result(productResponse)
                    .message("Product updated successfully")
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Product has been deleted successfully")
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/generateFakeProducts")
    private ResponseEntity<?> generateFakeProducts(){
        Faker  faker = new Faker();
        for(int i = 0; i< 50; i++){
            String productName = faker.commerce().productName();
            if(productService.existsByName(productName)){
                continue;
            }

            ProductDTO productDTO = ProductDTO.builder()
                    .name(productName)
                    .price((float)faker.number().numberBetween(10000, 1000000))
                    .description(faker.lorem().sentence())
                    .thumbnail("")
                    .categoryId((long)faker.number().numberBetween(3, 5))
                    .build();
            try{
                productService.createProduct(productDTO);
            }catch (Exception e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok("Fake products generated");
    }
}
