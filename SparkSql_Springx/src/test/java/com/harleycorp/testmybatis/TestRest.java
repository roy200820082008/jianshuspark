/**
 * 
 */
package com.harleycorp.testmybatis;

import java.net.URL;

import javax.servlet.Filter;

import org.junit.Test;

/**
 * @author kevin
 *
 */
public class TestRest {

    @Test 
    public void get(){ 
        URL url = Filter.class.getProtectionDomain().getCodeSource().getLocation(); 
        System.out.println("path:"+url.getPath()+"  name:"+url.getFile()); 
    }
}