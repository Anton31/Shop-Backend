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

    List<Type> getAllTypes(String dir, String sort);

    List<Type> getProductTypes();

    List<Brand> getAllBrandsByTypeId(long typeId, String dir, String sort);

    long addProduct(ProductDto dto);

    long addType(TypeDto dto);

    long addBrand(BrandDto dto);

    long deleteProduct(long id) throws IOException;

    long addPhoto(PhotoDto dto);

    long removePhotos(long productId);

    Photo getPhoto(long photoId);

    long deleteType(long id);

    long deleteBrand(long id);

}
