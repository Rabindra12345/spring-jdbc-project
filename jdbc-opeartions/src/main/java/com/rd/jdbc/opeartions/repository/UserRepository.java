package com.rd.jdbc.opeartions.repository;

import com.rd.jdbc.opeartions.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
//@JdbcRepository(dialect = Dialect.H2) // Example for a JDBC repository
//@EnableJdbcRepositories(basePackageClasses = UserRepository.class)
public abstract class UserRepository implements CrudRepository<User,String> {

     private final JdbcOperations jdbcOperations ;

     public UserRepository(JdbcOperations jdbcOperations) {
         this.jdbcOperations = jdbcOperations;
     }

    public List<User> findByNameAndAbout(){
        String sql = """
                SELECT * FROM public.user WHERE name=? AND about=?
                """;
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, "John Doe");
        params.put(2, "Software Engineer at XYZ");

        return jdbcOperations.execute(sql, (PreparedStatement statement) -> {
            params.forEach((key, val) -> {
                try{
                    if (val instanceof String) {
                        statement.setString(key, (String) val);
                    } else if (val instanceof Integer) {
                        statement.setInt(key, (Integer) val);
                    }
                }catch (SQLException exception){
                    exception.printStackTrace();
                }
            });

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User entity = new User();
                    entity.setName(resultSet.getString("name"));
                    entity.setEmail(resultSet.getString("email"));
                    entity.setAbout(resultSet.getString("about"));
                    return List.of(entity);
                }
            }
            return null;
        });
    }
}

//public interface UserRepository extends CrudRepository<User,String> {
//
//     JdbcOperations jdbcOperations = null;
//
//    default List<User> findByNameAndAbout(){
//        String sql = """
//                SELECT * FROM public.user WHERE name=? AND about=?
//                """;
//        Map<Integer,Object> params = new HashMap<>();
//        params.put(1, "John Doe");
//        params.put(2, "Software Engineer at XYZ");
//
//        return jdbcOperations.execute(sql, (PreparedStatement statement) -> {
//            params.forEach((key, val) -> {
//                try{
//                    if (val instanceof String) {
//                        statement.setString(key, (String) val);
//                    } else if (val instanceof Integer) {
//                        statement.setInt(key, (Integer) val);
//                    }
//                }catch (SQLException exception){
//                    exception.printStackTrace();
//                }
//            });
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    User entity = new User();
//                    entity.setAbout(resultSet.getString("about"));
//                    return List.of(entity);
//                }
//            }
//            return null;
//        });
//    }
//}
