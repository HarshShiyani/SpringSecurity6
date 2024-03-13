package com.harsh.securebank.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {

    private String name;

    private String email;

    private String mobileNumber;

    private String role;

    private List<String> authorities;
}
