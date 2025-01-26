package com.auth.db;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.jupiter.api.Test;

//public class PostgreSQLConnectionTest {
//  private String URL = System.getenv("POSTGRES_USER_URL");
//  private String USERNAME = System.getenv("POSTGRES_USERNAME"); //postgresql 계정
//  private String PASSWORD = System.getenv("POSTGRES_PASSWORD"); //비밀번호
//
//  @Test
//  public void ConnectionTest() throws Exception{
//    Connection con = DriverManager.getConnection(URL,USERNAME,PASSWORD); //db 연결
//    System.out.println(con); //연결 정보 출력
//  }
//}