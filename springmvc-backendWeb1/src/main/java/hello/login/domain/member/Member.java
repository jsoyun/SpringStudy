package hello.login.domain.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Builder
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private Long id;

    @NotEmpty
    private String loginId; //로그인ID
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;

}
