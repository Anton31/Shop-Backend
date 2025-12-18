package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.*;
import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.Type;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ResponseProductDto getProducts(Long typeId, Long brandId, String sort,
                                   String dir, int page, int size);

    Product getProduct(long id);

    List<Type> getAllTypes(String sort, String dir);

    List<Brand> getAllBrands(String sort, String dir);

    List<Type> getProductTypes(String sort, String dir);

    List<Brand> getAllBrandsByTypeId(Long typeId, String sort, String dir);

    long addProduct(ProductDto dto);

    long addType(TypeDto dto);

    long addBrand(BrandDto dto);

    long deleteProduct(long productId) throws IOException;

    long addPhotos(PhotoDto dto);

    long removePhoto(long productId, long photoId);

    long removePhotos(long productId);

    long deleteType(long id);

    long deleteBrand(long id);

}
