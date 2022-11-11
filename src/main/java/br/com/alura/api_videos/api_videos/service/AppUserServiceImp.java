package br.com.alura.api_videos.api_videos.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alura.api_videos.api_videos.model.AppUser;
import br.com.alura.api_videos.api_videos.model.Authority;
import br.com.alura.api_videos.api_videos.repository.AppUserRepository;
import br.com.alura.api_videos.api_videos.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImp implements AppUserService, UserDetailsService {

    private final AppUserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Page<AppUser> findAllUsers(Pageable pageable) {
        log.info("Fetching all users");
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<AppUser> findByUsername(String username) {
        log.info("Fetching username " + username);
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        log.info("Fetching user id " + id);
        return userRepository.findById(id);
    }

    @Override
    public Optional<Authority> findAuthority(String name) {
        log.info("Fetching authority " + name);
        return authorityRepository.findByAuthority(name);
    }

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving User " + user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void addAuthorityToUser(AppUser user, Authority authority) {
        log.info("Adding authority {} to user {}", authority.getAuthority(), user.getUsername());
        user.getAuthorities().add(authority);
        // autosave (transactional)
    }

    @Override
    public AppUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = userRepository.findByUsername(username);
        if (user.isPresent())
            return user.get();

        throw new UsernameNotFoundException("Invalid Credentials");
    }

}
