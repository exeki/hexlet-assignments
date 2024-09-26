package exercise.mapper;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

// BEGIN
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ProductMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "name", source = "title")
    @Mapping(target = "barcode", source = "vendorCode")
    @Mapping(target = "cost", source = "price")
    public abstract Product map(ProductCreateDTO product);

    @Mapping(target = "price", source = "cost")
    @Mapping(target = "vendorCode", source = "barcode")
    @Mapping(target = "title", source = "name")
    public abstract ProductDTO map(Product product);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "barcode", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "cost", source = "price")
    public abstract Product update(ProductUpdateDTO product, @MappingTarget Product model);
}
// END
