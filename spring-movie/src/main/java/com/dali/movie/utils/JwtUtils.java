package com.dali.movie.utils;

//配置
import com.dali.movie.constant.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtUtils {
    private static final long JWT_EXPIRATION = 60*60*1000;
    private static final String secretStr = "Le++o8NQWVXWo3+SJtAtnjBW9iA0OvPL0c0mMrol2fU=";
    private static final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretStr));
    /**
     * 生成token
     */
    public static String genJwtToken(Map<String, Object> claim){

        String token = Jwts.builder().setClaims(claim)
                .setExpiration(new Date(System.currentTimeMillis()+JWT_EXPIRATION))
                .signWith(key)
                .compact();
        return token;

    }

    /**
     * 校验token
     * Claims 为 null 表示校验失败
     */
    public static Claims parseToken(String token){
        JwtParser build = Jwts.parserBuilder().setSigningKey(key).build();
        Claims claims = null;
        try {
            claims = build.parseClaimsJws(token).getBody();
        }catch (Exception e){
            log.error("解析token失败, token:{}", token);
            return null;
        }
        return claims;
    }

    //从token中获取登录用户ID
    public static Long getUserIdByToken(String token){
        Claims claims = parseToken(token);
        if (claims!=null){
            Long userId = (Long) claims.get(Constants.TOKEN_USER_ID);
            if (userId>0){
                return userId;
            }
        }
        return null;
    }

    //从token中获取评论目标电影Id
    public static Long getTargetIdByToken(String token){
        Claims claims = parseToken(token);
        if (claims!=null){
            Long targetId = (Long) claims.get(Constants.TOKEN_TARGET_ID);
            if (targetId>0){
                return targetId;
            }
        }
        return null;
    }

    //从token中获取父评论Id
    public static Long getParentIdByToken(String token){
        Claims claims = parseToken(token);
        if (claims!=null){
            Long parentId = (Long) claims.get(Constants.TOKEN_PARENT_ID);
            if (parentId>0){
                return parentId;
            }
        }
        return null;
    }

    //从token中获取评论状态
    public static Byte getStatusByToken(String token){
        Claims claims = parseToken(token);
        if (claims!=null){
            Byte status = (Byte) claims.get(Constants.TOKEN_STATUS);
            if (status>0){
                return status;
            }
        }
        return null;
    }

    //从token中获取评论点赞数
    public static Integer getLikeCountByToken(String token){
        Claims claims = parseToken(token);
        if (claims!=null){
            Integer likeCount = (Integer) claims.get(Constants.TOKEN_LIKE_COUNT);
            if (likeCount>0){
                return likeCount;
            }
        }
        return null;
    }

    //从token中获取评论回复数
    public static Integer getReplyCountByToken(String token){
        Claims claims = parseToken(token);
        if (claims!=null){
            Integer replyCount = (Integer) claims.get(Constants.TOKEN_REPLY_COUNT);
            if (replyCount>0){
                return replyCount;
            }
        }
        return null;
    }

    //从token中获取用户名
    public static String getUsernameByToken(String token){
        Claims claims = parseToken(token);
        if (claims!=null){
            String username = (String) claims.get(Constants.TOKEN_USERNAME);
            if (username != null){
                return username;
            }
        }
        return null;
    }
}
