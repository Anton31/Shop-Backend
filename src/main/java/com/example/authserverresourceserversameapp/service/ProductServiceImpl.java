package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.*;
import com.example.authserverresourceserversameapp.exception.*;
import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Photo;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.Type;
import com.example.authserverresourceserversameapp.repository.BrandRepository;
import com.example.authserverresourceserversameapp.repository.PhotoRepository;
import com.example.authserverresourceserversameapp.repository.ProductRepository;
import com.example.authserverresourceserversameapp.repository.TypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String imageDir = "src/main/webapp/WEB-INF/images/";
    private static final String imageUrl = "http://localhost:8080/images/";
    private final ProductRepository productRepository;
    private final TypeRepository typeRepository;
    private final BrandRepository brandRepository;
    private final PhotoRepository photoRepository;


    public ProductServiceImpl(ProductRepository productRepository,
                              TypeRepository typeRepository,
                              BrandRepository brandRepository,
                              PhotoRepository photoRepository) {
        this.productRepository = productRepository;
        this.typeRepository = typeRepository;
        this.brandRepository = brandRepository;
        this.photoRepository = photoRepository;
    }

    /**
     * gets products from database according to request parameters
     *
     * @param typeId  id of type
     * @param brandId id of brand
     * @param sort    field for sorting
     * @param dir     direction of sorting
     * @param page    index of page
     * @param size    size of page
     * @return dto with list of products
     */
    @Override
    public ResponseProductDto getProducts(Long typeId, Long brandId,
                                          String sort, String dir,
                                          int page, int size) {
        ResponseProductDto dto = new ResponseProductDto();
        Page<Product> products;
        if (sort.equals("type")) {
            sort = "type.name";
        }
        if (sort.equals("brand")) {
            sort = "brand.name";
        }
        if (typeId == null && brandId == null) {
            products = productRepository.findAll(PageRequest.of(page, size, Sort.Direction.fromString(dir), sort));
        } else if (typeId != null && brandId == null) {
            products = productRepository.getAllByTypeId(typeId,
                    PageRequest.of(page, size, Sort.Direction.fromString(dir), sort));
        } else if (typeId == null) {
            products = productRepository.getAllByBrandId(brandId,
                    PageRequest.of(page, size, Sort.Direction.fromString(dir), sort));
        } else {
            products = productRepository.getAllByTypeIdAndBrandId(typeId,
                    brandId, PageRequest.of(page, size, Sort.Direction.fromString(dir), sort));
        }
        dto.setProducts(products.getContent());
        dto.setPageSize(products.getSize());
        dto.setCurrentPage(products.getNumber());
        dto.setTotalProducts(products.getTotalElements());
        return dto;
    }

    /**
     * gets all types from database
     *
     * @return list of types
     */
    @Override
    public List<Type> getAllTypes(String dir, String sort) {
        return typeRepository.findAll(Sort.by(Sort.Direction.fromString(dir), sort));
    }

    /**
     * gets all types from database related to products
     *
     * @return list of types related to products
     */
    @Override
    public List<Type> getProductTypes() {
        return typeRepository.getProductTypes();
    }

    /**
     * gets all brands from database with particular type id
     *
     * @param typeId id of type
     * @return list of brands
     */
    @Override
    public List<Brand> getAllBrandsByTypeId(long typeId, String dir, String sort) {
        List<Brand> brands;
        if (typeId == 0) {
            brands = brandRepository.findAll(Sort.by(Sort.Direction.fromString(dir), sort));
        } else {
            brands = brandRepository.getAllByTypesId(typeId, Sort.by(Sort.Direction.fromString(dir), sort));
        }
        return brands;
    }

    /**
     * adds new product to database or updates existing
     *
     * @param dto dto for adding new product or updating existing product
     * @return id of created or updated product
     */
    public long addProduct(ProductDto dto) {
        Product product;
        Type type = typeRepository.findById(dto.getTypeId()).orElseThrow(NoSuchElementException::new);
        Brand brand = brandRepository.findById(dto.getBrandId()).orElseThrow(NoSuchElementException::new);
        if (dto.getId() == null) {
            if (productRepository.findByName(dto.getName()) != null) {
                throw new ProductExistsException(dto.getName());
            }
            product = new Product();
            type.addProduct(product);
            brand.addProduct(product);
            if (!type.getBrands().contains(brand)) {
                type.addBrand(brand);
            }
        } else {
            product = productRepository.findById(dto.getId()).orElseThrow(NoSuchElementException::new);
            Type productType = product.getType();
            Brand productBrand = product.getBrand();
            productType.removeProduct(product);
            productBrand.removeProduct(product);
            productType.removeBrand(productBrand);
            type.addProduct(product);
            brand.addProduct(product);
            type.addBrand(brand);

        }
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return productRepository.save(product).getId();
    }

    /**
     * adds new type to database or updates existing
     *
     * @param dto dto for adding new type or updating existing type
     * @return id of created or updated type
     */
    @Override
    public long addType(TypeDto dto) {
        Type type;
        if (typeRepository.getOneByName(dto.getName()) != null) {
            throw new TypeExistsException(dto.getName());
        }
        if (dto.getId() == null) {
            type = new Type();
        } else {
            type = typeRepository.findById(dto.getId()).orElseThrow(NoSuchElementException::new);
            if (type.getId() == 1) {
                throw new TypeNotSelectedCanNotBeUpdatedOrDeletedException();
            }
        }
        type.setName(dto.getName());
        return typeRepository.save(type).getId();
    }

    /**
     * adds new brand to database or updates existing
     *
     * @param dto dto for adding new brand or updating existing brand
     * @return id of created or updated brand
     */
    @Override
    public long addBrand(BrandDto dto) {
        Brand brand;
        if (brandRepository.getOneByName(dto.getName()) != null) {
            throw new BrandExistsException(dto.getName());
        }
        if (dto.getId() == null) {
            brand = new Brand();
        } else {
            brand = brandRepository.findById(dto.getId()).orElseThrow(NoSuchElementException::new);
            if (brand.getId() == 1) {
                throw new BrandNotSelectedCanNotBeUpdatedOrDeletedException();
            }
        }
        brand.setName(dto.getName());
        return brandRepository.save(brand).getId();
    }

    /**
     * deletes product from database
     *
     * @param productId id of product
     * @return id of deleted product
     */
    @Override
    public long deleteProduct(long productId) {
        Product product = productRepository.findById(productId).get();
        removePhotos(productId);
        long id = product.getId();
        Type type = product.getType();
        Brand brand = product.getBrand();
        type.removeProduct(product);
        brand.removeProduct(product);
        productRepository.deleteById(id);
        return id;
    }

    /**
     * adds new photos to existing product
     *
     * @param dto dto with list of new photos
     * @return id of product to which new photos has been added
     */
    @Override
    public long addPhoto(PhotoDto dto) {
        Product product = productRepository.findById(dto.getProductId()).get();
        Path photoPath;
        removePhotos(product.getId());
        for (MultipartFile file : dto.getPhotos()) {
            Photo newPhoto = new Photo();
            long photoId = photoRepository.save(newPhoto).getId();
            newPhoto.setName(file.getOriginalFilename());
            newPhoto.setUrl(imageUrl + "photo_" + photoId + "_" + file.getOriginalFilename());
            product.addPhoto(newPhoto);
            photoPath = Paths.get(imageDir + "photo_" + photoId + "_" + file.getOriginalFilename());
            try {
                Files.write(photoPath, file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return productRepository.save(product).getId();
    }

    /**
     * @param productId
     * @return
     */
    @Override
    public long removePhotos(long productId) {
        Product product = productRepository.findById(productId).get();
        List<Photo> photos = new ArrayList<>(product.getPhotos());
        if (!photos.isEmpty()) {
            for (Photo photo : photos) {
                int index = photo.getUrl().indexOf("photo_");
                product.removePhoto(photo);
                photoRepository.delete(photo);
                if (index > -1) {
                    String file = photo.getUrl().substring(index);
                    Path path = Paths.get(imageDir + file);
                    if (path.toFile().exists())
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                }
            }
        }
        return product.getId();
    }

    /**
     * gets photo with particular id from database
     *
     * @param photoId id of photo
     * @return photo with particular id
     */
    @Override
    public Photo getPhoto(long photoId) {
        return photoRepository.findById(photoId).get();
    }


    /**
     * deletes type from database by id
     *
     * @param typeId id of type to delete
     * @return id of deleted type
     */
    @Override
    public long deleteType(long typeId) {
        if (typeId == 1) {
            throw new TypeNotSelectedCanNotBeUpdatedOrDeletedException();
        }
        Type type = typeRepository.findById(typeId).get();
        long id = type.getId();
        typeRepository.deleteById(id);
        return id;
    }

    /**
     * deletes brand from database by id
     *
     * @param brandId id of brand to delete
     * @return id of deleted brand
     */
    @Override
    public long deleteBrand(long brandId) {
        if (brandId == 1) {
            throw new BrandNotSelectedCanNotBeUpdatedOrDeletedException();
        }
        Brand brand = brandRepository.findById(brandId).get();
        long id = brand.getId();
        brandRepository.deleteById(id);
        return id;
    }

}
