package com.example.design.controller;

import com.example.design.dto.AccessTokenDTO;
import com.example.design.dto.GithubUser;
import com.example.design.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
   public String callback(@RequestParam(name = "code") String code,
                          @RequestParam(name = "state") String state){
       AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
       accessTokenDTO.setCode(code);
       accessTokenDTO.setState(state);
       accessTokenDTO.setClient_id("3efb8f26710211516eb1");
       accessTokenDTO.setClient_secret("bbe85df5d82da92e2c30f6be3f482449dc678601");
       accessTokenDTO.setRedirect_uri("http://127.0.0.1:8887/callback");
       String accessToken = githubProvider.getAccessToken(accessTokenDTO);
       GithubUser user = githubProvider.getGithubUser(accessToken);
       System.out.println(user.getName());
       return "index";
   }
}
