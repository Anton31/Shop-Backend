package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.*;
import com.example.authserverresourceserversameapp.exception.BrandExistsException;
import com.example.authserverresourceserversameapp.exception.ProductExistsException;
import com.example.authserverresourceserversameapp.exception.TypeExistsException;
import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Photo;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.Type;
import com.example.authserverresourceserversameapp.service.AppUserDetailsService;
import com.example.authserverresourceserversameapp.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @MockBean
    AppUserDetailsService userDetailsService;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    private Type type;
    private Brand brand;
    private Product product;
    private Photo photo;

    @BeforeEach
    public void setup() {
        product = new Product();
        product.setId(1L);
        product.setName("Mercedes S600");
        type = new Type();
        type.setId(1L);
        type.setName("Car");
        brand = new Brand();
        brand.setId(1L);
        brand.setName("Mercedes");
        photo = new Photo();
        photo.setId(1L);
        photo.setName("photo1.jpg");
    }

    @Test
    @WithMockUser
    public void getProductsTest() throws Exception {
        ResponseProductDto dto = new ResponseProductDto();
        Product product1 = new Product();
        product1.setId(2L);
        product1.setName("BMW 750i");
        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product1);
        dto.setProducts(products);
        dto.setTotalProducts(2L);
        dto.setPageSize(10);
        given(productService.getProducts(anyLong(), anyLong(), anyString(), anyString(), anyInt(), anyInt()))
                .willReturn(dto);
        this.mockMvc.perform(get("/products/product").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products", hasSize(2)))
                .andExpect(jsonPath("$.products[0].id").value(1))
                .andExpect(jsonPath("$.products[0].name").value("Mercedes S600"))
                .andExpect(jsonPath("$.products[1].id").value(2))
                .andExpect(jsonPath("$.products[1].name").value("BMW 750i"));
    }

    @Test
    @WithMockUser
    public void getAllTypesTest() throws Exception {
        Type type1 = new Type();
        type1.setId(2L);
        type1.setName("Smartphone");
        List<Type> types = new ArrayList<>();
        types.add(type);
        types.add(type1);
        given(productService.getAllTypes(anyString(), anyString())).willReturn(types);
        this.mockMvc.perform(get("/products/type").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Car"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Smartphone"));
    }


    @Test
    @WithMockUser
    public void getBrandsTest() throws Exception {
        Brand brand1 = new Brand();
        brand1.setId(2L);
        brand1.setName("BMW");
        List<Brand> brands = new ArrayList<>();
        brands.add(brand);
        brands.add(brand1);
        given(productService.getAllBrandsByTypeId(anyLong(), anyString(), anyString())).willReturn(brands);
        this.mockMvc.perform(get("/products/brand?typeId=1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Mercedes"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("BMW"));
    }

    @Test
    @WithMockUser
    public void addProductTest() throws Exception {
        String json = "{\"id\":0, \"name\":\"Audi A8\"}";
        given(productService.addProduct(any(ProductDto.class))).willReturn(3L);
        this.mockMvc.perform(post("/products/product").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(3));
    }

    @Test
    @WithMockUser
    public void editProductTest() throws Exception {
        String json = "{\"id\":1, \"name\":\"Audi A8\"}";
        given(productService.addProduct(any(ProductDto.class))).willReturn(1L);
        this.mockMvc.perform(put("/products/product").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
    }

    @Test
    @WithMockUser
    public void addPhotoTest() throws Exception {
        given(productService.addPhoto(any(PhotoDto.class))).willReturn(3L);
        this.mockMvc.perform(post("/products/photo").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(3));
    }

    @Test
    @WithMockUser
    public void deletePhotoTest() throws Exception {
        given(productService.getPhoto(anyLong())).willReturn(photo);
        given(productService.removePhotos(anyLong())).willReturn(1L);
        this.mockMvc.perform(delete("/products/photo/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1L));
    }

    @Test
    @WithMockUser
    public void addTypeTest() throws Exception {
        String json = "{\"name\":\"Plane\"}";
        given(productService.addType(any(TypeDto.class))).willReturn(3L);
        this.mockMvc.perform(post("/products/type").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(3));
    }

    @Test
    @WithMockUser
    public void editTypeTest() throws Exception {
        String json = "{\"name\":\"Plane\"}";
        given(productService.addType(any(TypeDto.class))).willReturn(3L);
        this.mockMvc.perform(put("/products/type").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(3));
    }

    @Test
    @WithMockUser
    public void deleteTypeTest() throws Exception {
        given(productService.deleteType(anyLong())).willReturn(1L);
        this.mockMvc.perform(delete("/products/type/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
    }

    @Test
    @WithMockUser
    public void addBrandTest() throws Exception {
        String json = "{\"name\":\"Boeing\"}";
        given(productService.addBrand(any(BrandDto.class))).willReturn(3L);
        this.mockMvc.perform(post("/products/brand").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(3));
    }

    @Test
    @WithMockUser
    public void editBrandTest() throws Exception {
        String json = "{\"name\":\"Boeing\"}";
        given(productService.addBrand(any(BrandDto.class))).willReturn(3L);
        this.mockMvc.perform(put("/products/brand").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(3));
    }

    @Test
    @WithMockUser
    public void deleteBrandTest() throws Exception {
        given(productService.deleteBrand(anyLong())).willReturn(1L);
        this.mockMvc.perform(delete("/products/brand/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
    }

    @Test
    @WithMockUser
    public void typeExistsExceptionTest() throws Exception {
        String json = "{\"name\":\"Car\"}";
        given(productService.addType(any(TypeDto.class)))
                .willThrow(new TypeExistsException("Car"));
        this.mockMvc.perform(post("/products/type").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message")
                        .value("Type with name: \"Car\" already exists!"));

    }

    @Test
    @WithMockUser
    public void brandExistsExceptionTest() throws Exception {
        String json = "{\"name\":\"Mercedes\"}";
        given(productService.addBrand(any(BrandDto.class)))
                .willThrow(new BrandExistsException("Mercedes"));
        this.mockMvc.perform(post("/products/brand").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict()).andExpect(jsonPath("$.message")
                        .value("Brand with name: \"Mercedes\" already exists!"));
    }

    @Test
    @WithMockUser
    public void productExistsExceptionTest() throws Exception {
        String json = "{\"name\":\"Mercedes S600\"}";
        given(productService.addProduct(any(ProductDto.class)))
                .willThrow(new ProductExistsException("Mercedes S600"));
        this.mockMvc.perform(post("/products/product").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict()).andExpect(jsonPath("$.message")
                        .value("Product with name: \"Mercedes S600\" already exists!"));
    }
}
