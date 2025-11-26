package com.dali.movie.mapper;

import com.dali.movie.model.MoviesInfo;
import com.dali.movie.model.UsersInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface MoviesInfoMapper {
    /**
     * 根据电影名，查看电影信息，主要用于搜索
     * @return
     */
    @Select("select * from movies where title = #{title}")
    MoviesInfo getMovieDetail(String username);

    /**
     * 电影分类
     * @return
     */
    List<MoviesInfo> getMovieGenre(String genre);

    List<MoviesInfo> getMovieReleaseDate(Integer releaseDate);

//    /**
//     * 用户注册时，插入数据
//     */
//    @Insert("insert into users (username,password) values (#{username},#{password})")
//    Integer insertData(String username,String password);
//
//    /**
//     * 根据用户ID, 查询用户信息,用于个人中心的个人信息显示
//     * @return
//     */
//    @Select("select * from users where status =1 and id = #{id}")
//    UsersInfo selectById(Integer id);

    /**
     * 电影分类，通过电影类型查看电影列表
     * @return
     */
//    @Select("select * from movies where title = #{title}")
//    MoviesInfo getMovieDetail(String username);
}
