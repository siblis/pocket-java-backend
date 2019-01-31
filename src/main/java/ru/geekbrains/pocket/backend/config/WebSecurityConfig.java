package ru.geekbrains.pocket.backend.config;

//@Configuration
class WebSecurityConfig { //extends GlobalAuthenticationConfigurerAdapter {

//    @Autowired
//    UserRepository userRepository;

//    @Autowired
//    private UserService userService;
//
//    @Override
//    public void init(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService); //userDetailsService());
//    }

//    @Bean
//    UserDetailsService userDetailsService() {
//        return email -> Optional.of(userRepository.findByEmail(email))
//                .map(u -> new User(u.getEmail(), u.getPassword(), true, true, true, true,
//                        AuthorityUtils.createAuthorityList("USER", "write")))
//                .orElseThrow(
//                        () -> new UsernameNotFoundException("could not find the user '"
//                                + email + "'"));
//    }
}
