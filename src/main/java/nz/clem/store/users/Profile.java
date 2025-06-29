package nz.clem.store.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Entity
@Getter
@Service
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bio")
    private String bio;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId // use same column as both primary and foreign key
    @ToString.Exclude
    @JsonIgnore
    private User user;

//    public void setUser(User user) {
//        this.user = user;
//        // user.setProfile(this);
//    }
}
