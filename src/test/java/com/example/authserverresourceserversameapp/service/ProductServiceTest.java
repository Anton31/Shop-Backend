package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.*;
import com.example.authserverresourceserversameapp.exception.BrandExistsException;
import com.example.authserverresourceserversameapp.exception.ProductExistsException;
import com.example.authserverresourceserversameapp.exception.TypeExistsException;
import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Photo;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.Type;
import com.example.authserverresourceserversameapp.repository.BrandRepository;
import com.example.authserverresourceserversameapp.repository.PhotoRepository;
import com.example.authserverresourceserversameapp.repository.ProductRepository;
import com.example.authserverresourceserversameapp.repository.TypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    PhotoDto photoDto;
    List<MultipartFile> files;
    MultipartFile mockMultipartFile;
    byte[] bytes;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private TypeRepository typeRepository;

    @Mock
    private PhotoRepository photoRepository;
    @InjectMocks
    private ProductServiceImpl productService;
    private Product product;
    private Type type;
    private Brand brand;
    private Photo photo;

    @BeforeEach
    public void setup() {
        photo = new Photo();
        photo.setId(1L);
        photo.setName("1_photo1.jpg");
        photo.setUrl("http://localhost:8080/images/photo_1_photo1.jpg");
        product = new Product();
        product.setId(1L);
        product.setName("Mercedes S600");
        product.addPhoto(photo);
        brand = new Brand();
        brand.setId(1L);
        brand.setName("Mercedes");
        type = new Type();
        type.setId(1L);
        type.setName("Car");
        type.addProduct(product);
        brand.addProduct(product);
        photoDto = new PhotoDto();
        photoDto.setProductId(1L);
    }

    @Test
    void addPhotoTest() throws IOException {
        Optional<Product> product1 = Optional.of(product);
        files = new ArrayList<>();
        File file = new File("src/main/webapp/WEB-INF/images/test_image.jpg");
        bytes = Files.readAllBytes(file.toPath());
        mockMultipartFile = new MockMultipartFile("photo1.jpg", "photo1.jpg",
                String.valueOf(MediaType.MULTIPART_FORM_DATA), bytes);
        files.add(mockMultipartFile);
        photoDto.setPhotos(files);
        given(productRepository.findById(anyLong())).willReturn(product1);
        given(photoRepository.save(any(Photo.class))).willReturn(photo);
        given(productRepository.save(any(Product.class))).willReturn(product);
        long id = productService.addPhoto(photoDto);
        assertThat(id).isEqualTo(1L);
    }



    @Test
    public void getProductsTest() {
        Product product1 = new Product();
        product1.setId(2L);
        product1.setName("BMW 750i");
        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product1);
        ResponseProductDto dto = new ResponseProductDto();
        dto.setProducts(products);
        Page<Product> page = new PageImpl<>(products);
        given(productRepository.findAll(PageRequest.of(0, 10, Sort.Direction.valueOf("ASC"),
                "name"))).willReturn(page);
        dto = productService.getProducts(null, null, "name", "ASC", 0, 10);

        assertThat(dto.getProducts().size()).isEqualTo(2);
        assertThat(dto.getProducts().get(0).getId()).isEqualTo(1L);
        assertThat(dto.getProducts().get(0).getName()).isEqualTo("Mercedes S600");
        assertThat(dto.getProducts().get(1).getId()).isEqualTo(2L);
        assertThat(dto.getProducts().get(1).getName()).isEqualTo("BMW 750i");
    }

    @Test
    public void addProductTest() {
        Product product1 = new Product();
        product1.setId(3L);
        product1.setName("Mercedes S500");
        ProductDto dto = new ProductDto();
        dto.setId(null);



        given(productRepository.save(any(Product.class))).willReturn(product1);
        given(typeRepository.findById(anyLong())).willReturn(Optional.ofNullable(type));
        given(brandRepository.findById(anyLong())).willReturn(Optional.ofNullable(brand));
        long productId = productService.addProduct(dto);
        assertEquals(productId, 3L);
    }

    @Test
    public void ProductExistsExceptionTest() {
        ProductDto dto = new ProductDto();
        dto.setId(null);
        dto.setName("Mercedes S600");
        given(typeRepository.findById(anyLong())).willReturn(Optional.ofNullable(type));
        given(brandRepository.findById(anyLong())).willReturn(Optional.ofNullable(brand));
        given(productRepository.findByName(anyString())).willThrow(new ProductExistsException("Mercedes S600"));
        ProductExistsException exception = assertThrows(ProductExistsException.class,
                () -> productService.addProduct(dto));
        assertEquals("Product with name: \"Mercedes S600\" already exists!", exception.getMessage());
    }
    @Test
    public void getTypesTest() {
        Type type1 = new Type();
        type1.setId(2L);
        type1.setName("Smartphone");
        List<Type> types = new ArrayList<>();
        types.add(type);
        types.add(type1);
        given(typeRepository.findAll(any(Sort.class))).willReturn(types);
        List<Type> serviceTypes = productService.getAllTypes("ASC", "name");
        assertThat(serviceTypes).isNotNull();
        assertThat(serviceTypes.size()).isEqualTo(2);
        assertThat(serviceTypes.get(0).getId()).isEqualTo(1L);
        assertThat(serviceTypes.get(0).getName()).isEqualTo("Car");
        assertThat(serviceTypes.get(1).getId()).isEqualTo(2L);
        assertThat(serviceTypes.get(1).getName()).isEqualTo("Smartphone");
    }

    @Test
    public void TypeExistsExceptionTest() {
        TypeDto dto = new TypeDto();
        dto.setId(0L);
        dto.setName("Car");
        given(typeRepository.getOneByName(anyString())).willThrow(new TypeExistsException("Car"));
        TypeExistsException exception = assertThrows(TypeExistsException.class, () -> productService.addType(dto));
        assertEquals("Type with name: \"Car\" already exists!", exception.getMessage());
    }

    @Test
    public void getBrandsByTypeIdTest() {
        List<Brand> brands = new ArrayList<>();
        brands.add(brand);
        given(brandRepository.getAllByTypesId(anyLong(), any(Sort.class))).willReturn(brands);
        List<Brand> serviceBrands = productService.getAllBrandsByTypeId(1L, "ASC", "name");
        assertThat(serviceBrands).isNotNull();
        assertThat(serviceBrands.size()).isEqualTo(1);
        assertThat(serviceBrands.get(0).getId()).isEqualTo(1L);
        assertThat(serviceBrands.get(0).getName()).isEqualTo("Mercedes");
    }

    @Test
    public void getBrandsByTypeIdIsNullTest() {
        Brand brand1 = new Brand();
        brand1.setId(2L);
        brand1.setName("Apple");
        List<Brand> brands = new ArrayList<>();
        brands.add(brand);
        brands.add(brand1);
        given(brandRepository.findAll(any(Sort.class))).willReturn(brands);
        List<Brand> serviceBrands = productService.getAllBrandsByTypeId(0, "ASC", "name");
        assertThat(serviceBrands).isNotNull();
        assertThat(serviceBrands.size()).isEqualTo(2);
        assertThat(serviceBrands.get(0).getId()).isEqualTo(1L);
        assertThat(serviceBrands.get(0).getName()).isEqualTo("Mercedes");
        assertThat(serviceBrands.get(1).getId()).isEqualTo(2L);
        assertThat(serviceBrands.get(1).getName()).isEqualTo("Apple");
    }

    @Test
    public void BrandExistsExceptionTest() {
        BrandDto dto = new BrandDto();
        dto.setId(0L);
        dto.setName("Samsung");
        given(brandRepository.getOneByName(anyString())).willThrow(new BrandExistsException("Samsung"));
        BrandExistsException exception = assertThrows(BrandExistsException.class, () -> productService.addBrand(dto));
        assertEquals("Brand with name: \"Samsung\" already exists!", exception.getMessage());
    }

    @Test
    public void deleteProductTest() {
        given(productRepository.findById(anyLong())).willReturn(Optional.ofNullable(product));
        doNothing().when(productRepository).deleteById(anyLong());
        long id = productService.deleteProduct(anyLong());
        assertThat(id).isEqualTo(1L);
    }
}
