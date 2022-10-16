package kz.bitlab.spring.crm.models.dto;

import kz.bitlab.spring.crm.models.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    String fullName;
    String email;
    List<Roles> rolesList;
}
