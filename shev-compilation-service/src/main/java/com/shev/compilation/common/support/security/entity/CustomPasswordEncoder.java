package com.shev.compilation.common.support.security.entity;

import com.shev.compilation.common.util.PrimaryKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomPasswordEncoder implements PasswordEncoder
{

    @Override
    public String encode(CharSequence rawPassword)
    {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword)
    {
        String encodePwd = encode(rawPassword);
        String decodePwd = null;
        try
        {
            decodePwd = PrimaryKeyUtil.privateKey(encodedPassword, PrimaryKeyUtil.modulus, PrimaryKeyUtil.privateExponent);
        } catch (Exception e)
        {
        }
        return encodePwd.equals(decodePwd);
    }
}
