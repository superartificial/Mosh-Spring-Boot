package nz.clem.store.users;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class DemoUserService {

    private final UserRepository userRespository;
    private final ProfileRepository profileRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void showRelatedEntities() {
        var p = profileRepository.findById(1L).orElseThrow();
        System.out.println(p.getUser().getEmail());
    }

    public void fetchAddress() {
        var address = addressRepository.findById(1L).orElseThrow();
        System.out.println(address.getCity());
    }

    public void persistRelated() {
        var user = User.builder()
                .name("Clem")
                .email("<EMAIL>")
                .password("<PASSWORD>")
                .build();

        var address = Address.builder()
                .street("street")
                .city("city")
                .state("CA")
                .zip("12345")
                .build();

        user.addAddress(address);
        userRespository.save(user);
    }

//    @Transactional
//    public void deleteRelated() {
//        var user = userRespository.findById(3L).orElseThrow();
//        var address = user.getAddresses().getFirst();
//        user.removeAddress(address);
//        userRespository.save(user);
////        userRespository.delete(user);
//    }

    @Transactional
    public void fetchUser() {
        var user = userRespository.findByEmail("clemw@hotmail.com").orElseThrow();
        System.out.println(user.getName());
    }

    @Transactional
    public void  fetchUsers() {
        var users = userRespository.findAllWithAddresses();
        users.forEach(u -> {
            System.out.println(u.getName());
            u.getAddresses().forEach(System.out::println);
        });
    }



    /*
    @Transactional
    public void findProfilesWithLoyaltyPointsGreaterThan() {
        userRespository.findLoyalUsers(5)
                .forEach(
                        u -> System.out.println(u.getEmail())
                );
    }

     */

}
