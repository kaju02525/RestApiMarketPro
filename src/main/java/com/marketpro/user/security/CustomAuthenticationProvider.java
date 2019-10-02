package com.marketpro.user.security;

import com.marketpro.user.model.authentication.AJwtRequest;
import com.marketpro.user.model.authentication.User;
import com.marketpro.user.repository.AUserRepo;
import com.marketpro.user.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private AUserRepo repo;


	 @Autowired
	 private PasswordEncoder passwordEncoder;

	@Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        AJwtRequest jrequest = (AJwtRequest) authentication.getPrincipal();
        User checkUser=null;
		String roles="";
		List<GrantedAuthority> grantedAuths =null;
		AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
		if(Utils.isPhoneNumberValid(jrequest.getMobile().trim()))
			checkUser=repo.findByMobile(jrequest.getMobile().trim());
		else if (Utils.isEmailValid(jrequest.getMobile().trim()))
			checkUser=repo.findByEmail(jrequest.getMobile().trim());

		if (checkUser!=null && passwordEncoder.matches(jrequest.getPassword().trim(),checkUser.getPassword().trim())) {
			grantedAuths =AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
			return new UsernamePasswordAuthenticationToken(jrequest.getMobile().trim(), null,grantedAuths);
		} else {
			throw new UsernameNotFoundException("mobile number or password invalid ! please try again");
		}
	

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public Authentication getUserDetails(AJwtRequest jrequest)
    	      throws AuthenticationException {

       //AJwtRequest jrequest = (AJwtRequest) authentication.getPrincipal();
    	User checkUser=null;
		String roles="";
		List<GrantedAuthority> grantedAuths =null;
                AuthorityUtils.commaSeparatedStringToAuthorityList(roles);

		if(Utils.isPhoneNumberValid(jrequest.getMobile()))
			checkUser=repo.findByMobile(jrequest.getMobile());
		else if (Utils.isEmailValid(jrequest.getMobile()))
			checkUser=repo.findByEmail(jrequest.getMobile());
		
		if (checkUser!=null) {
			grantedAuths =AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
			return new UsernamePasswordAuthenticationToken(jrequest.getMobile(), null,grantedAuths);
		} else {
			throw new UsernameNotFoundException("Passowrd is wrong or User not found with username: " + jrequest.getMobile());
		}
		
    	    }


}