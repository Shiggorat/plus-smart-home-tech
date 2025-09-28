package ru.yandex.practicum.commerce.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.dto.ProductDto;
import ru.yandex.practicum.commerce.dto.ProductPageDto;
import ru.yandex.practicum.commerce.exception.ProductNotFoundException;
import ru.yandex.practicum.commerce.request.SetProductQuantityStateRequest;
import ru.yandex.practicum.commerce.store.dto.GetProductsParams;
import ru.yandex.practicum.commerce.store.mapper.ProductMapper;
import ru.yandex.practicum.commerce.store.model.ProductEntity;
import ru.yandex.practicum.commerce.store.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        ProductEntity product = productMapper.toEntity(productDto);
        product.setProductId(UUID.randomUUID().toString());
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        String id = productDto.getProductId();
        validateProductExists(id);
        ProductEntity product = productRepository.save(productMapper.toEntity(productDto));
        return productMapper.toDto(product);
    }

    @Override
    public boolean deactivateProduct(String id) {
        id = id.replace("\"", "");
        validateProductExists(id);
        int updated = productRepository.deactivateProduct(id);
        return updated > 0;
    }

    @Override
    public boolean setQuantityState(SetProductQuantityStateRequest request) {
        validateProductExists(request.getProductId());
        int updated = productRepository.setProductQuantityState(request.getQuantityState(), request.getProductId());
        return updated > 0;
    }

    @Override
    public ProductDto getProductById(String id) {
        ProductEntity product = productRepository.findByProductId(id).orElseThrow(() ->
                new ProductNotFoundException(String.format("Product with id: %s was not found", id)));
        return productMapper.toDto(product);
    }

    @Override
    public ProductPageDto getProducts(GetProductsParams getProductsParams) {
        Page<ProductEntity> products = productRepository
                .findAllByProductCategory(getProductsParams.getProductCategory(), getProductsParams.getPageable());
        return productMapper.toProductPageDto(products);
    }

    @Override
    public Map<String, BigDecimal> getProductsPrice(List<String> productsIds) {
        List<ProductEntity> products = productRepository.findByProductIdIn(productsIds);
        Map<String, BigDecimal> priceById = new HashMap<>();
        for (ProductEntity product : products) {
            priceById.put(product.getProductId(), product.getPrice());
        }
        return priceById;
    }

    private void validateProductExists(String id) {
        if (!productRepository.existsByProductId(id)) {
            throw new ProductNotFoundException(String.format("Product with id: %s was not found", id));
        }
    }
}
