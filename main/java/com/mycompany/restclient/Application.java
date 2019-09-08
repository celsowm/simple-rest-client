/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restclient;

import java.lang.reflect.Array;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private RestService restService;

    public Application(RestService restService) {
        this.restService = restService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        String uri = "https://jsonplaceholder.typicode.com/posts";
        
        //teste get (all)
        Class vo = Post[].class;
        Object retorno = restService.get(uri, vo);
        Post[] posts = (Post[]) retorno;
        for(Post post : posts){
            System.out.println(post.getTitle());
        }
        
        //teste get (by id)
        int id = 3;
        Class voGet = Post.class;
        Object retornoGet = restService.get(uri, voGet, id);
        Post postId3 = (Post) retornoGet;
        System.out.println(postId3.getTitle());
        
        //test post (inserir)
        Post novoPost = new Post(33, "teste legal", "bla bla bla");
        Post retornoPostar = (Post) restService.post(uri, novoPost);
        System.out.println(retornoPostar.getTitle());
        
        //test put (editar)
        int idPut = 1;
        Post postAlterado = new Post(1, "mudei", "bli bli bli");
        Post retornoAlterar = (Post) restService.put(uri, postAlterado, idPut);
        System.out.println(retornoAlterar.getTitle());
        
        //test delete (remover)
        int idDelete = 3;
        restService.delete(uri, idDelete);
        
        /*if (retorno.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(retorno); i++) {
                Post post = (Post) Array.get(retorno, i);
                System.out.println(post.getTitle());
            }
        }*/

        /*for (var object : restService.get(uri, Post.class)) {
            Post post = (Post) object;
            System.out.println(post.getTitle());
        }*/
        // fetch post with url parameter
//		System.out.println(restService.getPostWithUrlParameters());
        // fetch post with response handling
//		System.out.println(restService.getPostWithResponseHandling());
        // fetch post with response handling
//		System.out.println(restService.getPostWithCustomHeaders());
        // create a new post
//		System.out.println(restService.createPost());
        // create a new post with object
//		System.out.println(restService.createPostWithObject());
        // update post
//		restService.updatePost();
        // update post with response handling
//		System.out.println(restService.updatePostWithResponse());
        // delete post
//		restService.deletePost();
        // retrieve headers
//        System.out.println(restService.retrieveHeaders().getCacheControl());
        // list allowed operations
//		for (HttpMethod method : restService.allowedOperations()) {
//			System.out.println(method.name());
//		}
        // get request with error handling
        //System.out.println(restService.unknownRequest());
    }
}
