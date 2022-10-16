package kz.bitlab.spring.crm.mappers;

import kz.bitlab.spring.crm.models.Users;
import kz.bitlab.spring.crm.models.dto.UsersDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    UsersDto entityToDto(Users user);
    Users dtoToEntity(UsersDto usersDto);
    List<UsersDto> toDtoList(List<Users> entityList);
    List<Users> toUsersList(List<UsersDto> dtoList);
}
