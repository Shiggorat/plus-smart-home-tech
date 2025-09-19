package ru.yandex.practicum.commerce.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.dto.AddressDto;
import ru.yandex.practicum.commerce.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.commerce.exception.ProductInShoppingCartLowQuantityInWarehouseException;
import ru.yandex.practicum.commerce.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.commerce.request.AddProductUnitRequest;
import ru.yandex.practicum.commerce.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.commerce.warehouse.mapper.WarehouseProductMapper;
import ru.yandex.practicum.commerce.warehouse.model.Dimension;
import ru.yandex.practicum.commerce.warehouse.model.WarehouseProduct;
import ru.yandex.practicum.commerce.warehouse.repository.ProductRepository;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private static final String[] ADDRESSES =
            new String[]{"ADDRESS_1", "ADDRESS_2"};
    private static final String CURRENT_ADDRESS =
            ADDRESSES[Random.from(new SecureRandom()).nextInt(0, 1)];
    private final WarehouseProductMapper mapper;
    private final ProductRepository productRepository;

    @Override
    public void createProduct(NewProductInWarehouseRequest request) {
        if (productRepository.existsByProductId(request.getProductId())) {
            throw new SpecifiedProductAlreadyInWarehouseException
                    ("The product with this description has already been registered in the warehouse.");
        }
        WarehouseProduct warehouseProduct = mapper.toEntity(request);
        productRepository.save(warehouseProduct);
    }

    @Override
    public BookedProductsDto checkProductsQuantity(ShoppingCartDto cart) {
        Set<String> productIds = cart.getProducts().keySet();
        List<WarehouseProduct> warehouseProducts = productRepository.findByProductIdIn(productIds);
        float volume = 0;
        float weight = 0;
        boolean fragile = false;
        for (WarehouseProduct product : warehouseProducts) {
            if (product.getQuantity() < cart.getProducts().get(product.getProductId())) {
                throw new ProductInShoppingCartLowQuantityInWarehouseException(
                        "The product from the basket is not in the required quantity in warehouse.");
            }

            Dimension dimension = product.getDimension();
            float productVolume = dimension.getHeight() * dimension.getWidth() * dimension.getDepth();
            volume += productVolume;

            if (!fragile) {
                fragile = product.isFragile();
            }

            weight += product.getWeight();
        }
        return new BookedProductsDto(weight, volume, fragile);
    }

    @Override
    public void addProductQuantity(AddProductUnitRequest request) {
        WarehouseProduct warehouseProduct = productRepository.findById(request.getProductId()).orElseThrow(() ->
                new NoSpecifiedProductInWarehouseException("There is no information about the product in warehouse."));
        int total = warehouseProduct.getQuantity() + request.getQuantity();
        warehouseProduct.setQuantity(total);
        productRepository.save(warehouseProduct);
    }

    @Override
    public AddressDto getAddress() {
        return AddressDto.builder()
                .country(CURRENT_ADDRESS)
                .city(CURRENT_ADDRESS)
                .street(CURRENT_ADDRESS)
                .house(CURRENT_ADDRESS)
                .flat(CURRENT_ADDRESS)
                .build();
    }
}
