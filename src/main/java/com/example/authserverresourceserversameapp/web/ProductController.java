package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.*;
import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Type;
import com.example.authserverresourceserversameapp.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/type")
    public List<Type> getAllTypes(@RequestParam(required = false, defaultValue = "ASC") String dir,
                                  @RequestParam(required = false, defaultValue = "name") String sort) {
        return productService.getAllTypes(dir, sort);
    }

    @GetMapping("/productType")
    public List<Type> getProductTypes() {
        return productService.getProductTypes();
    }

    @GetMapping("/brand")
    public List<Brand> getBrands(@RequestParam(required = false) Long typeId,
                                 @RequestParam(required = false, defaultValue = "ASC") String dir,
                                 @RequestParam(required = false, defaultValue = "name") String sort) {
        return productService.getAllBrands(typeId, dir, sort);
    }

    @GetMapping(value = "/product")
    public ResponseProductDto getProducts(@RequestParam(required = false) Long typeId,
                                          @RequestParam(required = false) Long brandId,
                                          @RequestParam(required = false, defaultValue = "name") String sort,
                                          @RequestParam(required = false, defaultValue = "ASC") String dir,
                                          @RequestParam(required = false, defaultValue = "0") int page,
                                          @RequestParam(required = false, defaultValue = "10") int size) {
        return productService.getProducts(typeId, brandId, sort, dir, page, size);
    }

    @PostMapping("/product")
    public long addProduct(ProductDto dto) {
        return productService.addProduct(dto);
    }

    @PutMapping("/product")
    public long editProduct(ProductDto dto) {
        return productService.addProduct(dto);
    }

    @DeleteMapping("/product/{id}")
    public long deleteProduct(@PathVariable long id) throws IOException {
        return productService.deleteProduct(id);
    }

    @PostMapping("/photo")
    public long addPhoto(PhotoDto dto) {
        return productService.addPhoto(dto);
    }

    @DeleteMapping("/photo/{productId}")
    public long deletePhoto(@PathVariable long productId) {
        return productService.removePhotos(productId);
    }

    @PostMapping("/type")
    public long addType(TypeDto dto) {
        return productService.addType(dto);
    }

    @PutMapping("/type")
    public long editType(TypeDto dto) {
        return productService.addType(dto);
    }

    @DeleteMapping("/type/{id}")
    public long deleteType(@PathVariable long id) {
        return productService.deleteType(id);
    }

    @PostMapping("/brand")
    public long addBrand(BrandDto dto) {
        return productService.addBrand(dto);
    }

    @PutMapping("/brand")
    public long editBrand(BrandDto dto) {
        return productService.addBrand(dto);
    }

    @DeleteMapping("/brand/{id}")
    public long deleteBrand(@PathVariable long id) {
        return productService.deleteBrand(id);
    }
}
