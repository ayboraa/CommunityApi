package org.community.api.service;


import org.community.api.auth.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class JwtBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtBlacklistService(RedisTemplate<String, String> redisTemplate, JwtTokenUtil jwtTokenUtil) {
        this.redisTemplate = redisTemplate;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public void blacklistToken(String token) {
        var jti = jwtTokenUtil.extractJti(token);
        redisTemplate.opsForValue().set(jti, "blacklisted", 3600 * 24, TimeUnit.SECONDS);
    }

        public boolean isTokenBlacklisted(String token) {
            String jti = jwtTokenUtil.extractJti(token);
            return redisTemplate.hasKey(jti);
        }

}
