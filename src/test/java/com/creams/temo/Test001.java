package com.creams.temo;

import org.junit.Test;

import static io.restassured.RestAssured.given;

/**
 * 用例描述
 *
 * <link>JIRA链接</link>
 *
 * @author Kevin
 * @since 1.0
 */

public class Test001 {

    @Test
    public void test1(){
        given().log().all().get("https://www.baidu.com").then().statusCode(201);
    }
}
