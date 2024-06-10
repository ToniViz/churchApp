package com.app.church;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.management.InvalidAttributeValueException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import com.app.church.church.entities.articles.Article;
import com.app.church.church.entities.articles.Comment;
import com.app.church.church.entities.articles.Content;
import com.app.church.church.entities.articles.Topic;
import com.app.church.church.repository.ArticleRepository;
import com.app.church.church.services.ArticleServiceImpl;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleServiceImpl articleService = new ArticleServiceImpl();

    /**
     * We need to received an Article with it video field
     * @throws InvalidAttributeValueException
     */
    @Test
    public void testUpdateArticleVideo() throws InvalidAttributeValueException{

        //Data test
        Article article = new Article("Perros", "Locos");
        article.setId(01L);
        article.setVideo("corneta@gmail.com");

        //Mock configuration
        Mockito.when(articleRepository.findById(01L)).thenReturn
        (Optional.of(article));

        //Method test
        Article result = articleService.updateArticleVideo(article.getVideo(), 01, 01l);

        //Check results
        assertNotNull(result);
        assertEquals("corneta@gmail.com", result.getVideo());

    }

    @Test
    public void testFindAllArticlesByVerification(){

        //Data Test
        Topic topic = new Topic("COMMON", false);
        Topic topicTwo = new Topic("NOT_COMMON", false);
        Article article = new Article("Perros", "Locos");
        article.setId(01L);
        article.setVideo("corneta@gmail.com");
        article.getTopics().add(topic);
        article.setVerification(true);

        Article articleTwo = new Article("lobos", "crazy");
        articleTwo.setId(01L);
        articleTwo.setVideo("corneta@gmail.com");
        articleTwo.getTopics().add(topicTwo);
        articleTwo.setVerification(true);

        Article articleThree = new Article("Perras", "Locos");
        articleThree.setId(01L);
        articleThree.setVideo("corneta@gmail.com");
        articleThree.getTopics().add(topic);
        articleThree.setVerification(true);

        List<Article> articles = Arrays.asList(article, articleTwo, articleThree);
        Page<Article> pageArticles = new PageImpl<>(articles);
        Pageable pageable = PageRequest.of(0, 10);
        //Mock configuration
        when(articleRepository.findAllWithVerification(true, pageable)).thenReturn(pageArticles);

        //Method test
        Page<Article> result = articleService.findAllArticlesWithVerification(true, 0, 10);

        //Check results 
        assertNotNull(result);
        assertEquals(2, result.getSize());


        //Check data results
        assertEquals("Perros", result.get().findFirst().get().getTitle());
       
    }

    @Test
    public void testFindAllByTopics(){
        //Data Test
        Topic topic = new Topic("COMMON", false);
        Topic topicTwo = new Topic("NOT_COMMON", false);
        Article article = new Article("Perros", "Locos");
        article.setId(01L);
        article.setVideo("corneta@gmail.com");
        article.getTopics().add(topic);
        article.setVerification(true);

        Article articleTwo = new Article("lobos", "crazy");
        articleTwo.setId(01L);
        articleTwo.setVideo("corneta@gmail.com");
        articleTwo.getTopics().add(topicTwo);
        articleTwo.setVerification(true);

        Article articleThree = new Article("Perras", "Locos");
        articleThree.setId(01L);
        articleThree.setVideo("corneta@gmail.com");
        articleThree.getTopics().add(topic);
        articleThree.setVerification(true);

        List<Article> articles = Arrays.asList(article, articleTwo, articleThree);
        Page<Article> articlePage = new PageImpl<>(articles);
        Pageable pageable = PageRequest.of(0, 10);
        //Mock configuration
        when(articleRepository.findAllWithTopics(pageable)).thenReturn(articlePage);

        //Method test
        Page<Article> result = articleService.findAllByTopics("NOT_COMMON",0,10);

        //Check results 
        assertNotNull(result);
        assertEquals(1, result.getSize());


        //Check data results
        assertEquals("lobos", result.get().findFirst().get().getTitle());
       
    }


    @Test 
    public void testDeleteContent() throws InvalidAttributeValueException{
        //Data Test
        Article article = new Article("Perros", "Locos");
        article.setId(01L);
        article.setVideo("corneta@gmail.com");

        Content content = new Content("Las bodas de F", "Figaro");
        content.setId(01L);
        article.getContent().add(content);
        Content contentTwo = new Content("Las bodas de SA", "Platón");
        contentTwo.setId(02L);
        article.getContent().add(contentTwo);
        Optional<Article> articleTest = Optional.of(article);

        //Mock config
        when(articleRepository.findByIdWithAll(01L)).thenReturn(articleTest);

        //Method test
        Article result = this.articleService.deleteContent(01L, 01L, 01L);
        //Check results
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        //Check data results
        assertEquals("Perros", result.getTitle());



    }


    @Test
    public void testSaveComment() throws InvalidAttributeValueException{

        //Data Test
        Article article = new Article("Perros", "Locos");
        article.setId(01L);
        article.setVideo("corneta@gmail.com");
        Comment comment = new Comment("Buenos días");
        comment.setId(01L);
        Comment comment2 = new Comment("Buenas noches");
        comment2.setId(02L);
        article.getComments().addAll(Arrays.asList(comment, comment2));

        //Mock config
        when(this.articleRepository.findByIdWithAll(01L)).thenReturn(
            Optional.of(article)
        );
        comment.setText("Buenas mañanas");
        Article result = this.articleService.addComment(01L,01L, comment);
        //chech result 
        assertNotNull(result);
        assertEquals(2, result.getComments().size());
        //Check data results
        List<Comment> comments = new ArrayList<>(result.getComments());
        assertEquals("Buenas mañanas", comments.get(0).getText());
    }
    
/* 
    @Test
    public void testDeleteComment() throws InvalidAttributeValueException{

        //Data Test
        Article article = new Article("Perros", "Locos");
        article.setId(01L);
        article.setVideo("corneta@gmail.com");
        Comment comment = new Comment("Buenos días");
        comment.setId(01L);
        Comment comment2 = new Comment("Buenas noches");
        comment2.setId(02L);
        article.getComments().addAll(Arrays.asList(comment, comment2));

        //Mock config
        when(this.articleRepository.findByIdWithAll(01L)).thenReturn(
            Optional.of(article)
        );
        comment.setText("Buenas mañanas");
        Article result = this.articleService.deleteComment(01L, comment);
        //chech result 
        assertNotNull(result);
        assertEquals(2, result.getComments().size());
        //Check data results
        List<Comment> comments = new ArrayList<>(result.getComments());
        assertEquals("Buenas mañanas", comments.get(0).getText());



    }

*/
    


}
