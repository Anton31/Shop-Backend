package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.*;
import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Photo;
import com.example.authserverresourceserversameapp.model.Type;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ResponseProductDto getProducts(Long typeId, Long brandId, String sort,
                                   String dir, int page, int size);

    List<Type> getAllTypes(String sort, String dir);

    List<Type> getProductTypes();

    List<Brand> getProductBrands(Long typeId, String sort, String dir);

    long addProduct(ProductDto dto);

    long addType(TypeDto dto);

    long addBrand(BrandDto dto);

    long deleteProduct(long id) throws IOException;

    long addPhotos(PhotoDto dto);

    long removePhoto(long productId, long photoId);

    long removePhotos(long productId);

    Photo getPhoto(long photoId);

    long deleteType(long id);

    long deleteBrand(long id);

}
