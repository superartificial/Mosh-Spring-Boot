package nz.clem.store.users;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

//    @JsonProperty("user_id")
    private Long id;
    private String name;
    private String email;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createdAt;

}
