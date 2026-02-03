package com.nimscreation.prepmind.dto.request;

import com.nimscreation.prepmind.entity.Enum.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDto {

    @Size(min = 2, message = "Name must be at least 2 characters")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    private Role role;
}
