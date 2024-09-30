package exercise.mapper;

import org.mapstruct.*;

import exercise.dto.CarCreateDTO;
import exercise.dto.CarUpdateDTO;
import exercise.dto.CarDTO;
import exercise.model.Car;

// BEGIN
@Mapper(
        // Подключение JsonNullableMapper
        uses = {JsonNullableMapper.class},
        //Вот эта штука обязательна, не дает затерать не переданные при апдейте поля
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public abstract class CarMapper {
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract Car map(CarCreateDTO carCreateDTO);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract Car update(CarUpdateDTO carUpdateDTO, @MappingTarget Car car);

    public abstract CarDTO map(Car car);
}
// END
