package com.rd.jdbc.opeartions;

import com.rd.jdbc.opeartions.models.User;
import com.rd.jdbc.opeartions.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class RepositoryTest {

    @Test
    public void testRoundingUp(){
        BigDecimal decimal = new BigDecimal("1.23");
        System.out.println(decimal);
        decimal =decimal.setScale(0, RoundingMode.UP);
        System.out.println(decimal);
    }

    @Test
    public void checkBigDecimalImplForTimesCalculation(){
        int result = calculateTimes(500, 15, 0.95);
        System.out.println("Current time:"+ LocalDateTime.now());
        System.out.println("The result is " + result+"::"+LocalDateTime.now());
    }

    public int calculateTimes(int card, int ticket, double perc){

        double priceOfA = 0;
        double priceOfB = card;
        int times = 0;
        double currentValue = ticket * perc; // First discounted ticket price

        while (Math.ceil(priceOfB) >= priceOfA) { // Continue until System B is cheaper
            times++;
            priceOfA += ticket; // Normal ticket cost
            priceOfB += currentValue; // Add current discounted price
            currentValue *= perc; // Apply discount for next ticket
        }

        return times;
    }

    @Test
    public void generateJwt(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", "rabindra");
        claims.put("operators","op1234");
        String jwtV="eyJhbGciOiJIUzI1NiJ9.eyJvcGVyYXRvcnMiOiJvcDEyMzQiLCJ1c2VyIjoicmFiaW5kcmEifQ.wZVDLH7r4XV5IHh7PImgCQvqQUz4kGPi1lkrLfUG7l4";
        Jws<Claims> parsedJwt = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor("SECRET123456ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes()))
                .build()
                .parseClaimsJws(jwtV);

        Claims claims1 = parsedJwt.getBody();
        String operator= claims1.get("operators").toString();
        System.out.println(operator);
        String jwtVal = Jwts.builder().claims(claims).signWith(Keys.hmacShaKeyFor("SECRET123456ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes()), SignatureAlgorithm.HS256).compact();
        System.out.println(jwtVal);
    }

    @Test
    public void testCharAtUsingCharItself(){
        String randomString= """
                hello rabindra how's everything man ? 
                you've been progressing lately right ? 
                proud of you man, you're finally making it .
                 keep going man. don't ever feel disappointed eh ? 
                 i am always by your side man ? i am the greatest companion of yours, 
                 keep marching till the last breath. no exception man. got it ?
                """;
        String sample = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String sample1= "abcdefghijklmnopqrstuvwxyz";
        char rot13Char;

        List<Character> rotList = new ArrayList<>();
        for(char c:randomString.toCharArray()){
            String group = checkContent(c, sample, sample1);
            if((group.equalsIgnoreCase(sample1)&&group.equalsIgnoreCase(sample))){
                int pos = group.indexOf(c);
                int charPosition = pos+13;
                if(charPosition>group.length()-1){
                    int p = (group.length()-charPosition);
                    charPosition = p*-1;
                }
                rot13Char  = group.charAt(charPosition);
            }
            else{
                rot13Char= group.toCharArray()[0];
            }

            rotList.add(rot13Char);
        }
        System.out.println(rotList.stream().map(String ::valueOf).collect(Collectors.joining("")));
    }

    public String checkContent(char c, String ... args){
        return args[0].contains(String.valueOf(c))?args[0]: (args[1].contains(String.valueOf(c)) ? args[1] :String.valueOf(c));
    }

//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void testFindByNameAndAbout() {
//        List<User> users = userRepository.findByNameAndAbout();
//        System.out.println(users);
//    }
}
